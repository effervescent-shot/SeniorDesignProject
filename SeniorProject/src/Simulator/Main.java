package Simulator;
import Helper.*;

public class Main {
    public static void main (String args[]) {
        Simulator sim = new Simulator();
        TopologyReader tr = new TopologyReader("input.txt",sim);
        sim.InitializeRoutingAlgorithm();
        sim.RunDijkstra();


        Pair<Integer,Integer> p1 = new Pair<>(0,1);
        Pair<Integer,Integer> p2 = new Pair<>(1,2);
        Pair<Integer,Integer> p3 = new Pair<>(2,3);

        System.out.println(sim.newtworkLinks.get(p1).getCapacity());
        System.out.println(sim.newtworkLinks.get(p2).getCapacity());
        System.out.println(sim.newtworkLinks.get(p3).getCapacity());


    }
}
