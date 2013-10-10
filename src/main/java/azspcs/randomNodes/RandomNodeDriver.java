package azspcs.randomNodes;

import azspcs.EmptyGraphFactory;
import azspcs.GraphCloner;
import azspcs.OutputFormat;
import azspcs.checking.Completer;
import azspcs.prob.RandomVariable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomNodeDriver {
    public static void main(String[] args){
        int nodes = 13;
        int maxEdgesInCompleteGraph = (nodes * (nodes - 1)) / 2;
        int notUsedEdges = 23;
        int maxEdges = maxEdgesInCompleteGraph - notUsedEdges;

        System.out.println("max edges " + maxEdges);

        Completer completer = new Completer(maxEdges);
        Random random = new Random();
        EmptyGraphFactory emptyGraphFactory = new EmptyGraphFactory();

        List<RandomVariable> variables = Lists.newArrayList();
        for (int i = 0; i < nodes; i++){
            variables.add(new RandomVariable(maxEdges, random));
        }

        int bestScore = 0;
        Set<Integer> prev = Sets.newHashSet();
        while(true){
            SimpleWeightedGraph<Integer,DefaultWeightedEdge> graph = emptyGraphFactory.build();
            for (RandomVariable variable : variables) {
                graph.addVertex(variable.get());
            }
            int score = completer.score(graph);
            if (score > maxEdges){
                completer.complete(graph);
            }
            int tweek = 4;
            if (score > (maxEdges - tweek)){
                for (RandomVariable variable : variables) {
                    variable.boost(10 * (score - maxEdges + tweek));
                }
            }

            if (score>=bestScore){
                System.out.println(graph + "=>" + score + "," + variables);
                bestScore = score;
            }
        }
    }
}
