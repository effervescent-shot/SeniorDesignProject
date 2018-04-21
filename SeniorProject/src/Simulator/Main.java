package Simulator;
import Helper.*;
import kPath.Graph;
import kPath.VariableGraph;

public class Main {
    public static void main (String args[]) {
        Simulator sim = new Simulator();
        TopologyReader tr = new TopologyReader("data/input.txt",sim);
        Graph graph = new VariableGraph("data/ginput.txt");
        PrefixReader pr = new PrefixReader("data/prefix_input.txt",sim);
        ServeReader sr = new ServeReader("data/serve_input.txt",sim);
        DemandReader dr = new DemandReader("data/demand_input.txt",sim);


        //sim.buildPaths(graph,3);  //k: run simulator with single path or multiple path
        sim.runSimulation(graph, 3);

    }




}
