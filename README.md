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


New code for launch, do each of these in a separate terminal:
start in kafka directory
```
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
bin/kafka-server-start.sh config/server-1.properties
bin/kafka-server-start.sh config/server-2.properties
./topic_start.sh
cd ../../twitter-kafka
java -jar twitter.jar
cd ../twitter-storm
java -jar storm.jar
```


















