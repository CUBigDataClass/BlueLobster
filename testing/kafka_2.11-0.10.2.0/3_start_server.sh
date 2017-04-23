bin/kafka-server-start.sh config/server-2.properties &


bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic TestTopicXYZ --partitions 2 --replication-factor 2



