import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class pageRankMapper
  extends Mapper<LongWritable, Text, Text, DoubleWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    // each line
    String line = value.toString();
    // exact and store as an array, matches sequence of one or more whitespace characters.
    String[] pages =  line.split("\\s+");
    StringBuilder sb = new StringBuilder();

    float number_of_outlinks = pages.length - 2;
    
    for(int i = 1; i <= pages.length-2; i++){
        Double temp = pages[pages.length-1] / number_of_outlink;
        context.write(new Text(pages[i]), new Text(String.valueOf(temp)));
        sb.append(pages[i] + " ");
    }

    context.write(new Text(page[0]), new Text(sb.toString()));

  }
}
// ^^ pageRankMapper
