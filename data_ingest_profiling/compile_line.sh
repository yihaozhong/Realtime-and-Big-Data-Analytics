rm final/*.java final/*.jar final/*.class
rm -r final/line_output
mv lineCountMinMaxAvg.java final/
cd final
javac -classpath `hadoop classpath` lineCountMinMaxAvg.java
jar cf lineCountMinMaxAvg.jar lineCountMinMaxAvg*.class
hadoop fs -rm -r final_2436/line_output
hadoop jar lineCountMinMaxAvg.jar lineCountMinMaxAvg final_2436/ingest_output final_2436/line_output

hadoop fs -ls final_2436/line_output
hadoop fs -get final_2436/line_output