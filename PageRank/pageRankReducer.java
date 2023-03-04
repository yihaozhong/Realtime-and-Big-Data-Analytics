import java.io.IOException;
import org.apache.hadoop.io.NullWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class pageRankReducer
extends Reducer<Text, Text, NullWritable, Text> {
@Override
public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    double score = 0;
    String rest = "";
    
    // loop and sum up the page rank

    for (Text value : values) {
        try {  
            score += Double.parseDouble(value.toString());  
          } 
        catch(Exception e){  
            rest = value.toString();
          }  

    }

    // write the long string output
    context.write(NullWritable.get(), new Text(key + " " + rest + score));
      
    }
}