package Helper;

import ICN.Prefix;
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

            //Prefix prefix;

            for (int i = 0; i<n; i++) {
                nodeID = reader.nextInt();
                prefix = reader.next();
                prefixes = prefix.split(";");
                ArrayList<String> tmp = new ArrayList<String>();
                for (int j = 0; j < prefixes.length; j++) {
                    tmp.add(prefixes[j]);
                }
                simulator.networkNodes.get(nodeID).setServedPrefixes(tmp);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Served Prefix file couldn't be found");
        }

    }
}
