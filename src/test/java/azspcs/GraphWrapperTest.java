package azspcs;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class GraphWrapperTest {
    @Test
    public void shouldProduceCorrectStringOutput(){
        GraphWrapper graphWrapper = createTestGraphWrapper();
        String representation = graphWrapper.toString();
        assertThat(representation).isEqualTo("(0,1),(0,null),(1,null)");
    }

    @Test
    public void shouldIterateToProduceCorrectStringOutput(){
        GraphWrapper graphWrapper = createTestGraphWrapper();
        Set<GraphWrapper> iterate = graphWrapper.iterate();

        assertThat(iterate).hasSize(1);
        assertThat(iterate.iterator().next().toString()).isEqualTo("(0,1),(0,3),(1,3)");
    }

    @Test
    public void shouldIterateTwice(){
        Map<Integer,Integer> scores = Maps.newHashMap();
        scores.put(0,0);

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        graph.setEdgeWeight(graph.addEdge(0, 1), -1.);
        graph.setEdgeWeight(graph.addEdge(0, 2), -1.);
        graph.setEdgeWeight(graph.addEdge(1, 2), -1.);

        GraphWrapper graphWrapper = new GraphWrapper(graph, scores);
        Set<GraphWrapper> once = graphWrapper.iterate();
        System.out.println(once);
        Set<GraphWrapper> twice = Sets.newHashSet();
        for (GraphWrapper wrapper : once) {
            twice.addAll(wrapper.iterate());
        }
        assertThat(twice).hasSize(4);

    }

    @Test
    public void shouldIterateAnotherKnownCase(){
        Map<Integer,Integer> scores = Maps.newHashMap();
        scores.put(0,0);
        scores.put(1,6);

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        for (int i = 0; i < 4; i++){
            graph.addVertex(i);
        }

        for (int i = 0; i < 4; i++){
            for (int j = i+1; j < 4; j++){
                graph.addEdge(i, j);
            }
        }

        for (DefaultWeightedEdge defaultWeightedEdge : graph.edgeSet()) {
            graph.setEdgeWeight(defaultWeightedEdge, -1);
        }

        graph.setEdgeWeight(graph.getEdge(0, 1), 6);

        Set<GraphWrapper> graphWrappers = Sets.newHashSet(new GraphWrapper(graph, scores));
        while (!graphWrappers.isEmpty()){
            Set<GraphWrapper> iterations = Sets.newHashSet();
            for (GraphWrapper graphWrapper : graphWrappers) {
                iterations.addAll(graphWrapper.iterate());
            }
            System.out.println(graphWrappers);
            graphWrappers = iterations;
        }
    }

    private GraphWrapper createTestGraphWrapper() {
        Map<Integer,Integer> scores = Maps.newHashMap();

        scores.put(0,0);
        scores.put(1,1);

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        graph.setEdgeWeight(graph.addEdge(0, 1), 1.);
        graph.setEdgeWeight(graph.addEdge(0, 2), -1.);
        graph.setEdgeWeight(graph.addEdge(1, 2), -1.);

        return new GraphWrapper(graph, scores);
    }
}
