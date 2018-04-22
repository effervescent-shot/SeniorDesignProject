package Network;

import Enums.NodeType;
import Helper.SimPath;
import ICN.FIB;
import ICN.Prefix;
import ICN.RIB;
import Simulator.Event;


import java.lang.reflect.Array;
import java.util.*;

public class Node {
    private int ID;
    private String name;
    private NodeType type;
    private FIB fib;
    private RIB rib;
    ArrayList<String> servedPrefixes;
    ArrayList<String> demandedPrefixes;
    private Map<Link,PriorityQueue<Event>> sendBuffers;
    private Map<Link,PriorityQueue<Event>> receiveBuffers;

    private double NodeTime = 0;

    public Node (int ID) {
        this.ID = ID;
        this.fib = new FIB();
        this.rib = new RIB();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
        sendBuffers = new HashMap<Link,PriorityQueue<Event>>();
        receiveBuffers = new HashMap<Link,PriorityQueue<Event>>();
     }

    public Node(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.fib = new FIB();
        this.rib = new RIB();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
        sendBuffers = new HashMap<Link,PriorityQueue<Event>>();
        receiveBuffers = new HashMap<Link,PriorityQueue<Event>>();
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

    public void Initialize_Interest(double time, Prefix prefix) {
        System.out.println("Node: "+ this.ID + " at: " + time + " Prefix: " +  prefix.getPrefixName() + " size: "+prefix.getData().getSize());
        //System.out.println(Arrays.toString(servedPrefixes.toArray()));

        if(servedPrefixes.contains(prefix.getPrefixName())) {
            createSendEvents(prefix, this.ID, prefix.getData().getOwner()/1024 + 1);
            return;
        }

        ArrayList<Integer> sources = rib.getNodeListofPrefix(prefix);
        System.out.println(prefix.getPrefixName() + " " +Arrays.toString(sources.toArray()));

        PriorityQueue<SimPath> allPaths = new PriorityQueue<>();
        for (int i = 0; i< sources.size(); i++) {
            FIB.FIBRow fr = fib.getFIBRow(sources.get(i));
            allPaths.add(fr.getFirstSimPath());
            allPaths.add(fr.getSecondSimPath());
            allPaths.add(fr.getThirdSimPath());
        }

        SimPath kingPath = allPaths.poll();
        SimPath queenPath = allPaths.poll();
        SimPath heirPath = allPaths.poll();

        System.out.print("King: " +kingPath.toString() + "\n" +
                        "Queen: " +queenPath.toString() + "\n"+
                        "Heir: "  +heirPath.toString());

    }

    public void Initialize_Data(double time, Prefix prefix, int destinationID, int pathID) {

    }

    public void createLinkBuffers (Link link1, Link link2) {
        sendBuffers.put(link1, new PriorityQueue<>());
        receiveBuffers.put(link2, new PriorityQueue<>());
    }

    private void createSendEvents(Prefix p, int destNodeID, int numOfEvents){
        if(destNodeID == this.ID){

        } else {

        }
    }
}
