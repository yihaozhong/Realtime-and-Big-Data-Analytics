import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.mapreduce.Counter;

public class WordCountPercentage {
    private static final List<String> c_words = new ArrayList<>(Arrays.asList(
            "fuck",
            "f*ck",
            "f**k",
            "fvck",
            "shit",
            "sh*t",
            "s**t",
            "ass",
            "bitch",
            "nigga",
            "nigger",
            "n****",
            "n***a",
            "hell",
            "whore",
            "dick",
            "d*ck",
            "piss",
            "pussy",
            "slut",
            "puta",
            "tit",
            "damn",
            "fag",
            "cunt",
            "cum",
            "cock",
            "blowjob"));

    public static class TokenizerMapper
            extends Mapper<LongWritable, Text, Text, Text> {

        private Text word = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = value.toString().split("\\s+");

            for (String token : tokens) {
                if (c_words.contains(token.toLowerCase())) {
                    word.set(token.toLowerCase());
                    context.write(word, new Text("1"));
                    context.write(new Text("ALL"), new Text("1"));
                } else {
                    context.write(new Text("ALL"), new Text("1"));
                }
            }
        }
    }

    public static class IntSumReducer
            extends Reducer<Text, Text, Text, Text> {

        private int totalCount = 0;

        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;

            for (Text val : values) {
                sum += Integer.parseInt(val.toString());
            }
            totalCount += sum;

            context.write(key, new Text(String.valueOf(sum)));
        }

    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <in> <out>");
            System.exit(2);
        }
        Job job = new Job(conf, "word count percentage");
        job.setJarByClass(WordCountPercentage.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(1);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}