package ICN;

public class Data {
    private int size;
    private int owner;
    private int sequence;

    public Data (int size) {
        this.size = size;
    }

    public Data(int owner, int size) {
        this.owner = owner;
        this.size = size;
    }
    /*
     *Copy constructor
     */
    public Data (Data dataObject) {
        this.owner = dataObject.owner;
        this.size = dataObject.size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getSequence() { return sequence; }

    public void setSequence(int sequence) { this.sequence = sequence; }
}
