package de.tum.ei.lkn.eces.topologies.networktopologies;

import de.tum.ei.lkn.eces.core.Controller;
import de.tum.ei.lkn.eces.graph.Edge;
import de.tum.ei.lkn.eces.graph.GraphSystem;
import de.tum.ei.lkn.eces.graph.Node;
import de.tum.ei.lkn.eces.network.Host;
import de.tum.ei.lkn.eces.network.Network;
import de.tum.ei.lkn.eces.network.NetworkNode;
import de.tum.ei.lkn.eces.network.NetworkingSystem;
import de.tum.ei.lkn.eces.network.util.NetworkInterface;
import de.tum.ei.lkn.eces.routing.algorithms.sp.ksp.KSPAlgorithm;
import de.tum.ei.lkn.eces.routing.algorithms.sp.ksp.yen.YenAlgorithm;
import de.tum.ei.lkn.eces.routing.proxies.ShortestPathProxy;
import de.tum.ei.lkn.eces.routing.requests.UnicastRequest;
import de.tum.ei.lkn.eces.routing.responses.Path;
import de.tum.ei.lkn.eces.topologies.NetworkTopology;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Test class checking that the Fat Tree topology shortest path
 * computation works fine.
 *
 * @author Amaury Van Bemten
 */
public class FatTreeTopologyTest {
	@Test
	public void testSPRuns() throws IOException {
		Controller controller = new Controller();
		GraphSystem graphSystem = new GraphSystem(controller);
		NetworkingSystem networkingSystem = new NetworkingSystem(controller, graphSystem);

		// just testing it runs for big stuff
		for (int k = 4; k < 9; k += 2) {
			for (int hostMultiplier = 1; hostMultiplier < 102; hostMultiplier += 50) {
				System.out.println("k = " + k + " hostMultiplier = " + hostMultiplier);
				FatTree fatTree = new FatTree(networkingSystem, k, hostMultiplier, 10.0, 10.0, 10.0, 10.0, new double[]{1, 1, 1}, 1);
				// test these values
				System.out.println(" " + fatTree.getNetwork().getHosts().size() + " hosts, " + (fatTree.getNetwork().getLinkGraph().getNodes().size() - fatTree.getNetwork().getHosts().size()) + " switches");
				assertEquals(hostMultiplier * Math.pow(k, 3) / 4, fatTree.getNetwork().getHosts().size(), 0);
				assertEquals((5 * Math.pow(k, 2) / 4 + hostMultiplier * Math.pow(k, 3) / 4), fatTree.getNetwork().getLinkGraph().getNodes().size(), 0);

				long start = System.currentTimeMillis();
				fatTree.computeEqualLengthShortestPaths();
				long finish = System.currentTimeMillis();
				System.out.println(" Computed kSPs in " + (finish - start) / 1e3 + " seconds");
			}
		}
	}

	@Test
	public void testSPsAreCorrect() throws IOException {
		Controller controller = new Controller();
		GraphSystem graphSystem = new GraphSystem(controller);
		NetworkingSystem networkingSystem = new NetworkingSystem(controller, graphSystem);

		for (int k = 4; k < 7; k += 2) {
			for (int hostMultiplier = 1; hostMultiplier < 5; hostMultiplier += 2) {
				if (k > 4 && hostMultiplier > 1)
					continue;
				System.out.println("k = " + k + " hostMultiplier = " + hostMultiplier);
				FatTree fatTree = new FatTree(networkingSystem, k, hostMultiplier, 10.0, 10.0, 10.0, 10.0, new double[]{1, 1, 1}, 1);
				// test these values
				System.out.println(" " + fatTree.getNetwork().getHosts().size() + " hosts, " + (fatTree.getNetwork().getLinkGraph().getNodes().size() - fatTree.getNetwork().getHosts().size()) + " switches");
				assertEquals(hostMultiplier * Math.pow(k, 3) / 4, fatTree.getNetwork().getHosts().size(), 0);
				assertEquals((5 * Math.pow(k, 2) / 4 + hostMultiplier * Math.pow(k, 3) / 4), fatTree.getNetwork().getLinkGraph().getNodes().size(), 0);

				long start = System.currentTimeMillis();
				fatTree.computeEqualLengthShortestPaths();
				long finish = System.currentTimeMillis();
				System.out.println(" Computed kSPs in " + (finish - start) / 1e3 + " seconds");

				// checking the computed KSPs
				System.out.println(" Checking kSPs");
				for (NetworkNode src : fatTree.getNodesAllowedToSend()) {
					for (NetworkNode dst : fatTree.getNodesAllowedToReceive()) {
						if (src == dst)
							continue;

						List<Edge[]> paths = fatTree.getEqualLengthShortestPaths(src.getLinkNode(), dst.getLinkNode());

						// Computing the ksps manually
						List<Edge[]> realPaths = new LinkedList<>();
						KSPAlgorithm yen = new YenAlgorithm(controller);
						yen.setProxy(new ShortestPathProxy());
						UnicastRequest kspRequest = new UnicastRequest(src.getLinkNode(), dst.getLinkNode());
						Iterator<Path> kSPsIterator = yen.iterator(kspRequest);

						// Get first shortest path
						Path firstPath;
						if (kSPsIterator.hasNext()) {
							firstPath = kSPsIterator.next();
							realPaths.add(firstPath.getPath());
						} else {
							// There's just nothing
							return;
						}

						int lastLength = firstPath.getPath().length;
						while (kSPsIterator.hasNext()) {
							Path nextPath = kSPsIterator.next();
							if (lastLength == nextPath.getPath().length) {
								realPaths.add(nextPath.getPath());
							} else {
								break;
							}
						}

						// sort to ensure comparison works
						realPaths.sort(Comparator.comparingInt(Arrays::hashCode));
						paths.sort(Comparator.comparingInt(Arrays::hashCode));
						assertEquals(realPaths.size(), paths.size());
						for (int i = 0; i < realPaths.size(); i++) {
							// check paths
							assertEquals(realPaths.get(i).length, paths.get(i).length);
							for (int j = 0; j < realPaths.get(i).length; j++) {
								assertEquals(realPaths.get(i)[j], paths.get(i)[j]);
							}
						}
					}
				}
			}
		}
	}
}
