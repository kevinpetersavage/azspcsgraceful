package azspcs.mapred;

import azspcs.EmptyGraphFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class GraphParser {
    private final EmptyGraphFactory emptyGraphFactory = new EmptyGraphFactory();

    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> parse(String string) {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> build = emptyGraphFactory.build();
        String[] split = string.split(",");
        for (int i = 0; i < split.length; i+=2) {
            int a = Integer.valueOf(split[i].substring(1));
            int b = Integer.valueOf(split[i+1].substring(0, split[i+1].length()-1));

            addNode(build, a);
            addNode(build, b);
            build.addEdge(a,b);
        }
        return build;
    }

    private void addNode(SimpleWeightedGraph<Integer, DefaultWeightedEdge> build, int node) {
        if (!build.containsVertex(node)){
            build.addVertex(node);
        }
    }
}
