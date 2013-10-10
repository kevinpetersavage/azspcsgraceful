package azspcs.iterate;

import azspcs.EmptyGraphFactory;
import azspcs.GraphCloner;
import azspcs.OutputFormat;
import azspcs.checking.Completer;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class CallNTimesDriver {
    public static void main(String[] args){
        int nodes = 11;
        int maxEdgesForCompleteGraph = (nodes * (nodes-1)) / 2;
        int maxEdges = maxEdgesForCompleteGraph - 15;

        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new EmptyGraphFactory().build();
        graph.addVertex(0);

        GraphCloner cloner = new GraphCloner();
        Completer completer = new Completer(maxEdges);
        depthFirst(graph, nodes, maxEdges, cloner, 0, completer);
    }

    private static void depthFirst(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph, int nodes, int maxEdges, GraphCloner cloner, int depth, Completer completer) {
        List<SimpleWeightedGraph<Integer, DefaultWeightedEdge>> nextSteps = new IterativeGraphWrapper(cloner, graph, nodes, maxEdges, completer).iterate();

        if (depth >= maxEdges-1){
            if (!nextSteps.isEmpty()){
                System.out.println();
                System.out.println(new OutputFormat().format(nextSteps.get(0)));
                System.exit(0);
            }
        } else {
            int count = 0;
            for (SimpleWeightedGraph<Integer, DefaultWeightedEdge> nextStep : nextSteps) {
                if (depth < 8) System.out.println(count ++ + "/" + nextSteps.size() + " " +  depth);

                depthFirst(nextStep, nodes, maxEdges, cloner, depth+1, completer);
            }
        }
    }
}
