package Simulator;

import Enums.EventType;
import Network.Packet;

public class Event implements Comparable {
    private double time;//In milisecond
    private EventType eventType;


    public Event(){

    }

    public Event(double time,EventType eventType){
        this.time=time;
        this.eventType=eventType;
    }

    @Override
    public int compareTo(Object o) {
        if(this.time == ((Event)o).getTime()) return 0;
        else if(this.time > ((Event)o).getTime()) return 1;
        else   return -1;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Event type: "+this.eventType+ " at time "+ this.time + "\n";
    }

    public void runEvent() {

    }

}
