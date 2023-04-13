public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String line = value.toString();
    String[] tokens = line.split(" ");

    String netID = tokens[0];
    String[] courses = tokens[1].split(",");

    for (String course : courses) {
        context.write(new Text(course), new Text(netID));
    }
}
public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    List<String> students = new ArrayList<>();
    int count = 0;

    for (Text value : values) {
        students.add(value.toString());
        count++;
    }

    Collections.sort(students);
    String studentsStr = String.join(",", students);
    context.write(key, new Text(count + " " + studentsStr));
}
