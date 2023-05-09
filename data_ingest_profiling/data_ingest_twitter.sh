wget -O t_d_1.txt https://archive.org/download/twitter_cikm_2010/twitter_cikm_2010.zip/test_set_tweets.txt
wget -O t_d_2.txt https://archive.org/download/twitter_cikm_2010/twitter_cikm_2010.zip/training_set_tweets.txt 
cat t_d_*.txt >> all_tweets_raw.txt

hadoop fs -mkdir final_2436
hadoop fs -put all_tweets_raw.txt final_2436/all_tweets_raw.txt

hadoop fs -ls final_2436
