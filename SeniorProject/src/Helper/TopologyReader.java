package Helper;

import Network.Link;
import Network.Node;
import Simulator.Simulator;
import Helper.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TopologyReader {
    String fileName;
    Simulator simulator;

    public TopologyReader(String fileName, Simulator sim) {
        this.fileName = fileName;
        this.simulator = sim;
        buildTopology();
    }

    private void buildTopology () {
        File f = new File(fileName);
        Scanner reader;
        try {
            reader = new Scanner(f);
            int n = reader.nextInt();

            String fnode;
            String snode;
            int fn = 0;
            int sn = 0;
            int lenght = 0;
            int nid = 10000;
            int eid = 1000;
            Node first;
            Node second;

            ArrayList<String> nodenames = new ArrayList();
            ArrayList<Node> nodes = new ArrayList();

            for (int i = 0; i<n; i++) {
                fnode = reader.next();
                snode = reader.next();
                lenght = reader.nextInt();

                if(!nodenames.contains(fnode)) {
                    nodenames.add(fnode);
                    first = new Node(nid++, fnode);
                    nodes.add(first);
                    simulator.networkNodes.put(first.getID(), first);
                } else {
                    first = nodes.get(nodenames.indexOf(fnode));
                }

                if(!nodenames.contains(snode)) {
                    nodenames.add(snode);
                    second = new Node(nid++, snode);
                    nodes.add(second);
                    simulator.networkNodes.put(second.getID(), second);
                } else {
                    second = nodes.get(nodenames.indexOf(snode));
                }
                Pair<Integer,Integer> edgePair = new Pair<>(first.getID(), second.getID());
                //System.out.println(edgePair.getLeft() + " " + edgePair.getRight());
                simulator.newtworkLinks.put(edgePair, new Link(eid++, first, second,lenght));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Topology file couldn't be found");
        }

    }
}
