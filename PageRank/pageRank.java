import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class pageRank {
public static void main(String[] args) throws Exception { 
    if (args.length != 2) {
      System.err.println("Usage: pageRank <input path> <output path>");
      System.exit(-1);
    }

    for(int i = 0; i<3; i++){
        Job job = Job.getInstance(); 
        job.setJarByClass(pageRank.class); 
        job.setJobName("Page Rank");
        job.setNumReduceTasks(1); // 1 Reduce task

        if(i == 0){
            FileInputFormat.addInputPath(job, new Path(args[0])); 
            FileOutputFormat.setOutputPath(job, new Path("lab3/output"+ i));
        }
        else if(i == 1){
            FileInputFormat.addInputPath(job, new Path("lab3/output"+ (i-1))); 
            FileOutputFormat.setOutputPath(job, new Path("lab3/output"+ i));
        }
        else{
            FileInputFormat.addInputPath(job, new Path("lab3/output"+ (i-1))); 
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
        }


        job.setMapperClass(pageRankMapper.class);
        // job.setCombinerClass(pageRankReducer.class);
        job.setReducerClass(pageRankReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.waitForCompletion(true);
 }
    }
}