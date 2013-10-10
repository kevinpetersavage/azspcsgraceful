package azspcs.iterate;

import azspcs.EmptyGraphFactory;
import azspcs.GraphCloner;
import azspcs.OutputFormat;
import azspcs.checking.Completer;
import com.google.common.collect.Lists;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class IterativeGraphWrapperTest {
    private final OutputFormat outputFormat = new OutputFormat();

    @Test public void iterateFrom0(){
        SimpleWeightedGraph<Integer,DefaultWeightedEdge> graph = new EmptyGraphFactory().build();
        graph.addVertex(0);
        int maxNodes = 5;
        int highestEdge = 9;

        IterativeGraphWrapper wrapper = new IterativeGraphWrapper(new GraphCloner(), graph, maxNodes, highestEdge, new Completer(highestEdge));
        List<SimpleWeightedGraph<Integer,DefaultWeightedEdge>> result = wrapper.iterate();

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> expected = new EmptyGraphFactory().build();
        expected.addVertex(0);
        expected.addVertex(9);
        expected.addEdge(0, 9);

        assertThat(result).hasSize(1);
        assertThat(outputFormat.format(result.get(0))).isEqualTo(outputFormat.format(expected));
    }

    @Test public void iterateFrom1(){
        SimpleWeightedGraph<Integer,DefaultWeightedEdge> graph = new EmptyGraphFactory().build();
        graph.addVertex(0);
        graph.addVertex(9);
        graph.addEdge(0, 9);
        int maxNodes = 5;
        int highestEdge = 9;

        IterativeGraphWrapper wrapper = new IterativeGraphWrapper(new GraphCloner(), graph, maxNodes, highestEdge, new Completer(highestEdge));
        List<SimpleWeightedGraph<Integer,DefaultWeightedEdge>> results = wrapper.iterate();

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> expected1 = new EmptyGraphFactory().build();
        expected1.addVertex(0);
        expected1.addVertex(1);
        expected1.addVertex(9);
        expected1.addEdge(0, 9);
        expected1.addEdge(9, 1);

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> expected2 = new EmptyGraphFactory().build();
        expected2.addVertex(0);
        expected2.addVertex(8);
        expected2.addVertex(9);
        expected2.addEdge(0, 9);
        expected2.addEdge(0, 8);

        assertThat(format(results)).containsOnly(outputFormat.format(expected1), outputFormat.format(expected2));
    }

    private List<String> format(List<SimpleWeightedGraph<Integer, DefaultWeightedEdge>> results) {
        List<String> strings = Lists.newArrayList();
        for (SimpleWeightedGraph<Integer, DefaultWeightedEdge> result : results) {
            strings.add(outputFormat.format(result));
        }
        return strings;
    }


}
