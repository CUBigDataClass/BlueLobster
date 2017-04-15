Useful links: </br>
slf4j API documentation: https://www.slf4j.org/manual.html


Info for getting kafka started on AWS with twitter feed:
Each of the following scripts will not be able to run as a daemon 
<code>
cd kafka/kafka_2.11-0.10.2.0
. 1_start_server.sh
</code>
<code>
cd kafka/kafka_2.11-0.10.2.0
. 2_start_server.sh
</code>

<code>
cd kafka/kafka_2.11-0.10.2.0
. 2_start_server.sh
</code>

Now that the a kafka topic named 'twitter-topic' is running (with a replication
factor of 3) we can start our twitter producer. 

<code>
java -jar twitter.jar 
</code>

Now there are scripts that can be run as a utility for checking correctnes.
<code>
cd kafka/kafka_2.11-0.10.2.0
</code>

To check the twitter json info being written to the topic:
<code>
. console_consumer.sh
</code>

To check the currently running topics:
<code>
. check.sh
</code>

To check the master node and replication factor of the twitter-topic:
<code>
. properties.sh
</code>


