import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class lineCountMinMaxAvg {

    public static class lineCountMinMaxAvgMapper
            extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text message = new Text();

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            int lineLength = line.split("\\s+").length;
            // System.out.println(parts[0]);
            context.write(new IntWritable(lineLength), one);
        }
    }

    public static class lineCountMinMaxAvgReducer extends Reducer<IntWritable, IntWritable, Text, IntWritable> {
        private int max = 0;
        private int min = Integer.MAX_VALUE;
        private int totalLength = 0;
        private int totalCount = 0;

        @Override
        public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            max = Math.max(max, key.get());
            min = Math.min(min, key.get());
            totalLength += key.get() * sum;
            totalCount += sum;
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            context.write(new Text("Max length: "), new IntWritable(max));
            context.write(new Text("Min length: "), new IntWritable(min));
            context.write(new Text("Total lines: "), new IntWritable(totalCount));
            context.write(new Text("Average length: "), new IntWritable(totalLength / totalCount));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "lineCountMinMaxAvg");

        job.setJarByClass(lineCountMinMaxAvg.class);
        job.setMapperClass(lineCountMinMaxAvgMapper.class);
        job.setReducerClass(lineCountMinMaxAvgReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setNumReduceTasks(1);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}