package Simulator;
import Enums.RoutingType;
import Helper.*;
import Input.RandomTopology;
import Network.Packet;
import kPath.Graph;
import kPath.VariableGraph;
import java.io.FileNotFoundException;


public class RunSimulator {

    public static String fileSuffix = "";
    public static final long timeSeed = 100000001;
    public static final long nodeSeed = 200000041;
    public static final long prefixSeed = 300000071;

    public static final int numEvents = 100;



    public static void main (String args[]) throws Exception {

        int NodeCount = 10;   //can be taken as argument
        int EdgeCount = 30;
        int PrefixCount = 15;
        long MaxSimTime = 10000;  //milisecond

        //Boolean boo = Boolean.valueOf(args[1]);
        System.out.println("Houston");

        Simulator sim = new Simulator(MaxSimTime);


        boolean check = true;

        //LogNormalDistribution logNormalDistribution = new LogNormalDistribution(3.5, 5.0);

        while (check) {
                try {
                    fileSuffix ="_"+1;
                    RandomTopology.randomGenerator(NodeCount,EdgeCount,PrefixCount);
                    init(sim);
                    Graph graph1 = new VariableGraph("data/ginput"+fileSuffix+".txt");
                    path1Sim(sim,graph1);
                    check = false;
                } catch (Exception e) {
                    check = true;
                    e.printStackTrace();
                }
            }


        Graph graph0 = new VariableGraph("data/ginput"+fileSuffix+".txt");


    }

    public static void init(Simulator sim) throws Exception{
        /*Simulator.networkNodes = new HashMap<>();
        Simulator.networkLinks = new HashMap<>();
        Simulator.networkPrefixes = new HashMap<>();
        Simulator.allPackets = new ArrayList<>();
        Simulator. eventQueue = new PriorityQueue<>();*/

        TopologyReader tr = new TopologyReader("data/input"+fileSuffix+".txt",sim);
        PrefixReader pr = new PrefixReader("data/prefix_input"+fileSuffix+".txt",sim);
        ServeReader sr = new ServeReader("data/serve_input"+fileSuffix+".txt",sim);
        DemandReader dr = new DemandReader("data/demand_input"+fileSuffix+".txt",sim);
    }


    public static void path3Sim(Simulator sim,Graph g3) throws FileNotFoundException {
        Packet.setRoutingType(RoutingType.SOURCE_ROUTING_3);
        sim.buildPaths(g3,3);
        sim.initialization(numEvents,timeSeed,nodeSeed,prefixSeed);
        sim.runSimulation(g3, 3);
        sim.printLinkLoads("vis-data/load3"+fileSuffix+".csv", "vis-data/packet3"+fileSuffix+".csv",
                            "vis-data/node3"+fileSuffix+".csv");
    }

    public static void path1Sim(Simulator sim,Graph g1) throws FileNotFoundException {
        Packet.setRoutingType(RoutingType.SOURCE_ROUTING_1);
        sim.buildPaths(g1,1);
        sim.initialization(numEvents,timeSeed,nodeSeed,prefixSeed);
        sim.runSimulation(g1, 1);
        sim.printLinkLoads("vis-data/load1"+fileSuffix+".csv", "vis-data/packet1"+fileSuffix+".csv",
                "vis-data/node1"+fileSuffix+".csv");
    }

    public static void LSCRSim(Simulator sim, Graph g0) throws  FileNotFoundException {
        Packet.setRoutingType(RoutingType.LSCR);
    }

}
