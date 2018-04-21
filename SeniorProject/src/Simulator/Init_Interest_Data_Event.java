package Simulator;

import Enums.EventType;
import ICN.Prefix;

public class Init_Interest_Data_Event extends Event {
    private int sourceID;
    private int destinationID = -1;
    private Prefix prefix;

    Init_Interest_Data_Event (long time, EventType eventType, int source,  Prefix prefix) {
        super(time, eventType);
        this.sourceID = source;
        this.prefix = prefix;
    }

    Init_Interest_Data_Event (long time, EventType eventType, int source, int destination,  Prefix prefix) {
       super(time, eventType);
       this.sourceID = source;
       this.destinationID = destination;
       this.prefix = prefix;
    }

    public int getSourceID() { return sourceID; }

    public void setSourceID(int sourceID) { this.sourceID = sourceID; }

    public int getDestinationID() { return destinationID; }

    public void setDestinationID(int destinationID) { this.destinationID = destinationID; }

    public Prefix getPrefix() { return prefix; }

    public void setPrefix(Prefix prefix) { this.prefix = prefix; }
}
