package Simulator;

import Enums.EventType;
import Helper.Pair;
import Network.Link;
import Network.Packet;

import static Enums.EventType.*;

public class Send_Receive_Event extends Event{
    private int from;    /// A-->B from A to B   //// B<--A to B from A
    private int to;
    private Packet packet;
    private Link link;

    public Send_Receive_Event(double time, EventType eventType, int from, int to, Packet pack) {
        super(time, eventType);
        this.from = from;
        this.to = to;
        this.packet = pack;
        this.link = Simulator.networkLinks.get(new Pair<Integer,Integer>(from,to));
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

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    ////////Fill later //////////////
    @Override
    public void runEvent () {
        if(this.getEventType() == SEND_INTEREST) {
            //System.out.println("Who let the dogs out!");
            //Simulator.networkNodes.get(this.from).Send(this.link, this.getEventType());
        }

        else if(this.getEventType() == SEND_DATA) {

        }

        else if(this.getEventType() == RECEIVE_INTEREST) {

        }

        else if(this.getEventType() == RECEIVE_DATA) {

        } else {

        }
    }

    @Override
    public String toString() {
        return "Event type: "+this.getEventType()+ " at time "+ this.getTime() +
                " from " + from + " to " + to + "\n";
    }
}
