package de.tum.ei.lkn.eces.topologies.networktopologies;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.gml.GMLReader;
import de.tum.ei.lkn.eces.core.Controller;
import de.tum.ei.lkn.eces.network.Network;
import de.tum.ei.lkn.eces.network.NetworkNode;
import de.tum.ei.lkn.eces.network.NetworkingSystem;
import de.tum.ei.lkn.eces.topologies.NetworkTopology;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class is used to generate topologies from GML files.
 * 
 * @author Mathias Gopp
 * @author Jochen Guck
 * @author Amaury Van Bemten
 **/

public class GmlTopology implements NetworkTopology {
	private Network network;
	private NetworkNode[] NodesAllowedToSend;
	private NetworkNode[] NodesAllowedToReceive;

	public GmlTopology(Controller controller,NetworkingSystem networkingSystem, String topologyString) throws IOException {
		this(controller,networkingSystem, new ByteArrayInputStream(topologyString.getBytes()));
	}

	public GmlTopology(Controller controller,NetworkingSystem networkingSystem, File topologyFile) throws IOException {
		this(controller,networkingSystem, new FileInputStream(topologyFile));
	}

	public GmlTopology(Controller controller,NetworkingSystem networkingSystem, InputStream inputstream) throws IOException {
		this(controller,networkingSystem,128000000, new double[]{60000}, inputstream);
	}
	public GmlTopology(Controller controller, NetworkingSystem networkingSystem, double rate, double[] queueSize, String topologyString) throws IOException {
		this(controller,networkingSystem,rate,queueSize, new ByteArrayInputStream(topologyString.getBytes()));
	}

	public GmlTopology(Controller controller, NetworkingSystem networkingSystem, double rate, double[] queueSize, InputStream inputstream) throws IOException {
		Graph graph = new TinkerGraph();
		GMLReader.inputGraph(graph, inputstream);
		inputstream.close();
		LinkedList<NetworkNode> send = new LinkedList<>();
		LinkedList<NetworkNode> receive = new LinkedList<>();
		network = networkingSystem.createNetwork();
		Map<Object,NetworkNode> nodeMap = new HashMap<>();
		Map<Object,Vertex> vertexMap = new HashMap<>();
		for(Vertex vertex : graph.getVertices()) {
			NetworkNode networkNode = networkingSystem.createNode(network, vertex.getProperty("label"));
			nodeMap.put(vertex.getId(), networkNode);
			vertexMap.put(vertex.getId(), vertex);
			if(vertex.getProperty("mode") == null || vertex.getProperty("mode").equals("both")) {
				send.add(networkNode);
				receive.add(networkNode);
			} else if(vertex.getProperty("mode").equals("send")) {
				send.add(networkNode);
			} else if(vertex.getProperty("mode").equals("receive")) {
				receive.add(networkNode);
			}
		}

		NodesAllowedToSend = send.toArray(new NetworkNode[send.size()]);
		NodesAllowedToReceive = receive.toArray(new NetworkNode[receive.size()]);
		for(Edge edge : graph.getEdges()){
			NetworkNode sourceNetworkNode = nodeMap.get(edge.getVertex(Direction.IN).getId());
			NetworkNode targetNetworkNode = nodeMap.get(edge.getVertex(Direction.OUT).getId());
			Vertex sourceVertex = vertexMap.get(edge.getVertex(Direction.IN).getId());
			Vertex targetVertex = vertexMap.get(edge.getVertex(Direction.OUT).getId());
			double propagationDelay = 0;
			try {
				propagationDelay = distFrom(
						Float.parseFloat(sourceVertex.getProperty("Latitude").toString()),
						Float.parseFloat(sourceVertex.getProperty("Longitude").toString()),
						Float.parseFloat(targetVertex.getProperty("Latitude").toString()),
						Float.parseFloat(targetVertex.getProperty("Longitude").toString())) / (299792458.0 / 3 * 2);
			}
			catch(NullPointerException e) {
				// In case these fields don't exist, we use 0 as propagation delay.
			}

			networkingSystem.createLinkWithPriorityScheduling(
					sourceNetworkNode,
					targetNetworkNode,
					rate,
					propagationDelay,
					queueSize
			);
			networkingSystem.createLinkWithPriorityScheduling(
					targetNetworkNode,
					sourceNetworkNode,
					rate,
					propagationDelay,
					queueSize
			);
		}
	}

	public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
		double earthRadius = 6371000; //meters
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		return (float) (earthRadius * c);
	}

	@Override
	public Network getNetwork() {
		return network;
	}

	@Override
	public NetworkNode[] getNodesAllowedToSend() {
		return NodesAllowedToSend;
	}

	@Override
	public NetworkNode[] getNodesAllowedToReceive() {
		return NodesAllowedToReceive;
	}
}