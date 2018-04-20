package Simulator;
import Helper.*;
import kPath.Graph;
import kPath.VariableGraph;

public class Main {
    public static void main (String args[]) {
        Simulator sim = new Simulator();
        TopologyReader tr = new TopologyReader("input.txt",sim);
        Graph graph = new VariableGraph("ginput.txt");  //If we want to add a new node on the fly, we can write on this file then construct a new graph
        PrefixReader pr = new PrefixReader("prefix_input.txt",sim);
        ServeReader sr = new ServeReader("serve_input.txt",sim);
        DemandReader dr = new DemandReader("demand_input.txt",sim);


        //sim.buildPaths(graph,3);  //k: run simulator with single path or multiple path
        sim.runSimulation(graph, 3);

    }




}
