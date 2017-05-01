package storm;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.storm.LocalCluster;
import org.apache.storm.cassandra.bolt.CassandraWriterBolt;
import org.apache.storm.cassandra.client.CassandraConf;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Tuple;

import clojure.lang.Named;

import static org.apache.storm.cassandra.DynamicStatementBuilder.*;
import static org.apache.storm.cassandra.bolt.CassandraWriterBolt.*;
import static org.apache.storm.cassandra.client.CassandraConf.*;

import storm.LoggerBolt;


public class TwitterStreamTopology {
	
	private static final Logger LOG = Logger.getLogger(TwitterStreamTopology.class);

	public static void main(String[] args) {
		final BrokerHosts zkrHosts = new ZkHosts("localhost:2181");
		final String kafkaTopic = "twitter-topic";
		final String zkRoot = "/brokers";
		final String clientId = "storm-consumer";
		final SpoutConfig kafkaConf = new SpoutConfig(zkrHosts, kafkaTopic, zkRoot, clientId);
		kafkaConf.scheme = new SchemeAsMultiScheme(new StringScheme());

		final TopologyBuilder topologyBuilder = new TopologyBuilder();
		topologyBuilder.setSpout("kafka-spout", new KafkaSpout(kafkaConf), 1);
		
		topologyBuilder.setBolt("sentiment-analysis", new SentimentAnalysisBolt()).globalGrouping("kafka-spout");
		topologyBuilder.setBolt("cassandra-writer", new CassandraBolt()).globalGrouping("sentiment-analysis");
	    		
		final LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("kafka-topology", new HashMap<>(), topologyBuilder.createTopology());
	}
}