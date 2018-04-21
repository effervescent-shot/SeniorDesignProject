package ICN;

public class Prefix {
    String prefixName;
    Data data;

    public Prefix(){

    }
    public Prefix(String prefixName){
        this.prefixName=prefixName;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }
}
