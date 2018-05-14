package ICN;

public class Prefix {
    String prefixName;
    Data data;

    public Prefix(String prefixName, int size){
        this.prefixName=prefixName;
        data = new Data(size);
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public Data getData() { return data; }

    public void setData(Data data) { this.data = data; }
}
