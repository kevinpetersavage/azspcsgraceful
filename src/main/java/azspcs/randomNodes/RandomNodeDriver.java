package azspcs.randomNodes;

import azspcs.EmptyGraphFactory;
import azspcs.checking.Completer;
import azspcs.prob.Constant;
import azspcs.prob.RandomVariable;
import azspcs.prob.Variable;
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
        int notUsedEdges = 22;
        int maxEdges = maxEdgesInCompleteGraph - notUsedEdges;

        System.out.println("max edges " + maxEdges);

        Completer completer = new Completer(maxEdges);
        Random random = new Random();
        EmptyGraphFactory emptyGraphFactory = new EmptyGraphFactory();

        List<Variable> variables = Lists.newArrayList();
        variables.add(new Constant(0));
        variables.add(new Constant(1));
        variables.add(new Constant(maxEdges));
        for (int i = 3; i < nodes; i++){
            variables.add(new RandomVariable(2, maxEdges-1, random));
        }

        int bestScore = 0;
        Set<Integer> prev = Sets.newHashSet();
        while(true){
            SimpleWeightedGraph<Integer,DefaultWeightedEdge> graph = emptyGraphFactory.build();
            for (Variable variable : variables) {
                graph.addVertex(variable.get());
            }
            int score = completer.score(graph);
            if (score > maxEdges){
                completer.complete(graph);
            }
            int tweek = 2;
            if (score > (maxEdges - tweek)){
                for (Variable variable : variables) {
                    variable.boost( 10 * (score - maxEdges + tweek));
                }
            }

            if (score>=bestScore){
                bestScore = score;
                System.out.println(graph + "=>" + score + "," + variables);
            }
        }
    }
}
