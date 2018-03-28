package Simulator;
import Helper.*;

public class Main {
    public static void main (String args[]) {
        Simulator sim = new Simulator();
        TopologyReader tr = new TopologyReader("input.txt",sim);

        Pair<Integer,Integer> p1 = new Pair<>(0,1);
        Pair<Integer,Integer> p2 = new Pair<>(1,2);
        Pair<Integer,Integer> p3 = new Pair<>(2,0);

        System.out.println(sim.newtworkLinks.get(p1).getLinkLength());
        System.out.println(sim.newtworkLinks.get(p2).getLinkLength());
        System.out.println(sim.newtworkLinks.get(p3).getLinkLength());


    }
}
