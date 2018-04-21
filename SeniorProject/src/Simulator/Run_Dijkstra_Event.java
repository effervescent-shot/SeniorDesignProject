package Simulator;

import Enums.EventType;
import kPath.Graph;

public class Run_Dijkstra_Event extends Event {

    Run_Dijkstra_Event (long time, EventType eventType) {
        super(time, eventType);
    }

    public void runEvent(Graph graph, int pathDegree) {
        Simulator.buildPaths(graph, pathDegree);
    }

}
