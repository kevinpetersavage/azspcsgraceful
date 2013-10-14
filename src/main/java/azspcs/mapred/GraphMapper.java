package azspcs.mapred;

import azspcs.GraphCloner;
import azspcs.OutputFormat;
import azspcs.checking.Completer;
import azspcs.iterate.IterativeGraphWrapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.IOException;
import java.util.List;

public class GraphMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
    private final int highestEdge = 56;
    private final int nodes = 13;
    private final GraphParser parser = new GraphParser();
    private final GraphCloner cloner = new GraphCloner();
    private final Completer completer = new Completer(highestEdge);
    private final OutputFormat format = new OutputFormat();

    @Override
    public void map(LongWritable longWritable, Text text, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        String string = text.toString();
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph = parser.parse(string);
        IterativeGraphWrapper wrapper = new IterativeGraphWrapper(cloner, graph, nodes, highestEdge, completer);
        List<SimpleWeightedGraph<Integer,DefaultWeightedEdge>> iterates = wrapper.iterate();
        for (SimpleWeightedGraph<Integer, DefaultWeightedEdge> iterate : iterates) {
            output.collect(new Text(format.format(iterate)), new Text(""));
        }
    }
}
