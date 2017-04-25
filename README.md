Useful links: </br>
slf4j API documentation: https://www.slf4j.org/manual.html </br>
Cassandra in Python:
http://datastax.github.io/python-driver/getting_started.html
</br>
Cassandra in python installation: </br>
<code>
pip install cassandra-driver </br>
</code>


Info for getting kafka started on AWS with twitter feed:
Each of the following scripts will not be able to run as a daemon
</br>
<code>
cd kafka/kafka_2.11-0.10.2.0
</code>
</br>
<code>
. zookeeper_start.sh </br>
. 1_start_server.sh
</code>
</br>
<code>
. 2_start_server.sh
</br>
</code>
</br>
<code>
. 2_start_server.sh
. topic_start.sh </br>
</code>
</br>
Now that the a kafka topic named 'twitter-topic' is running (with a replication
factor of 3) we can start our twitter producer. The file can be found in the
directory twitter-kafa
</br>
<code>
java -jar twitter.jar 
</code>
</br>
Now there are scripts that can be run as a utility for checking correctnes.
</br>
<code>
cd kafka/kafka_2.11-0.10.2.0
</code>
</br>
To check the twitter json info being written to the topic:
</br>
<code>
. console_consumer.sh
</code>
</br>
To check the currently running topics:
</br>
<code>
. check.sh
</code>
</br>
To check the master node and replication factor of the twitter-topic:
</br>
<code>
. properties.sh
</code>


