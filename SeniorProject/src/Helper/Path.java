package Helper;

import java.util.ArrayList;

public class Path {
    private double cost;
    private ArrayList<Integer> path = new ArrayList<Integer>();


    public int nextNodeID(int currentNode){
        if(path.size()-1==path.indexOf(currentNode)){
            return -1;
        }
        return path.get(path.indexOf(currentNode)+1);
    }

}
