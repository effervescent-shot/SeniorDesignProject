package Simulator;

import Enums.EventType;
import Helper.Pair;
import Network.Link;
import Network.Packet;

import static Enums.EventType.*;

public class Send_Receive_Event extends Event{
    private int from;    /// A-->B from A to B   //// B<--A to B from A
    private int to;
    private Packet aPackage;
    private Link aLink;

    public Send_Receive_Event(double time, EventType eventType, int from, int to, Packet pack) {
        super(time, eventType);
        this.from = from;
        this.to = to;
        this.aPackage = pack;
        this.aLink = Simulator.networkLinks.get(new Pair<Integer,Integer>(from,to));
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Packet getaPackage() {
        return aPackage;
    }

    public void setaPackage(Packet aPackage) {
        this.aPackage = aPackage;
    }

    public Link getaLink() {
        return aLink;
    }

    public void setaLink(Link aLink) {
        this.aLink = aLink;
    }

    ////////Fill later //////////////
    @Override
    public void runEvent () {
        if(this.getEventType() == SEND_INTEREST) {
                //Linki gönder, interest mi data mı onu gönder
        }

        if(this.getEventType() == SEND_DATA) {

        }

        if(this.getEventType() == RECEIVE_INTEREST) {

        }

        if(this.getEventType() == RECEIVE_DATA) {

        }
    }

    @Override
    public String toString() {
        return "Event type: "+super.getEventType()+ " at time "+ super.getTime() +
                " from " + from + " to " + to + "\n";
    }
}
