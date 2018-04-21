package Simulator;

import Enums.EventType;
import Helper.Pair;
import Network.Link;

public class Send_Receive_Event extends Event{
    private int from;    /// A-->B from A to B   //// B<--A to B from A
    private int to;
    private Package aPackage;
    private Link aLink;

    Send_Receive_Event(long time, EventType eventType, int from, int to, Package pack, Link link) {
        super(time, eventType);
        this.from = from;
        this.to = to;
        this.aPackage = pack;
        this.aLink = Simulator.newtworkLinks.get(new Pair<Integer,Integer>(from,to));
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

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public Link getaLink() {
        return aLink;
    }

    public void setaLink(Link aLink) {
        this.aLink = aLink;
    }
}
