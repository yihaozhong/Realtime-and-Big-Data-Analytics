
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// to use the regex
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wordCountMapper
  extends Mapper<LongWritable, Text, Text, IntWritable> {
  
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    String line = value.toString();
    String regex = "[a-zA-Z]+";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);

    while(matcher.find()) {
        context.write(new Text(matcher.group()), new IntWritable(1));
    }
  }
}
// ^^ wordCountMapper
