package Network;

import Enums.NodeType;
import Helper.SimPath;
import ICN.FIB;
import ICN.Prefix;
import ICN.RIB;
import Simulator.Event;

import java.util.ArrayList;

public class Node {
    private int ID;
    private String name;
    private NodeType type;
    private FIB fib;
    private RIB rib;
    ArrayList<String> servedPrefixes;
    ArrayList<String> demandedPrefixes;

    public Node (int ID) {
        this.ID = ID;
        this.fib = new FIB();
        this.rib = new RIB();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
     }

    public Node(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.fib = new FIB();
        this.rib = new RIB();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
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

    public void setFibRow(int nodeID, SimPath path1, SimPath path2, SimPath path3) {
        fib.addFIBEntry(nodeID, this.fib.createFIBRow(path1,path2,path3));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getServedPrefixes() {
        return servedPrefixes;
    }

    public void setServedPrefixes(ArrayList<String> servedPrefixes) {
        this.servedPrefixes = servedPrefixes;
    }

    public ArrayList<String> getDemandedPrefixes() {
        return demandedPrefixes;
    }

    public void setDemandedPrefixes(ArrayList<String> demandedPrefixes) {
        this.demandedPrefixes = demandedPrefixes;
    }

    public void sendData(Event event){

    }
    public void receiveData(Event event){

    }
}
