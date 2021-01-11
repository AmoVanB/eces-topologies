# Topologies

This repository implements the creation of well-known network topologies using the ECES [network library](https://github.com/AmoVanB/eces-network).

The module supports the topologies of the [Topology Zoo](http://www.topology-zoo.org/) and the ORB, TRR, TRB, and GR topologies defined in [*"Unicast QoS Routing Algorithms for SDN: A Comprehensive Survey and Performance Evaluation", JW Guck, A Van Bemten, M Reisslein, W Kellerer. 2017*](https://mediatum.ub.tum.de/doc/1420144/file.pdf).

Additionally, the module can import topologies from GML files.

## Usage

The project can be downloaded from maven central using:
```xml
<dependency>
  <groupId>de.tum.ei.lkn.eces</groupId>
  <artifactId>topologies</artifactId>
  <version>X.Y.Z</version>
</dependency>
```

## Examples

```java
int k = 4;
Controller controller = new Controller();
GraphSystem graphSystem = new GraphSystem(controller);
NetworkingSystem networkingSystem = new NetworkingSystem(controller, graphSystem);
FatTree fatTree = new FatTree(networkingSystem, k, 1, 10.0, 10.0, 10.0, 10.0, new double[]{1, 1, 1}, 1);
```

See [tests](src/test) and [other ECES repositories using this library](https://github.com/AmoVanB/eces-topologies/network/dependents) for other simple examples.
