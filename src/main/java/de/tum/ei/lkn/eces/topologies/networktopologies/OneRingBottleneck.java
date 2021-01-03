package de.tum.ei.lkn.eces.topologies.networktopologies;

import de.tum.ei.lkn.eces.network.Network;
import de.tum.ei.lkn.eces.network.NetworkNode;
import de.tum.ei.lkn.eces.network.NetworkingSystem;
import de.tum.ei.lkn.eces.topologies.NetworkTopology;

/**
 * The ORB topology defined in
 * "Unicast QoS Routing Algorithms for SDN: A Comprehensive Survey and Performance Evaluation"
 * JW Guck, A Van Bemten, M Reisslein, W Kellerer
 * IEEE Communications Surveys &amp; Tutorials
 * 2017
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public class OneRingBottleneck implements NetworkTopology {
	protected Network network;
	// Nodes (the head of topology one, ring ones, branches ones, and those that can send)
	protected NetworkNode head;
	protected NetworkNode[] ring;
	protected NetworkNode[][] branches;
	private NetworkNode[] nodesToSend;

	// grid size
	protected int m;
	private int n;
	// PQ or WFQ
	private String linkType;
	// For each link
	private double rate;
	private double delay;
	private double[] queueSize;
	// WFQ weights
	private double[] weight;

	public OneRingBottleneck(NetworkingSystem networkingSystem,Network network, int m, int n, double rate, double delay, double queueSize) {
		linkType = "";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = new double[] { queueSize };

		init(networkingSystem,network, m, n);
	}

	public OneRingBottleneck(NetworkingSystem networkingSystem, Network network, int m, int n, double rate, double delay, double[] queueSize) {
		linkType = "PQ";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = queueSize;

		init(networkingSystem,network, m, n);
	}

	public OneRingBottleneck(NetworkingSystem networkingSystem,Network network, int m, int n, double rate, double delay, double[] queueSize, double[] weight) {
		linkType = "WFQ";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = queueSize;
		this.weight = weight;

		init(networkingSystem,network, m, n);
	}

	public OneRingBottleneck(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double queueSize) {
		linkType = "";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = new double[] { queueSize };

		init(networkingSystem, m, n);
	}

	public OneRingBottleneck(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double[] queueSize) {
		linkType = "PQ";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = queueSize;

		init(networkingSystem, m, n);
	}

	public OneRingBottleneck(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double[] queueSize, double[] weight) {
		linkType = "WFQ";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = queueSize;
		this.weight = weight;

		init(networkingSystem, m, n);
	}

	protected void init(NetworkingSystem networkingSystem, int m, int n) {
		init(networkingSystem, networkingSystem.createNetwork(), m, n);
	}

	protected void init(NetworkingSystem networkingSystem,Network network, int m, int n) {
		this.network = network;
		ring = new NetworkNode[m + 1];
		branches = new NetworkNode[m][n];
		nodesToSend = new NetworkNode[m * n];
		this.m = m;
		this.n = n;
		createNodes(networkingSystem);
		createLinks(networkingSystem);
	}

	protected void createNodes(NetworkingSystem networkingSystem) {
		head = networkingSystem.createNode(network);
		ring[m] = networkingSystem.createNode(network);

		for(int i = 0; i < m; i++) {
			ring[i] = networkingSystem.createNode(network);

			for(int j = 0; j < n; j++) {
				branches[i][j] = networkingSystem.createNode(network);
				nodesToSend[i * n + j] = branches[i][j];
			}
		}
	}

	protected void createLinks(NetworkingSystem networkingSystem) {
		createLink(networkingSystem, head, ring[ring.length-1]);
		createLink(networkingSystem, ring[ring.length-1], ring[0]);
		createLink(networkingSystem, ring[ring.length-1], ring[ring.length-2]);
		for(int i = 0; i < m; i++) {
			if(i != 0)
				createLink(networkingSystem, ring[i-1],ring[i]);

			createLink(networkingSystem, ring[i], branches[i][0]);

			for(int j = 1; j < n; j++)
				createLink(networkingSystem, branches[i][j-1], branches[i][j]);
		}
	}

	protected void createLink(NetworkingSystem networkingSystem, NetworkNode n1, NetworkNode n2) {
		createULink(networkingSystem, n1, n2);
		createULink(networkingSystem, n2, n1);
	}

	private void createULink(NetworkingSystem networkingSystem, NetworkNode n1, NetworkNode n2) {
		switch(linkType) {
			case "PQ":
				networkingSystem.createLinkWithPriorityScheduling(n1,n2, rate, delay, queueSize);
				break;
			case "WFQ":
				networkingSystem.createLinkWithWFQScheduling(n1,n2, rate, delay, queueSize, weight);
				break;
			default:
				networkingSystem.createLink(n1, n2, rate, delay, queueSize[0]);
		}
	}

	@Override
	public Network getNetwork() {
		return network;
	}

	@Override
	public NetworkNode[] getNodesAllowedToSend() {
		return nodesToSend;
	}

	@Override
	public NetworkNode[] getNodesAllowedToReceive() {
		return new NetworkNode[]{ head };
	}
}
