package azspcs;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class EmptyGraphFactory {
    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> build(){
        return new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }
}
