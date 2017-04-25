bin/kafka-server-start.sh config/server-2.properties &

bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic twitter-topic --partitions 2 --replication-factor 2
