package Simulator;

import Helper.Pair;
import Network.Link;
import Network.Node;
import org.omg.CORBA.INITIALIZE;

import java.lang.reflect.Array;
import java.util.*;

public class RoutingAlgorithm {

    public Map<Integer, GraphNode> graphNodes = new HashMap();  //same ID with the original node
    double [] path1Distance;
    double [] path2Distance;
    double [] path3Distance;
    int [] path1Previous;
    int [] path2Previous;
    int [] path3Previous;
    int [] levelOneVisitedNodes;
    int [] levelTwoVisitedNodes;
    int [] levelThreeVisitedNodes;

    public void RoutingAlgorithm(){

    }

    public void constructGraphNodes(Map<Integer, Node> networkNodes) {
        for (int n:networkNodes.keySet()) {
            graphNodes.put(n , new GraphNode(networkNodes.get(n).getID())); //nodeID - grapNode with the sameID
        }
    }

    public void runDijkstra (Node initialCode) {
        PriorityQueue <GraphNode>heap = new PriorityQueue<GraphNode>(); //Holds next node which has the minimum distance
        GraphNode root = graphNodes.get(initialCode.getID()); //root node which 3-path dijsktra runs
        GraphNode currentNode; //The node that is being examined
        heap.add(root);

        //**********INITIALIZE*********//
        path1Distance = new double[graphNodes.size()];
        path2Distance = new double[graphNodes.size()];
        path3Distance = new double[graphNodes.size()];

        path1Previous = new int[graphNodes.size()];
        path2Previous = new int[graphNodes.size()];
        path3Previous = new int[graphNodes.size()];

        levelOneVisitedNodes = new int[graphNodes.size()];
        levelTwoVisitedNodes = new int[graphNodes.size()];
        levelThreeVisitedNodes = new int[graphNodes.size()];

        for(int k =0 ; k<graphNodes.size(); k++) {
            path1Distance[k]= Double.MAX_VALUE;
            path2Distance[k]= Double.MAX_VALUE;
            path3Distance[k]= Double.MAX_VALUE;
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

        while(!heap.isEmpty()) {
            currentNode = heap.poll();
            //A node can be visited at most 3 times while dijsktra-3 running
            if(levelOneVisitedNodes[currentNode.ID]==0 || levelTwoVisitedNodes[currentNode.ID]==0 || levelThreeVisitedNodes[currentNode.ID]==0) {
                //For each edge of current node
                for (Link e: currentNode.interfaces ) {
                    //New distance to neighbors which is passing through the current node
                    double dist = (currentNode.dijDist + e.getCost());
                    //Check if the current node was reached via the current edge e
                    //If so, do not compare distance because it includes a loop
                    if(currentNode.dijPrev != e.getSecondNode().getID()) {
                        //Check neighbor's path1 distance is more than new calculated distance
                        if(dist < path1Distance[e.getSecondNode().getID()]) {
                            if(path1Previous[e.getSecondNode().getID()]!=-1) {
                                //If there exist a previously calculated value then shift the path informations
                                //Path 2 Info to Path 3 and Path 1 Info to Path 2
                                path3Distance[e.getSecondNode().getID()]= path2Distance[e.getSecondNode().getID()];
                                path3Previous[e.getSecondNode().getID()]= path2Previous[e.getSecondNode().getID()];
                                path2Distance[e.getSecondNode().getID()]= path1Distance[e.getSecondNode().getID()];
                                path2Previous[e.getSecondNode().getID()]= path1Previous[e.getSecondNode().getID()];
                            }
                            path1Distance[e.getSecondNode().getID()]= dist;
                            path1Previous[e.getSecondNode().getID()]=e.getFirstNode().getID();
                            heap.add(new GraphNode(graphNodes.get(e.getSecondNode().getID()),dist,e.getFirstNode().getID(),1));
                        }
                        //Check neighbor's path2 distance is more than new calculated distance
                        else if(dist < path2Distance[e.getSecondNode().getID()]) {
                            if(path2Previous[e.getSecondNode().getID()]!=-1) {
                                //If there exist a previously calculated value then shift the path informations
                                //Path 2 Info to Path 3
                                path3Distance[e.getSecondNode().getID()]= path2Distance[e.getSecondNode().getID()];
                                path3Previous[e.getSecondNode().getID()]= path2Previous[e.getSecondNode().getID()];
                            }
                            path2Distance[e.getSecondNode().getID()]= dist;
                            path2Previous[e.getSecondNode().getID()]=e.getFirstNode().getID();
                            heap.add(new GraphNode(graphNodes.get(e.getSecondNode().getID()),dist,e.getFirstNode().getID(),2));
                        }
                        //Check neighbor's path3 distance is more than new calculated distance
                        else if (dist < path3Distance[e.getSecondNode().getID()]) {
                            path3Distance[e.getSecondNode().getID()]= dist;
                            path3Previous[e.getSecondNode().getID()]=e.getFirstNode().getID();
                            heap.add(new GraphNode(graphNodes.get(e.getSecondNode().getID()),dist,e.getFirstNode().getID(),3));
                        }
                    }
                }
            }
            if(currentNode.dijLevel == 0 || currentNode.dijLevel==1) {
                levelOneVisitedNodes[currentNode.ID]+=1;
            } else if(currentNode.dijLevel == 2) {
                levelTwoVisitedNodes[currentNode.ID]+=1;
            } else {
                levelThreeVisitedNodes[currentNode.ID]+=1;
            }
        }

        System.out.println("Path1: "+ Arrays.toString(path1Previous) + "\n" +
                           "Path2: "+ Arrays.toString(path2Previous) + "\n" +
                           "Path3: "+ Arrays.toString(path3Previous) + "\n");

        System.out.println("Path1: "+ Arrays.toString(path1Distance) + "\n" +
                "Path2: "+ Arrays.toString(path2Distance) + "\n" +
                "Path3: "+ Arrays.toString(path3Distance) + "\n");



    }




    private class GraphNode implements Comparable {
        int ID;
        double dijDist = 0;
        int dijPrev = 0;
        int dijLevel =0;
        ArrayList<Integer> neighbours = new ArrayList<>(); // Keeps all incoming and outgoing channels information
        ArrayList<Link> interfaces = new ArrayList<>();

        private GraphNode (int ID) {
            this.ID = ID;
            findNeighbors();
            findInterfaces();
        }

        //copy constructor to be used in 3th degree dijsktra algorithm
        public GraphNode(GraphNode n, double newDijDist, int newDijPrev, int newDijLevel) {
            this.ID = n.ID;
            this.dijDist =  newDijDist;
            this.dijPrev = newDijPrev;
            this.dijLevel = newDijLevel;
            this.interfaces = n.interfaces;
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

        public void findInterfaces() {
            Pair<Integer,Integer> keyPair;
            for(int i=0; i< neighbours.size(); i++){
                keyPair = new Pair<>(this.ID,neighbours.get(i));
                interfaces.add(Simulator.newtworkLinks.get(keyPair));
        }
    }

;
    }

}
