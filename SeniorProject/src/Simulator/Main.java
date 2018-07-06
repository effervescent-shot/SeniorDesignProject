package Simulator;
import Helper.*;
import Input.RandomTopology;
import kPath.Graph;
import kPath.VariableGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static String fileSuffix = "";
    public static void main (String args[]) throws Exception {

        /*int NodeCount = Integer.valueOf(args[1]);
        int EdgeCount = Integer.valueOf(args[2]);
        int PrefixCount = Integer.valueOf(args[3]);
        long SimTime = Long.valueOf(args[4]);
        String fileSuffix = args[5];*/

//        Boolean boo = Boolean.valueOf(args[1]);
//        System.out.println(boo);

        Simulator sim = new Simulator(1000); //milisecond
        boolean check = true;
        while (check) {
            try {
                //RandomTopology.randomGenerator(10, 30, 150);
                init(sim);
                check = false;
            } catch (Exception e) {
                RandomTopology.randomGenerator(10, 30, 150);
                check = true;
            }
        }



        Graph graph3 = new VariableGraph("data/ginput"+fileSuffix+".txt");
        Graph graph1 = new VariableGraph("data/ginput"+fileSuffix+".txt");

        path1Sim(sim, graph1);



    }

    public static void init(Simulator sim) throws Exception{
        TopologyReader tr = new TopologyReader("data/input"+fileSuffix+".txt",sim);
        PrefixReader pr = new PrefixReader("data/prefix_input"+fileSuffix+".txt",sim);
        ServeReader sr = new ServeReader("data/serve_input"+fileSuffix+".txt",sim);
        DemandReader dr = new DemandReader("data/demand_input"+fileSuffix+".txt",sim);
    }


    public static void path3Sim(Simulator sim,Graph g3) throws FileNotFoundException {
        sim.buildPaths(g3,3);
        sim.initialization(10000,13454, 14517,191581);
        sim.runSimulation(g3, 3);
        sim.printLinkLoads("vis-data/load3"+fileSuffix+".csv", "vis-data/packet3"+fileSuffix+".csv");
    }

    public static void path1Sim(Simulator sim,Graph g1) throws FileNotFoundException {
        sim.buildPaths(g1,1);
        sim.initialization(10000,13454, 14517,191581);
        sim.runSimulation(g1, 1);
        sim.printLinkLoads("vis-data/load1"+fileSuffix+".csv", "vis-data/packet1"+fileSuffix+".csv");
    }

}
