package Simulator;

import Enums.EventType;
import Network.Packet;

public class Event implements Comparable {
    private long time;//In millisecond
    private EventType eventType;
    private Packet packet;


    public Event(){

    }

    public Event(long time,EventType eventType){
        this.time=time;
        this.eventType=eventType;
    }

    @Override
    public int compareTo(Object o) {
        if(this.time == ((Event)o).getTime()) return 0;
        else if(this.time > ((Event)o).getTime()) return 1;
        else   return -1;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setPacketType(EventType packetType) {
        this.eventType = packetType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
    public boolean hasPacket(){
        if(this.eventType==EventType.RUN_DIJKSTRA){
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Event type: "+this.eventType+ " at time "+ this.time + " and the packet is " + this.packet.getPacketType() + "\n"
                +this.packet.getSourceNodeID()+"-"+this.packet.getDestinationNodeID()+"\n"
                +this.packet.getSimPath().toString() +"\n";
    }
}
