package Network;

import Enums.NodeType;

public class Node {
    private int ID;
    private String name;
    private NodeType type;

    public Node (int ID) {
        this.ID = ID;
    }

    public Node(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Node (int ID, NodeType type) {
        this.ID = ID;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}
