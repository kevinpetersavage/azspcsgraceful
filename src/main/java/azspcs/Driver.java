package azspcs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Driver {
    public static void main(String[] args){
        int nodes = 11;
        int maxEdges = (nodes * (nodes-1)) / 2;
        int notUsedEdges = 11;

        Map<Integer,Integer> scores = Maps.newHashMap();
        scores.put(0,0);
        scores.put(1,maxEdges-notUsedEdges);

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        addVerticies(nodes, graph);
        addEdges(nodes, graph);
        setDefaultWeight(graph);
        removeEdges(notUsedEdges, graph);
        graph.setEdgeWeight(graph.getEdge(0, 1), maxEdges - notUsedEdges);

        Set<GraphWrapper> graphWrappers = Sets.newHashSet(new GraphWrapper(graph, scores));
        while (!graphWrappers.isEmpty()){
            Set<GraphWrapper> iterations = Sets.newHashSet();
            for (GraphWrapper graphWrapper : graphWrappers) {
                iterations.addAll(graphWrapper.iterate());
            }
            System.out.println("" + graphWrappers.size());
            System.out.println(" " + graphWrappers.iterator().next());
            graphWrappers = iterations;
        }
    }

    private static void removeEdges(int notUsedEdges, SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
        List<DefaultWeightedEdge> defaultWeightedEdges = Lists.newArrayList(graph.edgeSet());
        Collections.shuffle(defaultWeightedEdges);
        for (DefaultWeightedEdge toRemove : defaultWeightedEdges.subList(0, notUsedEdges)) {
            graph.removeEdge(toRemove);
        }
    }

    private static void setDefaultWeight(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
        for (DefaultWeightedEdge defaultWeightedEdge : graph.edgeSet()) {
            graph.setEdgeWeight(defaultWeightedEdge, -1);
        }
    }

    private static void addEdges(int nodes, SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
        for (int i = 0; i < nodes; i++){
            for (int j = i+1; j < nodes; j++){
                graph.addEdge(i, j);
            }
        }
    }

    private static void addVerticies(int nodes, SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
        for (int i = 0; i < nodes; i++){
            graph.addVertex(i);
        }
    }
}
