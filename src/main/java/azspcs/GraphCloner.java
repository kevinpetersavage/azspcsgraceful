package azspcs;


import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Set;

public class GraphCloner {
    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> cloneThe(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph) {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> clone = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        for (Integer integer : graph.vertexSet()) {
            clone.addVertex(integer);
        }
        Set<DefaultWeightedEdge> edges = graph.edgeSet();
        for (DefaultWeightedEdge edge : edges) {
            clone.setEdgeWeight(clone.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge)), graph.getEdgeWeight(edge));
        }
        return clone;
    }
}
