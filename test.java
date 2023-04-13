// Mapper function
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String line = value.toString();
    StringTokenizer tokenizer = new StringTokenizer(line);

    while (tokenizer.hasMoreTokens()) {
        String word = tokenizer.nextToken();
        int length = word.length();
        context.write(new IntWritable(length), new IntWritable(1));}
}
public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    int count = 0;
    for (IntWritable value : values) {
        count += value.get();
    }
    context.write(key, new IntWritable(count));
}
