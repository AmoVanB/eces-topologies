package de.tum.ei.lkn.eces.topologies.networktopologies;

import de.tum.ei.lkn.eces.network.Network;
import de.tum.ei.lkn.eces.network.NetworkNode;
import de.tum.ei.lkn.eces.network.NetworkingSystem;

/**
 * The TRB topology defined in
 * "Unicast QoS Routing Algorithms for SDN: A Comprehensive Survey and Performance Evaluation"
 * JW Guck, A Van Bemten, M Reisslein, W Kellerer
 * IEEE Communications Surveys &amp; Tutorials
 * 2017
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public class TwoRingBottleneck extends OneRingBottleneck {
	// the second ring introduced by TRB
	NetworkNode ring2[];

	public TwoRingBottleneck(NetworkingSystem networkingSystem, Network network, int m, int n, double rate, double delay, double queueSize) {
		super(networkingSystem,network, m, n, rate, delay, queueSize);
	}

	public TwoRingBottleneck(NetworkingSystem networkingSystem,Network network,  int m, int n, double rate, double delay, double[] queueSize) {
		super(networkingSystem,network, m, n, rate, delay, queueSize);
	}

	public TwoRingBottleneck(NetworkingSystem networkingSystem,Network network,  int m, int n, double rate, double delay, double[] queueSize, double[] weight) {
		super(networkingSystem,network, m, n, rate, delay, queueSize, weight);
	}

	public TwoRingBottleneck(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double queueSize) {
		super(networkingSystem, m, n, rate, delay, queueSize);
	}

	public TwoRingBottleneck(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double[] queueSize) {
		super(networkingSystem, m, n, rate, delay, queueSize);
	}

	public TwoRingBottleneck(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double[] queueSize, double[] weight) {
		super(networkingSystem, m, n, rate, delay, queueSize, weight);
	}

	@Override
	protected void createNodes(NetworkingSystem networkingSystem) {

		super.createNodes(networkingSystem);
		ring2 = new NetworkNode[m + 1];

		ring2[m] = networkingSystem.createNode(network);

		for(int i = 0; i < m; i++)
			ring2[i] = networkingSystem.createNode(network);
	}

	@Override
	protected void createLinks(NetworkingSystem networkingSystem) {
		super.createLinks(networkingSystem);

		createLink(networkingSystem, head, ring2[ring.length-1]);
		createLink(networkingSystem, ring2[ring2.length-1], ring2[0]);
		createLink(networkingSystem, ring2[ring2.length-1], ring2[ring2.length-2]);
		for(int i = 0; i < m; i++) {
			if(i != 0)
				createLink(networkingSystem, ring2[i-1],ring2[i]);

			createLink(networkingSystem, ring2[i], branches[i][branches[i].length -1]);
		}
	}
}
