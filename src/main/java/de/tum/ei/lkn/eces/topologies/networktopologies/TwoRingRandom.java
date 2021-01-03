package de.tum.ei.lkn.eces.topologies.networktopologies;

import de.tum.ei.lkn.eces.network.Network;
import de.tum.ei.lkn.eces.network.NetworkNode;
import de.tum.ei.lkn.eces.network.NetworkingSystem;

/**
 * The TRR topology defined in
 * "Unicast QoS Routing Algorithms for SDN: A Comprehensive Survey and Performance Evaluation"
 * JW Guck, A Van Bemten, M Reisslein, W Kellerer
 * IEEE Communications Surveys &amp; Tutorials
 * 2017
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public class TwoRingRandom extends TwoRingBottleneck {
	public TwoRingRandom(NetworkingSystem networkingSystem, Network network, int m, int n, double rate, double delay, double queueSize) {
		super(networkingSystem,network, m, n, rate, delay, queueSize);
	}

	public TwoRingRandom(NetworkingSystem networkingSystem,Network network,  int m, int n, double rate, double delay, double[] queueSize) {
		super(networkingSystem,network, m, n, rate, delay, queueSize);
	}

	public TwoRingRandom(NetworkingSystem networkingSystem,Network network,  int m, int n, double rate, double delay, double[] queueSize, double[] weight) {
		super(networkingSystem,network, m, n, rate, delay, queueSize, weight);
	}
	public TwoRingRandom(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double queueSize) {
		super(networkingSystem, m, n, rate, delay, queueSize);
	}

	public TwoRingRandom(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double[] queueSize) {
		super(networkingSystem, m, n, rate, delay, queueSize);
	}

	public TwoRingRandom(NetworkingSystem networkingSystem, int m, int n, double rate, double delay, double[] queueSize, double[] weight) {
		super(networkingSystem, m, n, rate, delay, queueSize, weight);
	}

	@Override
	public NetworkNode[] getNodesAllowedToReceive() {
		// The only difference with TRB is the nodes that can receive.
		return getNodesAllowedToSend();
	}
}
