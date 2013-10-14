package azspcs.mapred;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Jo
 * Date: 14/10/13
 * Time: 23:10
 * To change this template use File | Settings | File Templates.
 */
public class GraphParserTest {
    @Test
    public void testParse() throws Exception {
        String example = "(0,16),(0,23),(0,30),(0,40),(0,42),(0,43),(0,9),(1,16),(1,23),(1,30),(1,37),(1,40),(1,42),(1,9),(16,30),(16,37),(16,40),(16,42),(16,43),(23,40),(23,42),(23,43),(30,40),(30,42),(30,43),(37,42),(37,43),(40,42),(40,43),(42,43),(5,16),(5,23),(5,30),(5,37),(5,40),(5,42),(5,43),(5,9),(9,16),(9,37),(9,40),(9,42),(9,43)";

        SimpleWeightedGraph<Integer,DefaultWeightedEdge> graph = new GraphParser().parse(example);
        assertThat(graph.vertexSet().size()).isEqualTo(11);
        assertThat(graph.edgeSet().size()).isEqualTo(43);
    }
}
