package Helper;

import Simulator.Simulator;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.Arrays;

public class SimPath {
    private double cost;
    private ArrayList<Integer> path; // = new ArrayList<Integer>();

    public SimPath(){
        path = new ArrayList<>();
    }


    public SimPath(ArrayList<Integer> path, double cost){
        this.path=path;
        this.cost = cost;
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

    public void addPath(int nodeID) {
        this.path.add(nodeID);
    }

    @Override
    public String toString() {
        return "SimPath{" +  "cost=" + cost + "  " + Arrays.toString(path.toArray()) + "\n";
    }
}
