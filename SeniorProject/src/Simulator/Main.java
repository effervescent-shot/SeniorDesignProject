package Simulator;
import Helper.*;
import kPath.Graph;
import kPath.Path;
import kPath.VariableGraph;
import kPath.shortestpaths.YenTopKShortestPathsAlg;

public class Main {
    public static void main (String args[]) {
        Simulator sim = new Simulator();
        TopologyReader tr = new TopologyReader("input.txt",sim);
        Graph graph = new VariableGraph("ginput.txt");
        sim.buildPaths(graph);

    }




}
