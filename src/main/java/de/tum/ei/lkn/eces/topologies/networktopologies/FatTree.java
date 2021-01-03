package de.tum.ei.lkn.eces.topologies.networktopologies;

import de.tum.ei.lkn.eces.graph.Edge;
import de.tum.ei.lkn.eces.graph.Node;
import de.tum.ei.lkn.eces.network.*;
import de.tum.ei.lkn.eces.network.util.NetworkInterface;
import de.tum.ei.lkn.eces.topologies.NetworkTopology;
import org.javatuples.Pair;

import java.util.*;

/**
 * A fat-tree topology.
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public class FatTree implements NetworkTopology {
	private Network network;

	// Store queue-level and link-level nodes
	private final Set<Node>[] aggregationSwitchesPerPod;
	private final Set<Node>[] edgeSwitchesPerPod;
	private final Set<Node> edgeSwitchesSet;
	private final Set<Node> coreSwitchesSet;

	// Nodes of hosts
	private final NetworkNode[] hostsNodes;

	// Map edge switches nodes to their pod Id
	private final Map<Node, Integer> edgeSwitchesPods;

	// SPs between pairs of edge switches
	Map<Pair<Node, Node>, List<List<Edge>>> edgesSwitchesSPs;

	// Fat tree size
	private final int k;
	// Multiplier for the number of hosts
	// E.g., with 2, the topology will have 2x more hosts compared to
	// the traditional k fat-tree topology
	private final int nHostsMultiplier;
	// For each link
	private final double propagationDelay;
	private final double[] switchesQueueSizes;
	// Link rates
	private final double edgeLinkRate, aggregationLinkRate, coreLinkRate;
	// For host uplinks
	private final double hostQueueSize;

	private final NetworkingSystem networkingSystem;

	public FatTree(NetworkingSystem networkingSystem, int k, int nHostsMultiplier, double edgeLinkRate, double aggregationLinkRate, double coreLinkRate, double propagationDelay, double[] switchesQueueSizes, double hostQueueSize) {
		if(k % 2 != 0)
			throw new RuntimeException("k must be a multiple of 2!");
		if(nHostsMultiplier < 1)
			throw new RuntimeException("the number of hosts multiplier must be greater or equal to 1");

		this.networkingSystem = networkingSystem;
		this.nHostsMultiplier = nHostsMultiplier;
		this.edgeLinkRate = edgeLinkRate;
		this.aggregationLinkRate = aggregationLinkRate;
		this.coreLinkRate = coreLinkRate;
		this.propagationDelay = propagationDelay;
		this.switchesQueueSizes = switchesQueueSizes;
		this.hostQueueSize = hostQueueSize;
		this.k = k;
		this.hostsNodes = new NetworkNode[nHostsMultiplier * k * ((int) Math.pow(((double) k)/2, 2))];
		this.aggregationSwitchesPerPod = new HashSet[k];
		this.edgeSwitchesPerPod = new HashSet[k];
		this.edgeSwitchesSet = new HashSet<>();
		this.coreSwitchesSet = new HashSet<>();
		this.edgeSwitchesPods = new HashMap<>();
		for (int i = 0; i < k; i++) {
			this.aggregationSwitchesPerPod[i] = new HashSet<>();
			this.edgeSwitchesPerPod[i] = new HashSet<>();
		}
		init();
	}

	private void createSwitchConnection(NetworkNode first, NetworkNode second, double rate) {
		networkingSystem.createLinkWithPriorityScheduling(first, second, rate, propagationDelay, switchesQueueSizes);
		networkingSystem.createLinkWithPriorityScheduling(second, first, rate, propagationDelay, switchesQueueSizes);
	}

	private void createHostConnection(NetworkNode hostNode, NetworkNode switchNode) {
		networkingSystem.createLinkWithPriorityScheduling(switchNode, hostNode, edgeLinkRate, propagationDelay, switchesQueueSizes);
		networkingSystem.createLinkWithPriorityScheduling(hostNode, switchNode, edgeLinkRate, propagationDelay, new double[]{hostQueueSize});
	}

	protected void init() {
		NetworkNode[] coreSwitches = new NetworkNode[(int) Math.pow(((double) k)/2, 2)];
		NetworkNode[] edgeSwitches = new NetworkNode[k * k / 2];
		NetworkNode[] aggregationSwitches = new NetworkNode[k * k / 2];
		Host[] hosts = new Host[nHostsMultiplier * k * ((int) Math.pow(((double) k)/2, 2))];

		// TODO: change to local vars
		this.network = networkingSystem.createNetwork();

		// Creating (k/2)^2 core switches
		for(int i = 0; i < Math.pow(((double) k)/2, 2); i++) {
			coreSwitches[i] = networkingSystem.createNode(network, "Core #" + i);
			coreSwitchesSet.add(coreSwitches[i].getLinkNode());
			coreSwitchesSet.add(coreSwitches[i].getQueueNode());
		}

		// Keep track of the ID of the next server to create
		int hostId = 0;

		// Creating the k pods
		for(int podId = 0; podId < k; podId++) {
			// Creating k/2 edge/aggregation switches
			for(int switchId = 0; switchId < k/2; switchId++) {
				NetworkNode edgeSwitch = networkingSystem.createNode(network, "Pod #" + podId + " Edge #" + switchId);
				NetworkNode aggregationSwitch = networkingSystem.createNode(network, "Pod #" + podId + " Agg #" + switchId);

				edgeSwitches[podId * k/2 + switchId]        = edgeSwitch;
				aggregationSwitches[podId * k/2 + switchId] = aggregationSwitch;
				edgeSwitchesPerPod[podId].add(edgeSwitch.getLinkNode());
				edgeSwitchesPerPod[podId].add(edgeSwitch.getQueueNode());
				edgeSwitchesSet.add(edgeSwitch.getQueueNode());
				edgeSwitchesSet.add(edgeSwitch.getLinkNode());
				edgeSwitchesPods.put(edgeSwitch.getQueueNode(), podId);
				edgeSwitchesPods.put(edgeSwitch.getLinkNode(), podId);
				aggregationSwitchesPerPod[podId].add(aggregationSwitch.getLinkNode());
				aggregationSwitchesPerPod[podId].add(aggregationSwitch.getQueueNode());
			}

			for(int aggSwitchId = 0; aggSwitchId < k/2; aggSwitchId++) {
				// Connect aggregation switches to the core switches
				// Each agg switch connects to k/2 cores switches
				for(int coreSwitchConnectionId = 0; coreSwitchConnectionId < k/2; coreSwitchConnectionId++)
					createSwitchConnection(coreSwitches[aggSwitchId * k/2 + coreSwitchConnectionId], aggregationSwitches[podId * k/2 + aggSwitchId], coreLinkRate);

				// Connect the edge/aggregation switches together
				for(int edgeSwitchId = 0; edgeSwitchId < k/2; edgeSwitchId++)
					createSwitchConnection(edgeSwitches[podId * k/2 + edgeSwitchId], aggregationSwitches[podId * k / 2 + aggSwitchId], aggregationLinkRate);
			}

			// Connect the edge switches to servers
			for(int edgeSwitchId = 0; edgeSwitchId < k/2; edgeSwitchId++) {
				for(int serverId = 0; serverId < nHostsMultiplier * k/2; serverId++, hostId++) {
					// Create servers
					hosts[hostId] = networkingSystem.createHost(network, "Pod #" + podId + " Server #" + (edgeSwitchId * nHostsMultiplier * k/2 + serverId));
					int incrementedHostId = hostId + 1;
					hostsNodes[hostId] = networkingSystem.addInterface(hosts[hostId], new NetworkInterface("eth0",
							"aa:aa:" + Integer.toHexString((incrementedHostId / 255 / 255 / 255) % 255) + ":" + Integer.toHexString((incrementedHostId / 255 / 255) % 255) + ":" + Integer.toHexString((incrementedHostId / 255) % 255) + ":" + Integer.toHexString(incrementedHostId % 255),
							(incrementedHostId / 255 / 255 / 255) % 255 + "." + (incrementedHostId / 255 / 255) % 255 + "." + (incrementedHostId / 255) % 255 + "." + incrementedHostId % 255));

					// Connect
					createHostConnection(hostsNodes[hostId], edgeSwitches[podId * k/2 + edgeSwitchId]);
				}
			}
		}
	}

	/**
	 * Computes the equal-length shortest paths between each pair of Host Interface
	 */
	public void computeEqualLengthShortestPaths() {
		// keep paths per source edge switch and destination edge swith because if we increase
		// the number of servers per edge switch, this has to be done a LOT
		edgesSwitchesSPs = new HashMap<>();

		for (Node src : this.network.getLinkGraph().getNodes()) {
			if (edgeSwitchesSet.contains(src)) {
				for (Node dst : this.getNetwork().getLinkGraph().getNodes()) {
					if (dst != src && edgeSwitchesSet.contains(dst)) {
						Pair<Node, Node> edgeSwitchPair = new Pair<>(src, dst);
						if (!edgesSwitchesSPs.containsKey(edgeSwitchPair)) {
							edgesSwitchesSPs.put(edgeSwitchPair, computeEqualLengthShortestPaths(src, dst));
						}
					}
				}
			}
		}
	}

	/**
	 * Get the equal-length SPs between a pair of hosts
	 * @param source source host interface node
	 * @param destination destination host interface node
	 * @return a list of paths
	 */
	public List<Edge[]> getEqualLengthShortestPaths(Node source, Node destination) {
		List<Edge[]> result = new LinkedList<>();
		if (!networkingSystem.isAHost(network, source) || !networkingSystem.isAHost(network, destination))
			throw new RuntimeException("the computeEqualLengthShortestPaths method can only be called with Nodes belonging to Host interfaces");

		Node srcEdgeSwitch = source.getOutgoingConnections().get(0).getDestination();
		Node dstEdgeSwitch = destination.getIncomingConnections().get(0).getSource();
		if (srcEdgeSwitch == dstEdgeSwitch) {
			Edge[] finalPath = new Edge[2];
			finalPath[0] = source.getOutgoingConnections().get(0);
			finalPath[1] = destination.getIncomingConnections().get(0);
			result.add(finalPath);
		} else {
			Pair<Node, Node> edgeSwitchPair = new Pair<>(srcEdgeSwitch, dstEdgeSwitch);
			if (!edgesSwitchesSPs.containsKey(edgeSwitchPair))
				throw new RuntimeException("you should call computeEqualLengthShortestPaths before calling this method");

			List<List<Edge>> paths = edgesSwitchesSPs.get(edgeSwitchPair);
			for (List<Edge> path : paths) {
				Edge[] finalPath = new Edge[path.size() + 2];
				int i = 0;
				finalPath[i++] = source.getOutgoingConnections().get(0);
				for (Edge edge : path) {
					finalPath[i++] = edge;
				}
				finalPath[i] = destination.getIncomingConnections().get(0);
				result.add(finalPath);
			}
		}

		return result;
	}

	private List<List<Edge>> computeEqualLengthShortestPaths(Node source, Node destination) {
		if (!edgeSwitchesSet.contains(source) || !edgeSwitchesSet.contains(destination))
			throw new RuntimeException("the computeEqualLengthShortestPaths method can only be called with Nodes belonging to edge switches");
		if (source.getGraph() != this.network.getLinkGraph() || destination.getGraph() != this.network.getLinkGraph())
			throw new RuntimeException("the computeEqualLengthShortestPaths method can only be called with link-level nodes");

		int sourcePodId = edgeSwitchesPods.get(source);
		int destPodId = edgeSwitchesPods.get(destination);

		List<List<Edge>> paths = new LinkedList<>();
		paths.add(new LinkedList<>());
		for(Node lastNode = null; lastNode != destination; lastNode = paths.get(0).get(paths.get(0).size() - 1).getDestination()) {
			List<List<Edge>> newPaths = new LinkedList<>();
			for (List<Edge> path : paths) {
				newPaths.addAll(expandPath(sourcePodId, destPodId, path, source, destination));
			}
			paths = newPaths;
		}


		return paths;
	}

	// expand a path by one hop
	private List<List<Edge>> expandPath(int sourcePodId, int destPodId, List<Edge> partialPath, Node source, Node destination) {
		List<List<Edge>> result = new LinkedList<>();
		Node lastNode = (partialPath.size() == 0) ? source : partialPath.get(partialPath.size() - 1).getDestination();
		if (edgeSwitchesPerPod[sourcePodId].contains(lastNode) || edgeSwitchesPerPod[destPodId].contains(lastNode)) {
			// add all aggregation switch of this pod
			for (Edge edge : lastNode.getOutgoingConnections()) {
				if (aggregationSwitchesPerPod[sourcePodId].contains(edge.getDestination())) {
					ArrayList<Edge> newPath = new ArrayList<>(partialPath);
					newPath.add(edge);
					result.add(newPath);
				}
			}
		} else if (aggregationSwitchesPerPod[sourcePodId].contains(lastNode) || aggregationSwitchesPerPod[destPodId].contains(lastNode)) {
			// go towards core switch if we have to change pod and we do not come from a core switch
			if (sourcePodId != destPodId && (partialPath.size() == 0 || !coreSwitchesSet.contains(partialPath.get(partialPath.size() - 1).getSource()))) {
				// add all core switches
				for (Edge edge : lastNode.getOutgoingConnections()) {
					if (coreSwitchesSet.contains(edge.getDestination())) {
						ArrayList<Edge> newPath = new ArrayList<>(partialPath);
						newPath.add(edge);
						result.add(newPath);
					}
				}
			} else {
				// add the destination edge switch and that's fine
				for (Edge edge : lastNode.getOutgoingConnections()) {
					if (edge.getDestination() == destination) {
						partialPath.add(edge);
						result.add(partialPath);
						break;
					}
				}
			}
		} else if (coreSwitchesSet.contains(lastNode)) {
			// add all aggregation switches of the destination pod
			for (Edge edge : lastNode.getOutgoingConnections()) {
				if (aggregationSwitchesPerPod[destPodId].contains(edge.getDestination())) {
					ArrayList<Edge> newPath = new ArrayList<>(partialPath);
					newPath.add(edge);
					result.add(newPath);
				}
			}
		} else {
			throw new RuntimeException("that's not possible!");
		}

		return result;
	}

	@Override
	public Network getNetwork() {
		return network;
	}

	@Override
	public NetworkNode[] getNodesAllowedToSend() {
		return hostsNodes;
	}

	@Override
	public NetworkNode[] getNodesAllowedToReceive() {
		return hostsNodes;
	}
}