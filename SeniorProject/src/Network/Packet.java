package Network;

import Enums.PacketType;
import Helper.SimPath;
import ICN.Data;

public class Packet {
    private long ID;
    private int sourceNodeID;
    private int destinationNodeID;
    private Data data;
    private SimPath simPath;
    private PacketType packetType;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getSourceNodeID() {
        return sourceNodeID;
    }

    public void setSourceNodeID(int sourceNodeID) {
        this.sourceNodeID = sourceNodeID;
    }

    public int getDestinationNodeID() {
        return destinationNodeID;
    }

    public void setDestinationNodeID(int destinationNodeID) {
        this.destinationNodeID = destinationNodeID;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public SimPath getSimPath() {
        return simPath;
    }

    public void setSimPath(SimPath simPath) {
        this.simPath = simPath;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }
}
