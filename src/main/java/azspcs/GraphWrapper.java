package azspcs;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphWrapper {
    private final SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph;
    private final Map<Integer, Integer> nodeToScore;
    private final GraphCloner cloner = new GraphCloner();

    public GraphWrapper(SimpleWeightedGraph<Integer,DefaultWeightedEdge> graph, Map<Integer, Integer> nodeToScore) {
        this.graph = graph;
        this.nodeToScore = nodeToScore;
    }

    public Set<GraphWrapper> iterate(){
        Set<DefaultWeightedEdge> edges = graph.edgeSet();
        List<Integer> unusedWeights = possibleWeights(edges);
        List<DefaultWeightedEdge> possibleEdges = Lists.newArrayList();
        for (DefaultWeightedEdge edge : edges) {
            double edgeWeight = graph.getEdgeWeight(edge);
            if (edgeWeight>0){
                unusedWeights.remove(Integer.valueOf((int)edgeWeight));
            } else if (edgeHasOneNodeValue(edge)){
                possibleEdges.add(edge);
            }
        }
        Collections.sort(unusedWeights);

        return getGraphWrappers(unusedWeights, possibleEdges);
    }

    private Set<GraphWrapper> getGraphWrappers(List<Integer> unusedWeights, List<DefaultWeightedEdge> possibleEdges) {
        Set<GraphWrapper> iteration = Sets.newHashSet();
        for (DefaultWeightedEdge unusedEdge : possibleEdges) {
            for (Integer unusedWeight : unusedWeights) {
                GraphWrapper newGraph1 = createNewGraph(unusedEdge, unusedWeight);
                if (newGraph1 != null) iteration.add(newGraph1);
                GraphWrapper newGraph2 = createNewGraph(unusedEdge, -unusedWeight);
                if (newGraph2 != null) iteration.add(newGraph2);
            }
        }
        return iteration;
    }

    private GraphWrapper createNewGraph(DefaultWeightedEdge edge, Integer weight) {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> clonedGraph = cloner.cloneThe(graph);
        Map<Integer, Integer> clonedMap = Maps.newHashMap(nodeToScore);
        Integer populated = populated(edge);
        Integer unpopulated = unpopulated(edge);

        int newValue = nodeToScore.get(populated) + weight;
        if (newValue < 1) return null;
        clonedMap.put(unpopulated, newValue);

        Set<DefaultWeightedEdge> edges = clonedGraph.edgesOf(unpopulated);
        for (DefaultWeightedEdge defaultEdge : edges) {
            Integer score1 = clonedMap.get(clonedGraph.getEdgeTarget(defaultEdge));
            Integer score2 = clonedMap.get(clonedGraph.getEdgeSource(defaultEdge));
            if (score1 != null && score2 != null){
                int newWeight = Math.abs(score1 - score2);
                clonedGraph.setEdgeWeight(defaultEdge, newWeight);
            }
        }

        if (!verified(clonedGraph)) return null;
        return new GraphWrapper(clonedGraph, clonedMap);
    }

    private boolean verified(SimpleWeightedGraph<Integer, DefaultWeightedEdge> clonedGraph) {
        List<Integer> weights = Lists.newArrayList();
        for (DefaultWeightedEdge defaultEdge : clonedGraph.edgeSet()) {
            int edgeWeight = (int)clonedGraph.getEdgeWeight(defaultEdge);
            if (edgeWeight > clonedGraph.edgeSet().size()) {
                return false;
            } else if (edgeWeight>=1) {
                weights.add(edgeWeight);
            }
        }
        return Sets.newHashSet(weights).size() == weights.size();
    }

    private Integer populated(DefaultWeightedEdge edge) {
        Integer node1 = graph.getEdgeTarget(edge);
        Integer node2 = graph.getEdgeSource(edge);
        return nodeToScore.containsKey(node1) ? node1 : node2;
    }

    private Integer unpopulated(DefaultWeightedEdge edge) {
        Integer node1 = graph.getEdgeTarget(edge);
        Integer node2 = graph.getEdgeSource(edge);
        return nodeToScore.containsKey(node1) ? node2 : node1;
    }

    private boolean edgeHasOneNodeValue(DefaultWeightedEdge edge){
        return nodeToScore.containsKey(graph.getEdgeTarget(edge)) || nodeToScore.containsKey(graph.getEdgeSource(edge));
    }

    private List<Integer> possibleWeights(Set<DefaultWeightedEdge> edges) {
        List<Integer> usedWeights = Lists.newArrayList();
        for (int i = 1; i <= edges.size(); i++){
            usedWeights.add(i);
        }
        return usedWeights;
    }

    @Override
    public String toString() {
        List<String> edges = Lists.newArrayList();
        for (Integer source : graph.vertexSet()) {
            for (DefaultWeightedEdge edge : graph.edgesOf(source)) {
                Integer target = graph.getEdgeTarget(edge);
                if (source<target){
                    edges.add(String.format("(%d,%d)", nodeToScore.get(source), nodeToScore.get(target)));
                }
            }
        }
        return Joiner.on(",").join(edges);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphWrapper that = (GraphWrapper) o;

        if (nodeToScore != null ? !nodeToScore.equals(that.nodeToScore) : that.nodeToScore != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nodeToScore != null ? nodeToScore.hashCode() : 0;
    }
}
