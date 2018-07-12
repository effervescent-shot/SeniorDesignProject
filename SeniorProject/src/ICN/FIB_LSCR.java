package ICN;

import java.util.HashMap;
import java.util.Map;

public class FIB_LSCR {
    private Map<Integer,Integer> forwardingTable_LSCR = new HashMap<>();

    public FIB_LSCR() {

    }

    public int getNextHop(int destID) {
        if(forwardingTable_LSCR.containsKey(destID)) {
            return forwardingTable_LSCR.get(destID);
        }
        return  -1;
    }

    public void setNextHop(int destID, int nextHop ) {
        forwardingTable_LSCR.put(destID,nextHop);
    }


}
