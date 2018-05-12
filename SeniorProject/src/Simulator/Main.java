package Simulator;
import Helper.*;
import kPath.Graph;
import kPath.VariableGraph;

public class Main {
    public static void main (String args[]) {

        Simulator sim = new Simulator(1000); //milisecond
        TopologyReader tr = new TopologyReader("data/input.txt",sim);
        Graph graph = new VariableGraph("data/ginput.txt");
        PrefixReader pr = new PrefixReader("data/prefix_input.txt",sim);
        ServeReader sr = new ServeReader("data/serve_input.txt",sim);
        DemandReader dr = new DemandReader("data/demand_input.txt",sim);

        sim.buildPaths(graph,3);
        sim.initialization(10,116185, 17,19);

        sim.runSimulation(graph, 3);

    }
}
