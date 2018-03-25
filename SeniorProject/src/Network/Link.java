package Network;

public class Link {
    private int ID;
    private Node firstNode;
    private Node secondNode;
    private long linkLength; //in meter

    public Link(int ID, int length) {
        this.ID = ID;
        this.linkLength = length;
    }

    public Link(int ID, Node firstNode, Node secondNode, int length) {
        this.ID = ID;
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.linkLength = length;
    }

    public long getLinkLength() {
        return linkLength;
    }

    public void setLinkLength(long linkLength) {
        this.linkLength = linkLength;
    }
}
