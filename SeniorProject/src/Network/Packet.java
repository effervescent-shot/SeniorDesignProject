package Network;

import Enums.PacketType;
import Helper.SimPath;
import ICN.Data;
import ICN.Prefix;

public class Packet {
    private static final int InterestPacketSize = 128; //MB
    private static final int DataPacketSize = 1024; //MB

    private static long ID = 11000;
    private int sourceNodeID;
    private int destinationNodeID;
    private Prefix prefix;
    private SimPath simPath;
    private PacketType packetType;

    public Packet () {
       this.ID++;
    }

    public Packet (SimPath path, PacketType packetType) {
        this.ID++;
        this.simPath = path;
        this.packetType = packetType;
    }

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

    public Prefix getPrefix() { return prefix; }

    public void setPrefix(Prefix prefix) { this.prefix = prefix; }

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

    public static int getInterestPacketSize() { return InterestPacketSize; }

    public static int getDataPacketSize() { return DataPacketSize; }
}
