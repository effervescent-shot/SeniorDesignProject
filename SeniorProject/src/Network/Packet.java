package Network;

import Enums.PacketType;
import Helper.SimPath;
import ICN.Prefix;
import Simulator.Simulator;

public class Packet {
    private static final int InterestPacketSize = 1; //MB
    private static final int DataPacketSize = 8; //MB

    private static long sID = 1;
    private long ID;
    private int sourceNodeID;
    private int destinationNodeID;
    private Prefix prefix;
    private SimPath simPath;
    private PacketType packetType;

    private long CreationTime;
    private long TerminationTime;

    public Packet (long creationTime) {
       this.ID = sID;
       this.sID++;
       this.CreationTime = creationTime;
    }

    public Packet (long creationTime, PacketType packetType, SimPath path) {
        this.ID = sID;
        this.sID++;
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

    private static int getInterestPacketSize() { return InterestPacketSize; }

    private static int getDataPacketSize() { return DataPacketSize; }

    public int getPacketSize() {
        if(this.packetType == PacketType.INTEREST_PACKET)
            return getInterestPacketSize();

        return getDataPacketSize();
    }

    public void terminatePacket(long terminationTime) {
        this.TerminationTime = terminationTime;
        Simulator.allPackets.add(this);
    }

    public long getCreationTime() {
        return CreationTime;
    }

    public long getTerminationTime() {
        return TerminationTime;
    }

    @Override
    public String toString() {
        return ""+this.ID+" p_type:" + packetType+ "  __from: "+sourceNodeID+" to "+destinationNodeID+"   __created: " + CreationTime + " terminated: " + TerminationTime;
    }
}
