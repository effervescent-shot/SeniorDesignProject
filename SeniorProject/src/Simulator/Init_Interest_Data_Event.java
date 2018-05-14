package Simulator;

import Enums.EventType;
import ICN.Prefix;

import static Enums.EventType.INITIALIZE_DATA;
import static Enums.EventType.INITIALIZE_INTEREST;

public class Init_Interest_Data_Event extends Event {
    private int sourceID;
    private int destinationID = -1;
    private Prefix prefix;
    private int nodeID;

    public Init_Interest_Data_Event(){

    }

    public Init_Interest_Data_Event (long time, EventType eventType, int source,  Prefix prefix) {
        super(time, eventType);
        this.sourceID = source;
        this.prefix = prefix;
    }

    public Init_Interest_Data_Event (long time, EventType eventType, int source, int destination,  Prefix prefix) {
       super(time, eventType);
       this.sourceID = source;
       this.destinationID = destination;
       this.prefix = prefix;
    }


    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public int getSourceID() { return sourceID; }

    public void setSourceID(int sourceID) { this.sourceID = sourceID; }

    public int getDestinationID() { return destinationID; }

    public void setDestinationID(int destinationID) { this.destinationID = destinationID; }

    public Prefix getPrefix() { return prefix; }

    public void setPrefix(Prefix prefix) { this.prefix = prefix; }

    ////////Fill later //////////
    @Override
    public void runEvent () {
        if(this.getEventType() == INITIALIZE_INTEREST) {
            //bufferlara ekle
            Simulator.networkNodes.get(getNodeID()).
                    Initialize_Interest( getTime(),
                            getPrefix());

        }

        if(this.getEventType() == INITIALIZE_DATA) {

        }
    }
}
