package Helper;

import Simulator.Simulator;

import java.util.ArrayList;

public class Path {
    private double cost;
    private ArrayList<Integer> path = new ArrayList<Integer>();

    public Path(){

    }
    public Path(ArrayList<Integer> path){
        this.path=path;
        cost = calculateCost();
    }

    public void setReverse(){
        ArrayList<Integer> reversePath= new ArrayList<Integer>();
        for(int i = path.size(); i>0;i--){
            reversePath.add(path.get(i-1));
        }
        this.path= reversePath;
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

    public double calculateCost() {
        int firstID = 0;
        int secondID = 0;
        int sum=0;
        Pair<Integer,Integer> p;
        for(int i =0; i< path.size()-1; i++){
            firstID = path.get(i);
            secondID = path.get(i+1);
            p = new Pair<>(firstID,secondID);
            sum+= Simulator.newtworkLinks.get(p).getCost();
        }

        return cost;
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
