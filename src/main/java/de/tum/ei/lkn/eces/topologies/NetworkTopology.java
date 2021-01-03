package de.tum.ei.lkn.eces.topologies;

import de.tum.ei.lkn.eces.network.Network;
import de.tum.ei.lkn.eces.network.NetworkNode;

/**
 * A network topology defines a network and the list of nodes that can send and receive.
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public interface NetworkTopology {
	Network getNetwork();
	NetworkNode[] getNodesAllowedToSend();
	NetworkNode[] getNodesAllowedToReceive();
}
