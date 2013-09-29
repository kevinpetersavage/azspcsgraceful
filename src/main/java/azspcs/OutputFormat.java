package azspcs;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Collections;
import java.util.List;

public class OutputFormat {
    public String format(SimpleWeightedGraph<Integer,DefaultWeightedEdge> graph) {
        List<String> edges = Lists.newArrayList();
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            Integer source = graph.getEdgeSource(edge);
            Integer target = graph.getEdgeTarget(edge);
            if (source<target){
                edges.add(String.format("(%d,%d)", source, target));
            } else {
                edges.add(String.format("(%d,%d)", target, source));
            }
        }
        Collections.sort(edges);
        return Joiner.on(",").join(edges);
    }
}
