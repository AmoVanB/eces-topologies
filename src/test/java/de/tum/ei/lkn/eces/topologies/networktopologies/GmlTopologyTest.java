package de.tum.ei.lkn.eces.topologies.networktopologies;

import de.tum.ei.lkn.eces.core.Controller;
import de.tum.ei.lkn.eces.graph.GraphSystem;
import de.tum.ei.lkn.eces.network.NetworkingSystem;
import de.tum.ei.lkn.eces.topologies.NetworkTopology;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test class checking that the Topology Zoo import works fine.
 * Topologies are not checked, just whether it runs smoothly.
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public class GmlTopologyTest {
	@Test
	public void GMLTopologyString() throws IOException {
		TopologyZoo.ABILENE.getTopologyContent();
	}

	@Test
	public void numberOfEdgesNodes() throws IOException {
		Controller controller = new Controller();
		GraphSystem graphSystem = new GraphSystem(controller);
		NetworkingSystem networkingSystem = new NetworkingSystem(controller, graphSystem);
		for(TopologyZoo topology : TopologyZoo.values()) {
			if(topology != TopologyZoo.NO) {
				NetworkTopology networkTopology = new GmlTopology(controller, networkingSystem, topology.getTopologyFile());
				int numEdges = networkTopology.getNetwork().getLinkGraph().getEdges().size();
				int numNodes = networkTopology.getNetwork().getLinkGraph().getNodes().size();
				assertEquals("The number of edges are not equal", numEdges, topology.getNumberOfEdges());
				assertEquals("The number of edges are not equal", numNodes, topology.getNumberOfNodes());
				networkingSystem.deleteNetwork(networkTopology.getNetwork());
			}
		}
	}
}
