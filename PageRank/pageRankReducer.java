import java.io.IOException;
import org.apache.hadoop.io.IntWritable; import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class pageRankReducer
extends Reducer<Text, IntWritable, Text, IntWritable> {
@Override
public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    double score = 0;
    String adj = "";
    
    for (Writable value : values) {
        if (value instanceof DoubleWritable) {
            score += ((DoubleWritable) value).get();
        } else if (value instanceof Text) {
            adj = value.toString();
        }
    }
    
    String output = key.toString() + " " + adj + score;
    
    context.write(NullWritable.get(), new Text(output));
      
    }
}