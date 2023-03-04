import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class pageRankMapper
  extends Mapper<LongWritable, Text, Text, Text> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    // each line
    String line = value.toString();
    // exact and store as an array, matches sequence of one or more whitespace characters.
    String[] pages =  line.split("\\s+");

    StringBuilder sb = new StringBuilder();

    double number_of_outlinks = pages.length - 2;
    
    // loop the middle
    for(int i = 1; i <= pages.length-2; i++){
        double temp = Double.parseDouble(pages[pages.length-1]) / number_of_outlinks;
        context.write(new Text(pages[i]), new Text(Double.toString(temp)));
        sb.append(pages[i] + " ");
    }
    // write the final 
    context.write(new Text(pages[0]), new Text(sb.toString()));

  }
}
// ^^ pageRankMapper
