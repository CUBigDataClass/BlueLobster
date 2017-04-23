package com.saurzcode.twitter;

import java.util.Map;



import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.base.BaseBasicBolt;

// twitter4j libraries for tweet parsing
import twitter4j.GeoLocation;
import twitter4j.Place;


@SuppressWarnings("serial")
public class LocationBolt implements IRichBolt {
   private OutputCollector collector;
   
   // @Override
   public void prepare(Map stormConf, TopologyContext context,
      OutputCollector collector) {
      this.collector = collector;
   }
   
  // @Override
   public void execute(Tuple input) {
      String sentence = input.getString(0);
      String[] words = sentence.split(" ");
      
      String text = input.getStringByField("text");
      
      if (text != "US" || text != "test") {
    	  
      }
      
      for(String word: words) {
         word = word.trim();
         
         if(!word.isEmpty()) {
            word = word.toLowerCase();
            collector.emit(new Values(word));
         }
         
      }

      collector.ack(input);
   }
   
  // @Override
   public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("word"));
   }

   //@Override
   public void cleanup() {}
   
   //@Override
   public Map<String, Object> getComponentConfiguration() {
      return null;
   }
   
}