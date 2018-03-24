package ICN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RIB {
    private Map<Prefix, ArrayList<Integer>> ribTable;

    public RIB() {
        ribTable = new HashMap<>();
    }

    public ArrayList<Integer> getNodeListofPrefix(Prefix prefix) {
        if (ribTable.containsKey(prefix)) {
            return ribTable.get(prefix);
        } else {
            return new ArrayList<Integer>();
        }

    }

    public void addRIBRow(Prefix prefix, ArrayList<Integer> nodeList) {
        ribTable.put(prefix, nodeList);
    }

    public void addNodeToRIBRowNodeList(Prefix prefix, int nodeID) {
        if (ribTable.containsKey(prefix)) {
            if (!ribTable.get(prefix).contains(nodeID)) {
                ribTable.get(prefix).add(nodeID);
            }
        }
    }


}
