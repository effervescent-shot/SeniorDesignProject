package Input;

import Simulator.Main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomTopology {
    static String fileSuffix;
    public static void randomGenerator(int nodeCount,int edgeCount,int prefixCount){
        fileSuffix=Main.fileSuffix;
        randomTopologyGenerator(nodeCount,edgeCount);
        randomPrefixGenerator(prefixCount);
        randomServePrefixGenerator(nodeCount,prefixCount);
        randomDemandPrefixGenerator(nodeCount,prefixCount);
    }

    public static void randomTopologyGenerator(int nodeCount,int edgeCount){

        try{
            PrintWriter writer = new PrintWriter("data/input"+fileSuffix+".txt", "UTF-8");
            PrintWriter writer2 = new PrintWriter("data/ginput"+fileSuffix+".txt", "UTF-8");
            writer.println(edgeCount);
            writer2.println(nodeCount);
            writer2.println("");
            int node1, node2;
            int capacityDegree;
            int capacity;
            for (int i = 0; i < edgeCount; i++) {
                node1 = (int)Math.floor(nodeCount*Math.random());
                node2 = (int)Math.floor(nodeCount*Math.random());
                while(node1==node2){
                    node2 = (int)Math.floor(nodeCount*Math.random());
                }
                capacityDegree=(int)Math.floor(4*Math.random()+8);
                capacity=(int)Math.pow(2,capacityDegree);
                writer.println("node"+node1+" node"+node2+" "+capacity);
                writer2.println(node1+" "+node2+" "+capacity);
                writer2.println(node2+" "+node1+" "+capacity);
            }
            writer.close();
            writer2.close();
        }catch (Exception e){

        }

    }
    public static void randomPrefixGenerator(int prefixCount){

        try{
            PrintWriter writer = new PrintWriter("data/prefix_input"+fileSuffix+".txt", "UTF-8");
            int capacityDegree;
            int capacity;
            writer.println(prefixCount);
            for (int i = 1; i <=prefixCount; i++) {
                capacityDegree=(int)Math.floor(3*Math.random()+9);
                capacity=(int)Math.pow(2,capacityDegree);
                writer.println("prefix"+i+" "+capacity);
            }
            writer.close();
        }catch (Exception e){

        }
    }

    public static void randomServePrefixGenerator(int nodeCount,int prefixCount){
        try{
            PrintWriter writer = new PrintWriter("data/serve_input"+fileSuffix+".txt", "UTF-8");
            writer.println(nodeCount);
            HashMap<Integer,ArrayList<Integer>> mMap = new HashMap<>();
            for (int i = 0; i < nodeCount; i++) {
                mMap.put(i,new ArrayList<>());
            }
            int nodeID;
            for (int i = 1; i <=prefixCount ; i++) {
                nodeID = (int)Math.floor(nodeCount*Math.random());
                mMap.get(nodeID).add(i);
            }
            int totalPrefix;
            int prefixID;
            for (int i = 0; i <nodeCount; i++) {
                totalPrefix = (int)Math.floor(prefixCount*Math.random());
                if(totalPrefix>mMap.get(i).size()){
                    while(totalPrefix>mMap.get(i).size()){
                         prefixID = (int)Math.floor(prefixCount*Math.random());
                         if(!mMap.get(i).contains(prefixID)){
                             mMap.get(i).add(prefixID);
                         }
                    }
                }
            }
            for (int i = 0; i <nodeCount ; i++) {
                String s = i + " ";
                for (int j = 0; j< mMap.get(i).size() ; j++) {
                    if(j==0){
                        s+="prefix"+mMap.get(i).get(j);
                    }
                    else{
                        s+=";prefix"+mMap.get(i).get(j);
                    }
                }
                writer.println(s);
            }
            writer.close();
        }catch (Exception e){

        }
    }

    public static void randomDemandPrefixGenerator(int nodeCount, int prefixCount){
        try{
            PrintWriter writer = new PrintWriter("data/demand_input"+fileSuffix+".txt", "UTF-8");
            writer.println(nodeCount);
            HashMap<Integer,ArrayList<Integer>> mMap = new HashMap<>();
            for (int i = 0; i < nodeCount; i++) {
                mMap.put(i,new ArrayList<>());
            }
            int nodeID;
            for (int i = 1; i <=prefixCount ; i++) {
                nodeID = (int)Math.floor(nodeCount*Math.random());
                mMap.get(nodeID).add(i);
            }
            int totalPrefix;
            int prefixID;
            for (int i = 0; i <nodeCount; i++) {
                totalPrefix = (int)Math.floor(prefixCount*Math.random());
                if(totalPrefix>mMap.get(i).size()){
                    while(totalPrefix>mMap.get(i).size()){
                        prefixID = (int)Math.floor(prefixCount*Math.random());
                        if(!mMap.get(i).contains(prefixID)){
                            mMap.get(i).add(prefixID);
                        }
                    }
                }
            }
            for (int i = 0; i <nodeCount ; i++) {
                String s = i + " ";
                for (int j = 0; j< mMap.get(i).size() ; j++) {
                    if(j==0){
                        s+="prefix"+mMap.get(i).get(j);
                    }
                    else{
                        s+=";prefix"+mMap.get(i).get(j);
                    }
                }
                writer.println(s);
            }
            writer.close();
        }catch (Exception e){

        }
    }

}
