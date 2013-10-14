package azspcs.mapred;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class MapDriver extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new MapDriver(), args);
        System.exit(res);
    }

    public int run(String[] args) throws Exception {
        String in = args[0];
        String outPrefix = args[1];

        runJob(in, outPrefix + 0);

        for(int i = 0; i < 10; i++){
            runJob(outPrefix + i, outPrefix + (i + 1));
        }
        return 0;
    }

    private void runJob(String from, String to) throws IOException {
        JobConf conf = new JobConf(getConf(), MapDriver.class);
        conf.setJobName("graphs");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(GraphMapper.class);
        conf.setCombinerClass(GraphReducer.class);
        conf.setReducerClass(GraphReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(from));
        FileOutputFormat.setOutputPath(conf, new Path(to));

        JobClient.runJob(conf);
    }
}
