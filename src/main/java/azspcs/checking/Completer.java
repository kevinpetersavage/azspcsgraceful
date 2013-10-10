package azspcs.checking;

import azspcs.OutputFormat;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Set;
import java.util.TreeSet;

public class Completer {
    private final int highestEdge;

    public Completer(int highestEdge) {
        this.highestEdge = highestEdge;
    }

    public boolean complete(SimpleWeightedGraph<Integer, DefaultWeightedEdge> modified) {
        Set<Integer> edgeWeights = createEdgeWeights(modified);
        ContiguousSet<Integer> expected = ContiguousSet.create(Range.closed(0, highestEdge), DiscreteDomain.integers());
        boolean equals = edgeWeights.equals(expected);
        if (equals){
            for (Integer a : modified.vertexSet()) {
                for (Integer b : modified.vertexSet()) {
                    int abs = Math.abs(a - b);
                    if (abs > 0 && edgeWeights.remove(abs)){
                        modified.addEdge(a, b);
                    }
                }
            }
            System.out.println(new OutputFormat().format(modified));
            System.exit(0);
        }
        return false;
    }

    public int score(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph){
        Set<Integer> edgeWeights = createEdgeWeights(graph);
        return edgeWeights.size();
    }

    private Set<Integer> createEdgeWeights(SimpleWeightedGraph<Integer, DefaultWeightedEdge> modified) {
        Set<Integer> edgeWeights = Sets.newHashSet();
        for (Integer a : modified.vertexSet()) {
            for (Integer b : modified.vertexSet()) {
                edgeWeights.add(Math.abs(a - b));
            }
        }
        return edgeWeights;
    }
}
