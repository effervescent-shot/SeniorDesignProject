package Helper;

import ICN.Prefix;
import ICN.RIB;
import Network.Node;
import Simulator.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServeReader {
    String fileName;
    Simulator simulator;

    public ServeReader(String fileName, Simulator sim) {
        this.fileName = fileName;
        this.simulator = sim;
        setPrefixServed();
    }

    private void setPrefixServed() {
        File f = new File(fileName);
        Scanner reader;
        try {
            reader = new Scanner(f);
            int n = reader.nextInt();

            int nodeID;
            String prefix;
            String[] prefixes;
            RIB rib = new RIB();
            //Prefix prefix;

            for (int i = 0; i<n; i++) {
                nodeID = reader.nextInt();
                //System.out.println("SNode:"+nodeID);
                prefix = reader.next();
                //System.out.println("Sprefix:"+prefix);

                prefixes = prefix.split(";");
                ArrayList<String> tmp = new ArrayList<String>();
                for (int j = 0; j < prefixes.length; j++) {
                    tmp.add(prefixes[j]);
                    rib.addNodeToRIBRowNodeList(simulator.networkPrefixes.get(prefixes[j]),nodeID);
                }
                simulator.networkNodes.get(nodeID).setServedPrefixes(tmp);

            }

            for (Node node: simulator.networkNodes.values()){
                    node.setRib(rib);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Served Prefix file couldn't be found");
        }

    }
}
