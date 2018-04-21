package Network;

import Enums.NodeType;
import Helper.SimPath;
import ICN.FIB;
import ICN.RIB;
import Simulator.Event;


import java.util.ArrayList;
import java.util.PriorityQueue;

public class Node {
    private int ID;
    private String name;
    private NodeType type;
    private FIB fib;
    private RIB rib;
    ArrayList<String> servedPrefixes;
    ArrayList<String> demandedPrefixes;
    private PriorityQueue<Event> sendBuffer;
    private PriorityQueue<Event> receiveBuffer;

    public Node (int ID) {
        this.ID = ID;
        this.fib = new FIB();
        this.rib = new RIB();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
        sendBuffer = new PriorityQueue<>();
        receiveBuffer = new PriorityQueue<>();
     }

    public Node(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.fib = new FIB();
        this.rib = new RIB();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
        sendBuffer = new PriorityQueue<>();
        receiveBuffer = new PriorityQueue<>();
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


    ///////////////////     Fill later      ////////////

    public void Send() {

    }

    public void Receive() {
        /////  if this is the destination   ////
                /////   if interest received

                /////    if data received

        ///   if this is not the destination //////
    }

}
