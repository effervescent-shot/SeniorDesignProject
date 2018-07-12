package Network;

import Enums.EventType;
import Enums.NodeType;
import Enums.PacketType;
import Enums.RoutingType;
import Helper.Pair;
import Helper.SimPath;
import ICN.FIB;
import ICN.FIB_LSCR;
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
    private FIB_LSCR fib_lscr;
    ArrayList<String> servedPrefixes;
    ArrayList<String> demandedPrefixes;
    private Map<Link,Queue<Event>> sendBuffers;
    private Map<Link,Long> sendBufferTime;
    private Map<Link,Queue<Event>> receiveBuffers;

    private long nodeLoad = 0;
    private long NodeTime = 0;

    public Node (int ID) {
        this.ID = ID;
        this.fib = new FIB();
        this.rib = new RIB();
        this.fib_lscr = new FIB_LSCR();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
        sendBuffers = new HashMap<Link,Queue<Event>>();
        receiveBuffers = new HashMap<Link,Queue<Event>>();
        sendBufferTime = new HashMap<>();
     }

    public Node(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.fib = new FIB();
        this.rib = new RIB();
        this.fib_lscr = new FIB_LSCR();
        servedPrefixes=new ArrayList<String>();
        demandedPrefixes = new ArrayList<String>();
        sendBuffers = new HashMap<Link,Queue<Event>>();
        receiveBuffers = new HashMap<Link,Queue<Event>>();
        sendBufferTime = new HashMap<>();
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

    public long getNodeLoad() { return nodeLoad; }

    public void setNodeLoad(long nodeLoad) { this.nodeLoad = nodeLoad; }

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

    public FIB_LSCR getFib_lscr() {
        return fib_lscr;
    }

    public void setFib_lscr(FIB_LSCR fib_lscr) {
        this.fib_lscr = fib_lscr;
    }

    public void setFibRow(int targetNodeID, SimPath path1, SimPath path2, SimPath path3) {
        fib.addFIBEntry(targetNodeID, this.fib.createFIBRow(path1,path2,path3));
        fib_lscr.setNextHop(targetNodeID,path1.nextNodeID(this.ID));
        /*System.out.println("this is node:" + this.ID + " and target node is:" + targetNodeID +
                            " simPath1 = " + path1.toString() + " and the next hop " +  path1.nextNodeID(this.ID));*/
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



    public void Send(Link link, EventType eventType) {

        if(eventType == EventType.SEND_INTEREST) {
            Send_Receive_Event event = (Send_Receive_Event) sendBuffers.get(link).poll();
            if(event!=null) {
                nodeLoad += event.getPacket().getPacketSize();
                long delay = calculateDelay(link, event); //this yield second we want milisecond
                event.setEventType(EventType.RECEIVE_INTEREST); // turn the event into receive event
                event.getLink().augmentLoad(event.getPacket().getPacketSize()); // update the link load
                event.setTime(event.getTime() + delay);  // set receive time
                sendBufferTime.put(link,event.getTime());  //Earliest time that next packet can be send through this link
                Simulator.eventQueue.add(event); // receive event is put in the global queue
                getNextFromBufferQueue(link, event.getTime()); //get next element of the queue
            }
        }

        if(eventType == EventType.SEND_DATA) {
            Send_Receive_Event event = (Send_Receive_Event) sendBuffers.get(link).poll();
            if(event!=null){
               nodeLoad += event.getPacket().getPacketSize();
               long delay = calculateDelay(link, event);
               event.setEventType(EventType.RECEIVE_DATA);
               event.getLink().augmentLoad(event.getPacket().getPacketSize());
               event.setTime(event.getTime()+delay);
               sendBufferTime.put(link,event.getTime()); //Earliest time that next packet can be send trough this link
               Simulator.eventQueue.add(event);
               getNextFromBufferQueue(link, event.getTime());
            }
        }

    }

    public void Receive(Link link, Event event) {  ///Eventi direk receiveBufferdan çek!!!!

        Send_Receive_Event e = (Send_Receive_Event) event;
        /////  if this is the destination   ////
        if(e.getPacket().getDestinationNodeID() == this.ID) {
            /////   if interest received
                if(e.getEventType() == EventType.RECEIVE_INTEREST) {
                    if(servedPrefixes.contains(e.getPacket().getPrefix().getPrefixName())) {
                        Initialize_Data(e.getTime(),e.getPacket().getPrefix(),e.getPacket().getSourceNodeID(),e.getPacket().getSimPath());  //service time may be added as random time
                        e.getPacket().terminatePacket(e.getTime());
                    }
                }
            /////    if data received
                else if(e.getEventType() == EventType.RECEIVE_DATA) {
                        ///DO NOTHING -- Log all
                    e.getPacket().terminatePacket(e.getTime());
                }
        } else {
            ///   if this is not the destination //////
            int nextHopID = -1;  //TODO::merge those somebitch assignments

            if(Packet.getRoutingType() != RoutingType.LSCR) {
                nextHopID = e.getPacket().getSimPath().nextNodeID(this.ID);
            } else {
                nextHopID = fib_lscr.getNextHop(e.getPacket().getDestinationNodeID());
            }

            if(e.getEventType() == EventType.RECEIVE_INTEREST) {

                e.setEventType(EventType.SEND_INTEREST);
                e.setLink(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)));
                e.setFrom(this.ID);
                e.setTo(nextHopID);
                addToSendBuffer(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)), e);
            }
            /////    if data received
            else if(e.getEventType() == EventType.RECEIVE_DATA) {
                e.setEventType(EventType.SEND_DATA);
                e.setLink(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)));
                e.setFrom(this.ID);
                e.setTo(nextHopID);
                addToSendBuffer(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)), e);
            }

        }

    }



    public void Initialize_Interest(long time, Prefix prefix) {
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

    public void addToSendBuffer(Link link, Event event){

        if(sendBuffers.get(link).isEmpty()) {
            if(sendBufferTime.get(link) != null) {
                if (event.getTime() < sendBufferTime.get(link)) {
                    event.setTime(sendBufferTime.get(link));
                }
            }
                sendBuffers.get(link).add(event);
                Send(link,event.getEventType());

        } else {
            sendBuffers.get(link).add(event);
        }
    }

    private void getNextFromBufferQueue(Link link, long time) {
        //if buffer is not empyty then add delay to each element
        if(!sendBuffers.get(link).isEmpty()){
            Event e = sendBuffers.get(link).peek();
            e.setTime(time);
            Simulator.eventQueue.add(e);
        }
    }

    private long calculateDelay (Link link, Event e) {
        return (long)1000 * ((Send_Receive_Event)e).getPacket().getPacketSize()*8 / link.getCapacity(); //*1000 to turn it milisecond
    }

    public void Initialize_Data(long time, Prefix prefix, int destNodeID, SimPath path) {

        if(Packet.getRoutingType() == RoutingType.LSCR) {
            Packet packet = new Packet(time, PacketType.DATA_PACKET);
            packet.setSourceNodeID(this.ID);
            packet.setDestinationNodeID(destNodeID);
            packet.setPrefix(prefix);

            int nextHopID = fib_lscr.getNextHop(destNodeID);
            Event sendEvent = new Send_Receive_Event(time, EventType.SEND_DATA, this.ID, nextHopID,packet);
            addToSendBuffer(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)),sendEvent);

        } else {
            SimPath reversePath = new SimPath(path); ///pathi kopyala koy her zaman
            reversePath.setReverse();

            Packet packet = new Packet(time, PacketType.DATA_PACKET, reversePath);  /// random service time eklenebilir
            packet.setSourceNodeID(this.ID);   ///   TODO:: put all those variables in an initialization method or make a new constructor for Packet
            packet.setDestinationNodeID(destNodeID);
            packet.setPrefix(prefix);

            int nextHopID = reversePath.nextNodeID(this.ID);
            Event sendEvent = new Send_Receive_Event(time, EventType.SEND_DATA, this.ID, nextHopID,packet);

            addToSendBuffer(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)),sendEvent);
        }


    }

    public void createLinkBuffers (Link link1, Link link2) {
        sendBuffers.put(link1, new PriorityQueue<>());
        receiveBuffers.put(link2, new PriorityQueue<>());  // ATTENTION!!! :: receiveBuffers have reverse links
    }

    public void addToReceiveBuffer(Link link, Event event) {
        receiveBuffers.get(link).add(event);
    }

    private void createInterestSendEvents(long time, Prefix prefix, int destNodeID, int numOfEvents, SimPath path){
        if(destNodeID == this.ID){
            return;
        } else if (Packet.getRoutingType() == RoutingType.LSCR) {

            for(int i = 0 ; i< numOfEvents ; i++) {
                int nextHopID = fib_lscr.getNextHop(destNodeID);

                Packet packet = new Packet(time, PacketType.INTEREST_PACKET);
                packet.setSourceNodeID(this.ID);
                packet.setDestinationNodeID(destNodeID);
                packet.setPrefix(prefix);

                Event sendEvent = new Send_Receive_Event(time, EventType.SEND_INTEREST, this.ID, nextHopID,packet);
                addToSendBuffer(Simulator.networkLinks.get(new Pair<>(this.ID, nextHopID)), sendEvent);
            }
        } else {
            //System.out.println("At time: " + time + " " +prefix.getPrefixName() +  " from " + destNodeID + " # of events " + numOfEvents +" " + path.toString());

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
