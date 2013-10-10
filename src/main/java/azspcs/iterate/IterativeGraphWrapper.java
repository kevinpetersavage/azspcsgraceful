package azspcs.iterate;


import azspcs.GraphCloner;
import azspcs.OutputFormat;
import azspcs.checking.Completer;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

public class IterativeGraphWrapper {
    private final GraphCloner cloner;
    private final SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
    private final int maxNodes;
    private final int highestEdge;
    private final Completer completer;

    public IterativeGraphWrapper(GraphCloner cloner, SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph, int maxNodes, int highestEdge, Completer completer) {
        this.cloner = cloner;
        this.graph = graph;
        this.maxNodes = maxNodes;
        this.highestEdge = highestEdge;
        this.completer = completer;
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

        if (modified.vertexSet().size() == maxNodes && !completer.complete(modified)){
            return emptyList();
        }

        try{
            modified.addEdge(node, newNode);
            return newArrayList(modified);
        } catch (IllegalArgumentException e){
            return emptyList();
        }
    }


}
