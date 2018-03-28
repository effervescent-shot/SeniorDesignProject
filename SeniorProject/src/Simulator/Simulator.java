package Simulator;
import Network.*;
import Helper.*;


import java.util.HashMap;
import java.util.Map;

public class Simulator {
    public static Map<Integer, Node> networkNodes = new HashMap();
    public static Map< Pair<Integer, Integer>, Link> newtworkLinks = new HashMap();
    public RoutingAlgorithm routingAlgorithm;

    public Simulator() {
        routingAlgorithm = new RoutingAlgorithm();
    }

    public void InitializeRoutingAlgorithm() {
        routingAlgorithm.constructGraphNodes(this.networkNodes);
    }

    public void RunDijkstra(){
        routingAlgorithm.runDijkstra(networkNodes.get(0));
    }


}
