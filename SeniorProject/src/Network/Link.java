package Network;

import java.util.ArrayList;

public class Link {
    private int ID;
    private Node firstNode;
    private Node secondNode;
    private int capacity; // in Mbps
    private double load;
    private double cost;

    private double deltaLoad = 0;
    private double deltaTime = 1;

    private ArrayList<Double> linkLoadPerSecond = new ArrayList<>();

    public ArrayList<Double> getLinkLoadPerSecond() {
        return linkLoadPerSecond;
    }

    public void resetLink() {
        linkLoadPerSecond = new ArrayList<>();
        load = 0;
        deltaLoad = 0;
        deltaTime = 1;
    }

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
        this.capacity = capacity;  /// Mbps
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

    public void resetLoad() {
        if(linkLoadPerSecond.isEmpty()) {
            load = 0;
        } else {
            double sumLoad = 0;
            for(int i=1; i<5; i++){
                sumLoad += linkLoadPerSecond.get(linkLoadPerSecond.size()-i);
            }
            load = sumLoad/5;
        }
    }

    public void updateLoad() {
        //load = deltaLoad/deltaTime;
        linkLoadPerSecond.add(deltaLoad);
        deltaLoad = 0;
        //System.out.println("Here::::" + linkLoadPerSecond.size());
//        System.out.println("Node1: " + firstNode.getID() + " Node2: " + secondNode.getID() +
//                            " DeltaLoad: " + deltaLoad + " DeltaTime: " + deltaTime +
//                            "   Capacity: " + capacity + "   Load: " + load + "   Cost: " + cost );

    }

    public void augmentLoad(double packetSize) {  // packet size (MB)
        deltaLoad += 8*packetSize;
        deltaTime += 8*packetSize/capacity;
//        System.out.println("Node1: " + firstNode.getID() + " Node2: " + secondNode.getID() +
//                            " DeltaLoad: " + deltaLoad + " DeltaTime: " + deltaTime +
//                            "   Capacity: " + capacity + "   Load: " + load + "   Cost: " + cost );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Link)) return false;
        Link linko = (Link) o;
        return this.firstNode.equals(linko.getFirstNode()) &&
                this.secondNode.equals(linko.getSecondNode());
    }

    @Override
    public String toString() {
        return "Link__"+ firstNode.getID() + "_" + secondNode.getID();
    }


}
