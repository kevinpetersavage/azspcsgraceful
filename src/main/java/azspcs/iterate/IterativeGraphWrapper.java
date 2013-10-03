package azspcs.iterate;


import azspcs.GraphCloner;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

public class IterativeGraphWrapper {
    private final GraphCloner cloner;
    private final SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
    private final int maxNodes;
    private final int highestEdge;

    public IterativeGraphWrapper(GraphCloner cloner, SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph, int maxNodes, int highestEdge) {
        this.cloner = cloner;
        this.graph = graph;
        this.maxNodes = maxNodes;
        this.highestEdge = highestEdge;
    }

    public List<SimpleWeightedGraph<Integer,DefaultWeightedEdge>> iterate(){
        int edgesAlreadyAdded = graph.edgeSet().size();
        int edgeToAdd = highestEdge - edgesAlreadyAdded;

        List<SimpleWeightedGraph<Integer, DefaultWeightedEdge>> result = newArrayList();
        for (Integer node : graph.vertexSet()) {
            result.addAll(attachNode(node, node + edgeToAdd));
            result.addAll(attachNode(node, node - edgeToAdd));
        }
        return result;
    }

    private List<SimpleWeightedGraph<Integer, DefaultWeightedEdge>> attachNode(int node, int newNode) {
        if (newNode > highestEdge || newNode < 0) return emptyList();

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> modified = cloner.cloneThe(graph);
        if (!modified.containsVertex(newNode)) {
            if (modified.vertexSet().size() < maxNodes){
                modified.addVertex(newNode);
            } else {
                return emptyList();
            }
        }
        if (modified.containsEdge(node, newNode)){
            return emptyList();
        }

        modified.addEdge(node, newNode);
        return newArrayList(modified);
    }
}
