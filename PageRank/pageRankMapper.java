import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class pageRankMapper
  extends Mapper<LongWritable, Text, Text, IntWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    // each line
    String line = value.toString();
    // exact and store as an array, matches sequence of one or more whitespace characters.
    String[] pages =  line.split("\\s+");
    
    float number_of_outlinks = pages.length - 2;
    
    for(int i = 1; i <= pages.length-2; i++){
        context.write(new )
    }
  }
}
// ^^ pageRankMapper
