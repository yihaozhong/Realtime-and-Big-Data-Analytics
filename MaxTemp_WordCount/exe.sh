javac -classpath `hadoop classpath` *.java;
jar cvf maxTemp.jar *.class;
hadoop jar maxTemp.jar MaxTemperature lab2/temperatureInputs.txt lab2/output