
import java.util.UUID;

import org.apache.storm.tuple.Fields; 
import org.apache.storm.tuple.Values;



//import storm configuration packages
import org.apache.storm.Config;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.KafkaConfig;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.cassandra.bolt.CassandraWriterBolt;
import static org.apache.storm.cassandra.DynamicStatementBuilder.*;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;



public class TwitterStreamTopology { 
	public static void main(String[] args) throws Exception {
		Config config = new Config();
		config.setDebug(true);
		
		// configure kafka properties
		String topicName = "twitter-topic"; 
		String zkConnString = "localhost:9092";
		
		// set up generic kafka spout
		BrokerHosts hosts = new ZkHosts(zkConnString);
		SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, "/" + topicName, UUID.randomUUID().toString());
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
		
		
		// set topology
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("kafkaspout", kafkaSpout);
		builder.setBolt("geo-filter", new LocationBolt()).shuffleGrouping("kafka-spout");
		builder.setBolt("sentiment-analysis", new SentimentAnalysisBolt()).shuffleGrouping("geo-filter");
		
		// write to cassandra with its own bolt
	    CassandraWriterBolt cBolt = new CassandraWriterBolt(
	            async(
	                simpleQuery("INSERT INTO album (title,year,performer,genre,tracks) VALUES (?, ?, ?, ?, ?);")
	                    .with( all() )
	                )
	        );
	    builder.setBolt("cassandra", cBolt);
		
	    
	    // set up local cluster
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("TwitterStreamTopology", config, builder.createTopology());
		
		
	}
	

}
