package Simulator;
import Enums.EventType;
import Helper.Pair;
import Helper.SimPath;
import ICN.Prefix;
import Network.Link;
import Network.Node;
import Network.Packet;
import kPath.Graph;
import kPath.Path;
import kPath.shortestpaths.YenTopKShortestPathsAlg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;

import static Enums.EventType.RUN_DIJKSTRA;
import static Enums.EventType.UPDATE_LOAD;

public class Simulator {
    public static Map<Integer, Node> networkNodes = new HashMap();
    public static Map<Pair<Integer, Integer>, Link> networkLinks = new HashMap();
    public static Map<String, Prefix> networkPrefixes = new HashMap<>();
    public static ArrayList<Packet> allPackets = new ArrayList<>();
    public static PriorityQueue<Event> eventQueue = new PriorityQueue<>();


    private static long MAX_SIM_TIME;
    private static long SimTime;  //milisecond

    private static Random randomTime;
    private static Random randomNode;
    private static Random randomPrefix;

    private static int numDisj;

    public Simulator(long max_sim_time){
        this.MAX_SIM_TIME = max_sim_time;
        this.numDisj = (int)MAX_SIM_TIME/5000;
        SimTime = 0;
    }


    public void runSimulation(Graph graph, int pathDegree){
        //buildPaths(graph, pathDegree);
        ///////// initialize initial interests //////////

        //
        while(!eventQueue.isEmpty() && SimTime < MAX_SIM_TIME) {
            Event e = (Event)eventQueue.poll();
            SimTime = e.getTime();
            //System.out.println(e.toString());
            //System.out.println(e.getEventType()+ " time: " + e.getTime());
            if(e.getEventType() == RUN_DIJKSTRA) {
                //updateEdgeCosts(graph);
                //buildPaths(graph, pathDegree);
            } else if(e.getEventType() == UPDATE_LOAD) {
                for (Link l: networkLinks.values()) {
                    l.updateLoad();
                }
                //System.out.println("\n\n\n\n\n\n\n");
            } else {
                e.runEvent();
                //System.out.println(e.toString());
            }
        }
        /*for ( Packet p: allPackets) {
            System.out.println(p);
        }*/
        System.out.println(allPackets.size());
    }

    public static void buildPaths(Graph graph, int pathDegree) {
        updateEdgeCosts(graph);

        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
        for (Node n: networkNodes.values()) {
            for (Node m: networkNodes.values()) {
                if(n.getID() != m.getID()){
                    List<Path> shortest_paths_list = yenAlg.getShortestPaths(
                            graph.getVertex(n.getID()), graph.getVertex(m.getID()),pathDegree);
                    fillFIBtable(shortest_paths_list, n.getID(), m.getID(), pathDegree);
                }
            }
        }
        //networkNodes.get(7).getFib().FIB_toString();
    }

    public static void fillFIBtable(List<Path> paths, int source, int target, int pathDegree ) {
        Node n = networkNodes.get(source);
        if(pathDegree == 3) {
            SimPath path1 = new SimPath(paths.get(0).getPathList(), paths.get(0).getWeight());
            SimPath path2 = new SimPath(paths.get(1).getPathList(), paths.get(1).getWeight());
            SimPath path3 = new SimPath(paths.get(2).getPathList(), paths.get(2).getWeight());
            n.setFibRow(target,path1,path2,path3);
        }
        else if (pathDegree == 1) {
            SimPath path1 = new SimPath(paths.get(0).getPathList(), paths.get(0).getWeight());
            SimPath path2 = new SimPath(paths.get(0).getPathList(), paths.get(0).getWeight());
            SimPath path3 = new SimPath(paths.get(0).getPathList(), paths.get(0).getWeight());
            n.setFibRow(target,path1,path2,path3);
        }

    }

    public static void updateEdgeCosts(Graph graph) {
        for (Link l: networkLinks.values() ) {
            //l.updateLoad();  //Call in each second
            l.resetLoad();   //calculate avg load of last 5 second
            //System.out.println(l.getCost());
            graph.vertexPairWeightIndex.put(
                    new Pair<Integer, Integer>(l.getFirstNode().getID(),l.getSecondNode().getID()),l.getCost());
        }
    }

    public int getRandomNodeID(){
        return (int)Math.floor(0+(networkNodes.size()-0)*randomNode.nextDouble());
    }
    public int getRandomPrefixID(){
        return (int)Math.floor(0+(networkPrefixes.size()-0)*randomPrefix.nextDouble())+1;
    }

    private int PoissonDist(Double lamda) {
        return -1;
    }


    public long getRandomStartTime(){
        return (long)Math.floor(0+(MAX_SIM_TIME-0)*randomTime.nextDouble());
        //return (long) Math.min(MAX_SIM_TIME, randomTime.nextLong());
    }


    public void initialization(int numEvent, long timeSeed, long nodeSeed, long prefixSeed) {
        randomTime = new Random();
        randomTime.setSeed(timeSeed);

        randomNode = new Random();
        randomNode.setSeed(nodeSeed);

        randomPrefix = new Random();
        randomPrefix.setSeed(prefixSeed);



        while (numEvent > 0) {
                    init( getRandomNodeID(),getRandomStartTime(),
                            networkPrefixes.get("prefix"+getRandomPrefixID()));
            numEvent--;
        }

        int k = 1;
        while (k<numDisj){
            Event e = new Event( k*5000 ,EventType.RUN_DIJKSTRA);
            Simulator.eventQueue.add(e);
            k++;
        }

        int l = 1;
        while (l <= MAX_SIM_TIME/1000) { //How many seconds
            Event e = new Event( l*1000, EventType.UPDATE_LOAD);
            Simulator.eventQueue.add(e);
            l++;
        }

    }

    public void init(int nodeID,long time, Prefix prefix){
        Init_Interest_Data_Event e = new Init_Interest_Data_Event();
        e.setTime(time);
        e.setPrefix(prefix);
        e.setNodeID(nodeID);
        e.setEventType(EventType.INITIALIZE_INTEREST);
        Simulator.eventQueue.add(e);
    }

    public void resetVariables() {
        for (Link l : networkLinks.values()) {
            l.resetLink();
        }
        eventQueue.clear();
        allPackets.clear();
        SimTime = 0;
    }

    public void printLinkLoads(String loadFile, String packetFile, String nodeFile ) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(new File(loadFile));
        StringBuilder sb;  //= new StringBuilder();
        sb = new StringBuilder();
        sb.append("Links:");
        for(int i = 0; i<MAX_SIM_TIME/1000; i++) {
            sb.append(',');
            sb.append('T');
            sb.append(i);
        }
        sb.append('\n');
        pw.write(sb.toString());
        DecimalFormat df = new DecimalFormat("######.###");

        for ( Link l : networkLinks.values() ) {
            ArrayList<Double> test_link = l.getLinkLoadPerSecond();
            sb = new StringBuilder();
            sb.append(l.toString());
            for ( double loadVal : test_link ) {
                sb.append(',');
                sb.append(df.format(loadVal));
            }
            sb.append('\n');
            pw.write(sb.toString());
        }
        pw.close();


        PrintWriter pw2 = new PrintWriter(new File(packetFile));
        StringBuilder sb2;
        sb2 = new StringBuilder();
        long d;
        for (int i = 0; i<allPackets.size()-1; i++) {
            d = (allPackets.get(i).getTerminationTime()- allPackets.get(i).getCreationTime());
            sb2.append(d);
            sb2.append(',');

        }
        sb2.append(allPackets.get(allPackets.size()-1).getTerminationTime() - allPackets.get(allPackets.size()-1).getCreationTime());
        pw2.write(sb2.toString());
        pw2.close();



        PrintWriter pw3 = new PrintWriter(new File(nodeFile));
        StringBuilder sb3;
        sb3 = new StringBuilder();
        Object[] list = networkNodes.values().toArray();
        for (int i =0; i< list.length-1; i++) {
            sb3.append(((Node)list[i]).getNodeLoad());
            sb3.append(',');
        }
        sb3.append(((Node)list[list.length-1]).getNodeLoad());
        pw3.write(sb3.toString());
        pw3.close();

        System.out.println("done!");
    }
}
