package Simulator;
import Helper.*;
import ICN.Prefix;
import kPath.Graph;
import kPath.Path;
import kPath.VariableGraph;
import kPath.shortestpaths.YenTopKShortestPathsAlg;

public class Main {
    public static void main (String args[]) {
        Simulator sim = new Simulator();
        TopologyReader tr = new TopologyReader("input.txt",sim);
        Graph graph = new VariableGraph("ginput.txt");
        PrefixReader pr = new PrefixReader("prefix_input.txt",sim);
        ServeReader sr = new ServeReader("serve_input.txt",sim);
        DemandReader dr = new DemandReader("demand_input.txt",sim);

        sim.buildPaths(graph);

    }




}
