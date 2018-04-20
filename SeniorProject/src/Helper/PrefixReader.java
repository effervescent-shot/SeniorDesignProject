package Helper;

import ICN.Prefix;
import Simulator.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PrefixReader {
    String fileName;
    Simulator simulator;

    public PrefixReader(String fileName, Simulator sim) {
        this.fileName = fileName;
        this.simulator = sim;
        setPrefix();
    }

    private void setPrefix () {
        File f = new File(fileName);
        Scanner reader;
        try {
            reader = new Scanner(f);
            int n = reader.nextInt();

            String prefixName;

            Prefix prefix;

            for (int i = 0; i<n; i++) {
                prefixName = reader.next();
                prefix = new Prefix(prefixName);
                simulator.networkPrefixes.put(prefixName,prefix);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Prefix file couldn't be found");
        }

    }
}
