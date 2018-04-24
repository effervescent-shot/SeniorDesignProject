package Network;

public class Link {
    private int ID;
    private Node firstNode;
    private Node secondNode;
    private int capacity; // in Mbps
    private double load;
    private double cost;

    private double deltaLoad = 0;
    private double deltaTime = 1;

    public Link(int ID, int capacity) {
        this.ID = ID;
        this.capacity = capacity;
        this.load = 0;
        this.cost = 1/(capacity-load);
    }

    public Link(int ID, Node firstNode, Node secondNode, int capacity) {
        this.ID = ID;
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.capacity = capacity;
        this.load = 0;
        this.cost = 1/(capacity-load);
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public double getCost() {
        cost = 1/(capacity-load);
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Node getFirstNode(){
        return this.firstNode;
    }

    public Node getSecondNode(){
        return this.secondNode;
    }

    public void resetLoad() { deltaLoad = 0; deltaTime = 1; }

    public void updateLoad() {
        load = deltaLoad/deltaTime;
    }

    public void augmentLoad(double packetSize) {
        deltaLoad += packetSize;
        deltaTime += packetSize/capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Link)) return false;
        Link linko = (Link) o;
        return this.firstNode.equals(linko.getFirstNode()) &&
                this.secondNode.equals(linko.getSecondNode());
    }
}
