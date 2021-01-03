package de.tum.ei.lkn.eces.topologies.networktopologies;

import de.tum.ei.lkn.eces.network.Network;
import de.tum.ei.lkn.eces.network.NetworkNode;
import de.tum.ei.lkn.eces.network.NetworkingSystem;
import de.tum.ei.lkn.eces.topologies.NetworkTopology;

/**
 * The GR topology defined in
 * "Unicast QoS Routing Algorithms for SDN: A Comprehensive Survey and Performance Evaluation"
 * JW Guck, A Van Bemten, M Reisslein, W Kellerer
 * IEEE Communications Surveys &amp; Tutorials
 * 2017
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public class GridRandom implements NetworkTopology {
	private Network network;
	private NetworkNode[][] grid;
	private NetworkNode[] nodesToSend;
	// grid size
	private int m;
	private int n;
	// PQ or WFQ
	private String linkType;
	// for each link
	private double rate;
	private double delay;
	private double[] queueSize;
	// WFQ weights
	private double[] weight;

	public GridRandom(NetworkingSystem networkingSystem,Network network, int m, int n, double rate, double delay, double queueSize) {
		linkType = "";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = new double[] { queueSize };

		init(networkingSystem,network, m, n);
	}

	public GridRandom(NetworkingSystem networkingSystem, Network network, int m, int n, double rate, double delay, double[] queueSize) {
		linkType = "PQ";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = queueSize;

		init(networkingSystem,network, m, n);
	}

	public GridRandom(NetworkingSystem networkingSystem, Network network, int m, int n, double rate, double delay, double[] queueSize, double[] weight) {
		linkType = "WFQ";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = queueSize;
		this.weight = weight;

		init(networkingSystem,network, m, n);
	}

	public GridRandom(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double queueSize) {
		linkType = "";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = new double[] {queueSize};

		init(networkingSystem, m, n);
	}

	public GridRandom(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double[] queueSize) {
		linkType = "PQ";
		this.rate = rate;
		this.delay = delay;
		this.queueSize = queueSize;

		init(networkingSystem, m, n);
	}

	public GridRandom(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double queueSize[], double weight[]){
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

	protected void init(NetworkingSystem networkingSystem, Network network, int m, int n) {
		this.network = network;
		grid = new NetworkNode[m][n];
		nodesToSend = new NetworkNode[m * n];
		this.m = m;
		this.n = n;
		createNodes(networkingSystem);
		createLinks(networkingSystem);
	}

	protected void createNodes(NetworkingSystem networkingSystem) {
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				grid[i][j] = networkingSystem.createNode(network);
				nodesToSend[i * n + j] = grid[i][j];
			}
		}
	}

	protected void createLinks(NetworkingSystem networkingSystem) {
		for(int i = 0; i < m; i++)
			for(int j = 1; j < n; j++)
				createBidirectionalLink(networkingSystem, grid[i][j-1], grid[i][j]);

		for(int i = 1; i < m; i++)
			for(int j = 0; j < n; j++)
				createBidirectionalLink(networkingSystem, grid[i-1][j], grid[i][j]);
	}

	private void createBidirectionalLink(NetworkingSystem networkingSystem, NetworkNode n1, NetworkNode n2){
		createUnidirectionalLink(networkingSystem, n1, n2);
		createUnidirectionalLink(networkingSystem, n2, n1);
	}

	private void createUnidirectionalLink(NetworkingSystem networkingSystem, NetworkNode n1, NetworkNode n2){
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
		return nodesToSend;
	}
}

