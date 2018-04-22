package Simulator;
import ICN.Prefix;
import Network.*;
import Helper.*;
import kPath.Graph;
import kPath.Path;

import kPath.shortestpaths.YenTopKShortestPathsAlg;
import java.util.*;

import static Enums.EventType.RUN_DIJKSTRA;

public class Simulator {
    public static Map<Integer, Node> networkNodes = new HashMap();
    public static Map<Pair<Integer, Integer>, Link> newtworkLinks = new HashMap();
    public static Map<String, Prefix> networkPrefixes = new HashMap<>();
    public static PriorityQueue<Event> eventQueue;
    private static double MAX_SIM_TIME;
    private static double SimTime;  //milisecond

    public Simulator(double max_sim_time){
        this.MAX_SIM_TIME = max_sim_time;
        SimTime = 0;
    }


    public void runSimulation(Graph graph, int pathDegree){
        buildPaths(graph, pathDegree);

            ///////// initialize initial interests //////////
        while(!eventQueue.isEmpty() && SimTime < MAX_SIM_TIME) {
            Event e = (Event)eventQueue.poll();
            if(e.getEventType() == RUN_DIJKSTRA) {
                updateEdgeCosts(graph);
                buildPaths(graph, pathDegree);
            } else {
                e.runEvent();
            }
        }


    }

    public static void buildPaths(Graph graph, int pathDegree) {
        updateEdgeCosts(graph);

        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
        for (Node n: networkNodes.values()) {
            for (Node m: networkNodes.values()) {
                if(n.getID() != m.getID()){
                    List<Path> shortest_paths_list = yenAlg.getShortestPaths(
                            graph.getVertex(n.getID()), graph.getVertex(m.getID()),pathDegree);
                    addPath(shortest_paths_list, n.getID(), m.getID());
                }
            }
        }
        //networkNodes.get(7).getFib().FIB_toString();
    }

    public static void addPath(List<Path> paths, int source, int target ) {
        Node n = networkNodes.get(source);
        SimPath path1 = new SimPath(paths.get(0).getPathList(), paths.get(0).getWeight());
        SimPath path2 = new SimPath(paths.get(1).getPathList(),paths.get(1).getWeight());
        SimPath path3 = new SimPath(paths.get(2).getPathList(), paths.get(2).getWeight());
        n.setFibRow(target,path1,path2,path3);
    }

    public static void updateEdgeCosts(Graph graph) {
        for (Link l: newtworkLinks.values() ) {
            l.updateLoad();
            l.resetLoad();
            graph.vertexPairWeightIndex.put(
                    new Pair<Integer, Integer>(l.getFirstNode().getID(),l.getSecondNode().getID()),l.getCost());
        }


    }

}
