package azspcs.mapred;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;

public class GraphReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text text, Iterator<Text> textIterator, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        output.collect(new Text(text.toString()), new Text(""));
    }
}
