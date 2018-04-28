package Network;

import Enums.EventType;
import Enums.NodeType;
import Enums.PacketType;
import Helper.Pair;
import Helper.SimPath;
import ICN.FIB;
import ICN.Prefix;
import ICN.RIB;
import Simulator.Event;
import Simulator.Send_Receive_Event;
import Simulator.Simulator;

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

    public void Send(Link link, EventType eventType) {
        if(eventType == EventType.SEND_INTEREST) {
            Send_Receive_Event event = (Send_Receive_Event) sendBuffers.get(link).poll();

            double delay = 1000 * event.getPacket().getInterestPacketSize() / link.getCapacity(); //this yield second we want milisecond

            event.setEventType(EventType.RECEIVE_INTEREST); // turn the event into receive event
            event.getLink().augmentLoad(event.getPacket().getInterestPacketSize()); // update the link load
            event.setTime(event.getTime() + delay);  // set receive time



            addDelayToBufferQueue(link, delay);  //add delay to all events in the buffer
        }

        if(eventType == EventType.SEND_DATA) {

        }

    }

    public void Receive(Link link, EventType eventType) {  ///Eventi direk receiveBufferdan çek!!!!
        /////  if this is the destination   ////

                /////   if interest received

                /////    if data received

        ///   if this is not the destination //////
    }

    public void Initialize_Interest(double time, Prefix prefix) {
        //System.out.println("Node: "+ this.ID + " at: " + time + " Prefix: " +  prefix.getPrefixName() + " size: "+prefix.getData().getSize());
        //System.out.println(Arrays.toString(servedPrefixes.toArray()));
        if(servedPrefixes.contains(prefix.getPrefixName())) {
            createInterestSendEvents(time,prefix, this.ID, prefix.getData().getSize()/1024 + 1, null);
            return;
        }

        ArrayList<Integer> sources = rib.getNodeListofPrefix(prefix);
        //System.out.println(prefix.getPrefixName() + " " +Arrays.toString(sources.toArray()));

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
                                             /// #of interest events are inverse proportionally distributed
        double kcost = kingPath.getCost();   /// a*kcost = b*qcost = c*hcost = k
        double qcost = queenPath.getCost();  /// a + b + c = totalPack
        double hcost = heirPath.getCost();   /// k/kcost + k/qcost + k/hcost = totalPack
                                             /// k(qcost*hcost) + k(kcost*hcost) + k(kcost*qcost) = totalPack

        int totalPackets = prefix.getData().getSize()/1024 + 1;
        double k = totalPackets*kcost*qcost*hcost / (qcost*hcost + kcost*hcost + kcost*qcost);

        int kpackets = (int) Math.round(k/kcost);
        int qpackets = (int) Math.round(k/qcost);
        int hpackets = totalPackets - kpackets - qpackets;

        if(hpackets < 0) {
            hpackets = 0;
        }

        if(hpackets > qpackets){
            qpackets = hpackets;
            hpackets = 0;
        }
        //System.out.println("Total packet: " + totalPackets + " kpac " + kpackets + " qpack " + qpackets + " hpack " + hpackets);

        createInterestSendEvents(time,prefix, kingPath.getLast(),kpackets, kingPath);
        createInterestSendEvents(time,prefix, queenPath.getLast(),qpackets, queenPath);
        createInterestSendEvents(time,prefix, heirPath.getLast(),hpackets, heirPath);

    }

    private void addDelayToBufferQueue(Link link, double delay) {
        //if buffer is not empyty then add delay to each element
        //then add the event into to Simulator queue
    }

    public void addInitEvents() {
        for (PriorityQueue queue : sendBuffers.values()) {
            if(!queue.isEmpty()) {
                Event e = (Event) queue.peek();
                Simulator.eventQueue.add(e);
            }
        }
    }

    public void Initialize_Data(double time, Prefix prefix, int destNodeID, SimPath path) {
        SimPath reversePath = new SimPath(path); ///pathi kopyala koy her zaman
        reversePath.setReverse();

        Packet packet = new Packet(time, PacketType.DATA_PACKET, reversePath);  /// random service time eklenebilir
        packet.setSourceNodeID(this.ID);   ///   TODO:: put all those variables in an initialization method or make a new constructor for Packet
        packet.setDestinationNodeID(destNodeID);
        packet.setPrefix(prefix);

        int nextHopID = reversePath.nextNodeID(this.ID);
        Event sendEvent = new Send_Receive_Event(time, EventType.SEND_DATA, this.ID, nextHopID,packet);
        sendBuffers.get(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID))).add(sendEvent);

    }

    public void createLinkBuffers (Link link1, Link link2) {
        sendBuffers.put(link1, new PriorityQueue<>());
        //receiveBuffers.put(link2, new PriorityQueue<>());  // ATTENTION!!! :: receiveBuffers have reverse links
    }

    public void addToSendBuffer(Link link, Event event){
        sendBuffers.get(link).add(event);
    }

    public void addToReceiveBuffer(Link link, Event event) {
        receiveBuffers.get(link).add(event);
    }

    private void createInterestSendEvents(double time, Prefix prefix, int destNodeID, int numOfEvents, SimPath path){
        if(destNodeID == this.ID){
            return;
        } else {
            //System.out.println("At time: " + time + " " +prefix.getPrefixName() +  " from " + destNodeID + " # " + numOfEvents +" " + path.toString());

            for(int i = 0 ; i< numOfEvents ; i++) {
                int nextHopID = path.nextNodeID(this.ID);

                Packet packet = new Packet(time, PacketType.INTEREST_PACKET, new SimPath(path)); ///pathi kopyala koy
                packet.setSourceNodeID(this.ID);   ///   TODO:: put all those variables in an initialization method or make a new constructor for Packet
                packet.setDestinationNodeID(destNodeID);
                packet.setPrefix(prefix);

                Event sendEvent = new Send_Receive_Event(time, EventType.SEND_INTEREST, this.ID, nextHopID,packet);
                addToSendBuffer(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)), sendEvent);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) return false;
        Node newnode = (Node) o;
        return this.ID == newnode.getID();
    }



}
