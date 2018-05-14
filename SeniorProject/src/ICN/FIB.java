package ICN;

import Helper.SimPath;

import java.util.HashMap;
import java.util.Map;

public class FIB {
    public class FIBRow {
        private SimPath firstSimPath;
        private SimPath secondSimPath;
        private SimPath thirdSimPath;
	
        public FIBRow() {
            firstSimPath = new SimPath();
            secondSimPath = new SimPath();
            thirdSimPath = new SimPath();
        }

        public FIBRow(SimPath p1, SimPath p2, SimPath p3) {
            this.firstSimPath = p1;
            this.secondSimPath = p2;
            this.thirdSimPath = p3;
        }

        public SimPath getFirstSimPath() {
            return firstSimPath;
        }

        public void setFirstSimPath(SimPath firstSimPath) {
            this.firstSimPath = firstSimPath;
        }

        public SimPath getSecondSimPath() {
            return secondSimPath;
        }

        public void setSecondSimPath(SimPath secondSimPath) {
            this.secondSimPath = secondSimPath;
        }

        public SimPath getThirdSimPath() {
            return thirdSimPath;
        }

        public void setThirdSimPath(SimPath thirdSimPath) {
            this.thirdSimPath = thirdSimPath;
        }

        public void FIBRow_toString() {
            System.out.println("First " + firstSimPath.toString() + "\n"+
                                "Second " + secondSimPath.toString() + "\n" +
                                "Third " + thirdSimPath.toString());
        }
    }

    Map<Integer, FIBRow> forwardingTable = new HashMap<Integer, FIBRow>();

    public FIBRow row;

    public FIB() {

    }

    public FIBRow createFIBRow(SimPath p1, SimPath p2, SimPath p3){
        return new FIBRow(p1, p2,p3);
    }

    public void addFIBEntry(int nodeID, FIBRow fibRow) {
        forwardingTable.put(nodeID, fibRow);
    }

    public void alterFIBRowPath(int nodeID, int pathID, SimPath newSimPath) {
        if (!forwardingTable.containsKey(nodeID)) {
            forwardingTable.put(nodeID, new FIBRow());
        }
        FIBRow fr = forwardingTable.get(nodeID);
        switch (pathID) {
            case 1:
                fr.setFirstSimPath(newSimPath);
                break;
            case 2:
                fr.setSecondSimPath(newSimPath);
                break;
            case 3:
                fr.setThirdSimPath(newSimPath);
                break;
        }
        forwardingTable.put(nodeID, fr);
    }

    public FIBRow getFIBRow(int nodeID) {
        return forwardingTable.get(nodeID);
    }

    public String FIB_toString() {
        for (Integer nodeID : forwardingTable.keySet()) {
           forwardingTable.get(nodeID).FIBRow_toString();
        }
        return "";
    }

}
