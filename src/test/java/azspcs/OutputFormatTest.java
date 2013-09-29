package azspcs;


import azspcs.iterate.IterativeGraphWrapper;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class OutputFormatTest {
    @Test
    public void shouldProduceSortedStringOutput(){
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new EmptyGraphFactory().build();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(3);

        graph.addEdge(1,3);
        graph.addEdge(0,3);
        graph.addEdge(1,0);

        String output = new OutputFormat().format(graph);
        assertThat(output).isEqualTo("(0,1),(0,3),(1,3)");
    }

}
