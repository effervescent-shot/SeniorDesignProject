package Simulator;
import Helper.*;
import Input.RandomTopology;
import kPath.Graph;
import kPath.VariableGraph;

import java.io.FileNotFoundException;

public class Main {
    public static String fileSuffix = "";
    public static void main (String args[]) throws FileNotFoundException {

        //RandomTopology.randomGenerator(10,30,15);

        Simulator sim = new Simulator(1000000); //milisecond
        TopologyReader tr = new TopologyReader("data/input"+fileSuffix+".txt",sim);
        Graph graph3 = new VariableGraph("data/ginput"+fileSuffix+".txt");
        Graph graph1 = new VariableGraph("data/ginput"+fileSuffix+".txt");

        PrefixReader pr = new PrefixReader("data/prefix_input"+fileSuffix+".txt",sim);
        ServeReader sr = new ServeReader("data/serve_input"+fileSuffix+".txt",sim);
        DemandReader dr = new DemandReader("data/demand_input"+fileSuffix+".txt",sim);

        path1Sim(sim, graph1);
        //path3Sim(sim, graph3);
    }

    public static void path3Sim(Simulator sim,Graph g3) throws FileNotFoundException {
        sim.buildPaths(g3,3);
        sim.initialization(5000,116185, 17,19);
        sim.runSimulation(g3, 3);
        sim.printLinkLoads("vis-data/load3"+fileSuffix+".csv", "vis-data/packet3"+fileSuffix+".csv");
    }

    public static void path1Sim(Simulator sim,Graph g1) throws FileNotFoundException {
        sim.buildPaths(g1,1);
        sim.initialization(5000,116185, 17,19);
        sim.runSimulation(g1, 1);
        sim.printLinkLoads("vis-data/load1"+fileSuffix+".csv", "vis-data/packet1"+fileSuffix+".csv");
    }
}
