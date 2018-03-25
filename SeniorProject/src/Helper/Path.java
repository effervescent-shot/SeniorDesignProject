package Helper;

import java.util.ArrayList;

public class Path {
    private double cost;
    private ArrayList<Integer> path = new ArrayList<Integer>();

    public Path(){

    }
    public Path(double cost,ArrayList<Integer> path){
        this.cost=cost;
        this.path=path;
    }

    public void setReverse(){
        ArrayList<Integer> reversePath= new ArrayList<Integer>();
        for(int i = path.size(); i>0;i--){
            
        }
    }


    public int nextNodeID(int currentNode){
        if(path.size()-1==path.indexOf(currentNode)){
            return -1;
        }
        return path.get(path.indexOf(currentNode)+1);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        String s="Path{" +
                "cost=" + cost + ", path=[" ;

        for (int i : path){
            s+=i+"/t";
        }
         s+= path + "]}";
        return s;
    }
}
