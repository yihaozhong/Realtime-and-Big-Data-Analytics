rm final/*.java final/*.jar final/*.class
rm -r final/profile_output
mv WordCountPercentage.java final/
cd final
javac -classpath `hadoop classpath` WordCountPercentage.java
jar cf wordcountpercentage.jar WordCountPercentage*.class
hadoop fs -rm -r final_2436/profile_output
hadoop jar wordcountpercentage.jar WordCountPercentage final_2436/ingest_output final_2436/profile_output

hadoop fs -ls final_2436/profile_output
hadoop fs -get final_2436/profile_output