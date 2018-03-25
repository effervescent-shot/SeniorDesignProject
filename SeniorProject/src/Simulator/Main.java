package Simulator;
import Helper.*;

public class Main {
    public static void main (String args[]) {
        Simulator sim = new Simulator();
        TopologyReader tr = new TopologyReader("input.txt",sim);

        Pair<Integer,Integer> p = new Pair<>(10001,10002);
        System.out.println(sim.newtworkLinks.get(p).getLinkLength());
    }
}
