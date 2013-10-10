package azspcs.prob;

import azspcs.GraphCloner;
import azspcs.OutputFormat;
import azspcs.checking.Completer;
import azspcs.iterate.IterativeGraphWrapper;
import com.google.common.collect.Lists;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Random;

public class ProbabilisticDriver {
    public static void main(String[] args) {
        int nodes = 11;
        int maxEdgesInCompleteGraph = (nodes * (nodes - 1)) / 2;
        int notUsedEdges = 15;
        int maxEdges = maxEdgesInCompleteGraph - notUsedEdges;

        GraphCloner cloner = new GraphCloner();
        Completer completer = new Completer(maxEdges);
        Random random = new Random();
        OutputFormat outputFormat = new OutputFormat();

        List<RandomVariable> variables = Lists.newArrayList();

        while(true){
            int index = 0;
            SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = singleNodeGraph(maxEdges);
            while (graph != null) {
                IterativeGraphWrapper graphWrapper = new IterativeGraphWrapper(cloner, graph, nodes, maxEdges, completer);
                List<SimpleWeightedGraph<Integer, DefaultWeightedEdge>> newGraphs = graphWrapper.iterate();
                if (variables.size()<=index+1){
                    for (RandomVariable boost : variables) {
                        boost.boost(1);
                    }
                }
                if (variables.size()<=index){
                    variables.add(new RandomVariable(maxEdges, random));
                }

                int i = variables.get(index).get();
                if (i < newGraphs.size()){
                    graph = newGraphs.get(i);
                } else {
                    if (graph.edgeSet().size()>9){
                        System.out.println("best had " + graph.edgeSet().size() + " edges " + graph.vertexSet().size() + " vertices");
                        System.out.println("variables were " + variables);
                        System.out.println(outputFormat.format(graph));
                        if (graph.edgeSet().size() == maxEdges) System.exit(0);
                    }
                    graph = null;
                }
                index++;
            }
        }
    }

    private static SimpleWeightedGraph<Integer, DefaultWeightedEdge> singleNodeGraph(int maxEdges) {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        graph.addVertex(0);
        graph.addVertex(maxEdges);
        graph.addEdge(0, maxEdges);
        return graph;
    }
}
