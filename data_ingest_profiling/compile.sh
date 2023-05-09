rm final/*.java final/*.jar final/*.class
mv ExtractMessageContent.java final/
cd final
javac -classpath `hadoop classpath` *.java
jar cvf ExtractMessageContent.jar Extract*.class
hadoop fs -rm -r final_2436/ingest_output
hadoop jar ExtractMessageContent.jar ExtractMessageContent final_2436/all_tweets_raw.txt final_2436/ingest_output

hadoop fs -getmerge final_2436/ingest_output part-r-00000
hadoop fs -ls final_2436/ingest_output
hadoop fs -get final_2436/ingest_output