package Network;

import Enums.NodeType;
import Helper.SimPath;
import ICN.FIB;
import ICN.RIB;

public class Node {
    private int ID;
    private String name;
    private NodeType type;
    private FIB fib;
    private RIB rib;

    public Node (int ID) {
        this.ID = ID;
        this.fib = new FIB(ID);
        this.rib = new RIB();
     }

    public Node(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.fib = new FIB(ID);
        this.rib = new RIB();
    }

    public Node (int ID, NodeType type) {
        this.ID = ID;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public FIB getFib() {
        return fib;
    }

    public void setFib(FIB fib) {
        this.fib = fib;
    }

    public RIB getRib() {
        return rib;
    }

    public void setRib(RIB rib) {
        this.rib = rib;
    }

    public void setFibRow(int nodeID, int pathID, SimPath path) {
        this.fib.alterFIBRowPath(nodeID, pathID, path);
    }
}
