package Network;

import Enums.PacketType;
import Helper.SimPath;
import ICN.Data;
import ICN.Prefix;
import Simulator.Simulator;

public class Packet {
    private static final int InterestPacketSize = 1; //MB
    private static final int DataPacketSize = 1024; //MB

    private static long ID = 10000;
    private int sourceNodeID;
    private int destinationNodeID;
    private Prefix prefix;
    private SimPath simPath;
    private PacketType packetType;

    private double CreationTime;
    private double TerminationTime;

    public Packet (double creationTime) {
       this.ID++;
       this.CreationTime = creationTime;
    }

    public Packet (double creationTime, PacketType packetType, SimPath path) {
        this.ID++;
        this.simPath = path;
        this.packetType = packetType;
        this.CreationTime = creationTime;
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

    public void terminatePacket(double terminationTime) {
        this.TerminationTime = terminationTime;
        Simulator.allPackets.add(this);
    }
}
