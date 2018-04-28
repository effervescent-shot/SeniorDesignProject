package Helper;

import Network.Link;
import Network.Node;
import Simulator.Simulator;

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
            int capacity = 0;
            int nid = 0;  //nodeID
            int eid = 1000;   //edgeID
            Node first;
            Node second;

            ArrayList<String> nodenames = new ArrayList();
            ArrayList<Node> nodes = new ArrayList();

            for (int i = 0; i<n; i++) {
                fnode = reader.next();
                snode = reader.next();
                capacity = reader.nextInt();

                if(!nodenames.contains(fnode)) {
                    nodenames.add(fnode);
                    first = new Node(Integer.parseInt(fnode.substring(4,fnode.length())), fnode);
                    nodes.add(first);
                    simulator.networkNodes.put(first.getID(), first);
                } else {
                    first = nodes.get(nodenames.indexOf(fnode));
                }

                if(!nodenames.contains(snode)) {
                    nodenames.add(snode);
                    second = new Node(Integer.parseInt(snode.substring(4,snode.length())), snode);
                    nodes.add(second);
                    simulator.networkNodes.put(second.getID(), second);
                } else {
                    second = nodes.get(nodenames.indexOf(snode));
                }
                Pair<Integer,Integer> edgePair1 = new Pair<>(first.getID(), second.getID());
                Pair<Integer,Integer> edgePair2 = new Pair<>(second.getID(), first.getID());
                //System.out.println(edgePair.getLeft() + " " + edgePair.getRight());
                Link direc1 = new Link(eid++, first, second,capacity);
                Link direc2 = new Link(eid++, second, first,capacity);
                simulator.networkLinks.put(edgePair1, direc1);
                simulator.networkLinks.put(edgePair2, direc2);

                //simulator.networkNodes.get(first.getID()).createLinkBuffers(direc1,direc2);
                //simulator.networkNodes.get(second.getID()).createLinkBuffers(direc2, direc1);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Topology file couldn't be found");
        }

    }
}
