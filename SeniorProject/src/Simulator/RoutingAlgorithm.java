package Simulator;

import Helper.Pair;
import Network.Link;
import Network.Node;
import org.omg.CORBA.INITIALIZE;

import java.util.*;

public class RoutingAlgorithm {

    private class GraphNode implements Comparable {
        int ID;
        int dijDist = 0;
        int dijPrev = 0;
        int dijLevel =0;
        ArrayList<Integer> neighbours = new ArrayList<>(); // Keeps all incoming and outgoing channels information

        private GraphNode (int ID) {
            this.ID = ID;
            findNeighbors();
        }

        @Override
        public int compareTo(Object o) {
            if(this.dijDist == ((GraphNode)o).dijDist) {
                return 0;
            } else if(this.dijDist > ((GraphNode)o).dijDist) {
                return 1;
            } else {
                return -1;
            }
        }

        public void findNeighbors() {
            Set<Pair<Integer, Integer>> keyPairs = Simulator.newtworkLinks.keySet();
            Object[] pairs  = keyPairs.toArray();
            for (int i =0; i<pairs.length; i++) {
                Pair<Integer,Integer> p = (Pair<Integer,Integer>)pairs[i];
                if(p.getLeft() == this.ID) {
                    this.neighbours.add(p.getRight()); //list of neighbor NodeIDs
                }
            }
        }
    }

;

    public static Map<Integer, GraphNode> graphNodes = new HashMap();  //same ID with the original node
    int [] path1Distance;
    int [] path2Distance;
    int [] path3Distance;
    int [] path1Previous;
    int [] path2Previous;
    int [] path3Previous;
    int [] levelOneVisitedNodes;
    int [] levelTwoVisitedNodes;
    int [] levelThreeVisitedNodes;



    public void RoutingAlgorithm(){

    }

    public void constructGraphNodes(Map<Integer, Node> simNodes) {
        for (int n:simNodes.keySet()) {
            graphNodes.put(n , new GraphNode(simNodes.get(n).getID())); //nodeID - grapNode with the sameID
        }
    }

    protected void runDijkstra (Node initialCode) {
        PriorityQueue <GraphNode>heap = new PriorityQueue<GraphNode>(); //Holds next node which has the minimum distance
        GraphNode root = graphNodes.get(initialCode.getID()); //root node which 3-path dijsktra runs
        GraphNode currentNode; //The node that is being examined
        heap.add(root);

        //**********INITIALIZE*********//
        path1Distance = new int[graphNodes.size()];
        path2Distance = new int[graphNodes.size()];
        path3Distance = new int[graphNodes.size()];
        path1Previous = new int[graphNodes.size()];
        path2Previous = new int[graphNodes.size()];
        path3Previous = new int[graphNodes.size()];
        levelOneVisitedNodes = new int[graphNodes.size()];
        levelTwoVisitedNodes = new int[graphNodes.size()];
        levelThreeVisitedNodes = new int[graphNodes.size()];
        for(int k =0 ; k<graphNodes.size(); k++) {
            path1Distance[k]= Integer.MAX_VALUE;
            path2Distance[k]= Integer.MAX_VALUE;
            path3Distance[k]= Integer.MAX_VALUE;
            path1Previous[k]= -1;
            path2Previous[k]= -1;
            path3Previous[k]= -1;
            levelOneVisitedNodes[k] = 0;
            levelTwoVisitedNodes[k] = 0;
            levelThreeVisitedNodes[k] = 0;
        }
        path1Previous[root.ID]=root.ID;
        path2Previous[root.ID]=root.ID;
        path3Previous[root.ID]=root.ID;
        path1Distance[root.ID]= 0;
        path2Distance[root.ID]= 0;
        path3Distance[root.ID]= 0;
        //*****************************//
    }

}
