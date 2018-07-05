package Helper;

import ICN.Prefix;
import Simulator.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DemandReader {
    String fileName;
    Simulator simulator;

    public DemandReader(String fileName, Simulator sim) {
        this.fileName = fileName;
        this.simulator = sim;
        setPrefixDemanded();
    }

    private void setPrefixDemanded() {
        File f = new File(fileName);
        Scanner reader;
        try {
            reader = new Scanner(f);
            int n = reader.nextInt();

            int nodeID;
            String prefix;
            String[] prefixes;

            //Prefix prefix;

            for (int i = 0; i<n; i++) {
                nodeID = reader.nextInt();
                //System.out.println("DNode:"+nodeID);
                prefix = reader.next();
                //System.out.println("Dprefix:"+prefix);
                prefixes = prefix.split(";");
                ArrayList<String> tmp = new ArrayList<String>();
                for (int j = 0; j < prefixes.length; j++) {
                    tmp.add(prefixes[j]);
                }
                simulator.networkNodes.get(nodeID).setDemandedPrefixes(tmp);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Served Prefix file couldn't be found");
        }

    }
}
