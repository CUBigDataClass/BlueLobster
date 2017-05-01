package storm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.task.TopologyContext;
import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.opencsv.*;

import clojure.uuid__init;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


@SuppressWarnings("serial")
public class CassandraBolt implements IRichBolt {
		
	   private OutputCollector collector;
	   private static final Logger LOG = Logger.getLogger(CassandraBolt.class);

	   @Override 
	   public void prepare(Map stormConf, TopologyContext context,
	      OutputCollector collector) {
	      this.collector = collector;
	  
	   }
	    
	   @Override
	   public void execute(Tuple input) {
		   
		   String state = input.getString(0);
		   Integer sentiment = input.getInteger(1);

		   Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		   Session session = cluster.connect("storm");
		   session.execute("INSERT INTO storm_data (id,state_name, state_sentiment) VALUES (:s, :s, :d) USING TTL 30",UUID.randomUUID(),state,sentiment);
		   try {
			Thread.sleep(1000);
		   } catch (InterruptedException e) {
			   e.printStackTrace();
		   }
		   
	   }
	   
	   @Override
	   public void declareOutputFields(OutputFieldsDeclarer declarer) {
	      declarer.declare(new Fields("state","sentiment"));
	   }

	   @Override
	   public void cleanup() {}
	   
	   @Override
	   public Map<String, Object> getComponentConfiguration() {
	      return null;
	   }
	   
	   
	   public void writeToCassandra(String state, int sentiment) {
		   Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		   Session session = cluster.connect("storm");
		   
		   session.execute("INSERT INTO storm_data (id,state_name, state_sentiment) VALUES (:s, :s, :d) USING TTL 30",UUID.randomUUID(),state,sentiment);
		   
	   }
}
	   
