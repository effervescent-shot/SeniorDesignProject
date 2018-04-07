package Simulator;
import ICN.FIB;
import Network.*;
import Helper.*;
import kPath.Graph;
import kPath.Path;
import kPath.VariableGraph;
import kPath.abstraction.BaseGraph;
import kPath.abstraction.BaseVertex;
import kPath.shortestpaths.YenTopKShortestPathsAlg;


import java.lang.reflect.Array;
import java.util.*;

public class Simulator {
    public static Map<Integer, Node> networkNodes = new HashMap();
    public static Map<Pair<Integer, Integer>, Link> newtworkLinks = new HashMap();
    public RoutingAlgorithm routingAlgorithm;

    public Simulator() {
        routingAlgorithm = new RoutingAlgorithm();
    }

    public void InitializeRoutingAlgorithm() {
        routingAlgorithm.constructGraphNodes(this.networkNodes);
    }

    public void RunDijkstra(){
        for (Node n: networkNodes.values()) {
            routingAlgorithm.runDijkstra(n);
        }
    }

    public void buildPaths(Graph graph) {


        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);




        for (Node n: networkNodes.values()) {
            for (Node m: networkNodes.values()) {
                if(n.getID() != m.getID()){
                    List<Path> shortest_paths_list = yenAlg.getShortestPaths(
                            graph.getVertex(n.getID()), graph.getVertex(m.getID()), 3);
                    addPath(shortest_paths_list, n.getID(), m.getID());
                }
            }
        }
    }

    public void addPath(List<Path> paths, int source, int target ) {
        Node n = networkNodes.get(source);
        SimPath path1 = new SimPath(paths.get(0).getPathList());
        SimPath path2 = new SimPath(paths.get(1).getPathList());
        SimPath path3 = new SimPath(paths.get(2).getPathList());
        n.setFibRow(target, 1 , path1);
        n.setFibRow(target, 2 , path2);
        n.setFibRow(target, 3 , path3);
    }

    public void updateEdgeCosts(VariableGraph graph) {

    }

}
