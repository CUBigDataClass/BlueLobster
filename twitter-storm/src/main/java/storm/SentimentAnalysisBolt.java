package storm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

 

@SuppressWarnings("serial")
public class SentimentAnalysisBolt implements IRichBolt {
		
	   private OutputCollector collector;
	   private static final Logger LOG = Logger.getLogger(SentimentAnalysisBolt.class);
	   public  Map<String,Integer> sentimap=new HashMap<String,Integer>();
	   public String[] states = new String[] {"Alabama", "Alaska", "Arizona ", "Arkansas ", "California", "Colorado",
               "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
               "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
               "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
               "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
               "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
               "Washington", "West Virginia", "Wisconsin", "Wyoming"};
	   
	   public String[] firstWords = new String[] {"north","south","new","west","rhode"};
	   public String[] secondWords = new String[] {"york","dakota","jersey","carolina","hampshire","virginia","mexico","island"};
	   
	   @Override 
	   public void prepare(Map stormConf, TopologyContext context,
	      OutputCollector collector) {
	      this.collector = collector;
	  
		   try {
				final URL url = Resources.getResource("AFINN-111.csv");
				final String text = Resources.toString(url, Charsets.UTF_8);
				final Iterable<String> lineSplit = Splitter.on("\n").trimResults().omitEmptyStrings().split(text);
				List<String> commaSplit;
				for (final String str: lineSplit) {
					commaSplit = Lists.newArrayList(Splitter.on(",").trimResults().omitEmptyStrings().split(str));
					sentimap.put(commaSplit.get(0), Integer.parseInt(commaSplit.get(1)));
				}
			} catch (final IOException ioException) {
				System.exit(1);
			}
	      
	   }
	    
	   @Override
	   public void execute(Tuple input) {

		   String tuple = input.getString(0);
		   
		   int sentiment = 0;
		   String state = "N/A";		
		   try {
			   
			   JSONParser parser = new JSONParser();
			   Object obj = parser.parse(tuple);
			   JSONObject jsonObject = (JSONObject) obj;
			   String tweetText = (String) jsonObject.get("text");
			   String cleanTweet = cleanTweet(tweetText);
			   state = getState(cleanTweet);
			   
			   // only compute sentiment of tweets that we can find a state for 
			   if (!state.equals("N/A")) {
				   sentiment = getSentiment(cleanTweet);
				   LOG.info(state);
				   LOG.info(sentiment);
				   collector.emit(new Values(state,sentiment));
			   }
			   
		   } catch (ParseException e) {
			   
		   } catch (NumberFormatException e) {
	
		   } catch (IOException e) {
			   
		   } catch (ApiException e) {

		   } catch (InterruptedException e) {

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
	   
	   public String cleanTweet(String tweetText) {
		   
		   if (tweetText == null) {
			   return "";
		   }
		   
		   
		   String withoutHashtags = tweetText.replace("#", "");

		   
		   Pattern urlPattern = Pattern.compile("((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)");
		   
		   Matcher matcher = urlPattern.matcher(withoutHashtags);
		   
		   String withoutURL = withoutHashtags;
		   //Check if matcher finds url
		   if(matcher.find()) {
		       withoutURL = matcher.replaceAll(""); 
		   } 
		   
		   String trim = withoutURL.trim();
		   String lower = trim.toLowerCase();
		   String cleanTweet = lower.replaceAll("[^a-zA-Z0-9\\s]", "");
		   
		   return cleanTweet;
	   }
	   

	   public int getSentiment(String tweet) throws NumberFormatException, IOException {
		  
	
		   int sentiment = 0; // set to 0 since tweets are neutral if they can't be scored
		   int sentiWords = 0;
		   String[] words = tweet.split("\\s+");
		   
		   
		   for (int i = 0; i < words.length; i++) {
			   String word = words[i].toString();
			   
			   if (sentimap.containsKey(word.toString())) {
				   sentiWords++;
				   sentiment = sentiment + sentimap.get(word.toString());
			   }
		   }
		   
		   if (sentiWords == 0) {
			   return sentiment;
		   } else {
			   sentiment = sentiment/sentiWords;
		   }
		   
		   return sentiment;
		   
	   }
	   
	   public String getState(String tweet) throws ApiException, InterruptedException, IOException {
		   
		   String state = "N/A";
		  
		   String[] words = tweet.split("\\s+");
		   
		   // loop through tweet text words
		   for (int i = 0; i < words.length; i++) {
			   String word = words[i].toString();
			  
				
			   // loop through states
			   for (int j = 0; j < states.length; j++) {
				   
				   // case handling for two word states
				   if (i != words.length - 1) { 
					   
					   String secondWord = words[i+1];   
					   switch (word) {
					   	
						   case "new":
							   if (secondWord.equals("york")) {
								   state = "new york";
							   }
							
							   if (secondWord.equals("jersey")) {
								   state = "new jersey";
							   }
							   
							   if (secondWord.equals("hampshire")) {
								   state = "new hampshire";
							   }
							   
							   if (secondWord.equals("mexico")) {
								   state = "new mexico";
							   }
							
						   case "north":
							   
							   if (secondWord.equals("carolina")) {
								   state = "north carolina";
							   }
							   
							   if (secondWord.equals("dakota")) {
								   state = "north dakota";
							   }
						   
						   case "south":
							   
							   if (secondWord.equals("dakota")) {
								   state = "south dakota";
							   }
							   
							   if (secondWord.equals("carolina")) {
								   state = "south carolina";
							   }
							   
						   case "west":
							   
							   if (secondWord.equals("virginia")) {
								   state = "west virginia";
							   }
							
						   case "rhode":
							   
							   if (secondWord.equals("island")) {
								   state = "rhode island";
							   }
						  
					   }
				   
				   }
				   // default case for single words states e.g. Colorado
				   if (states[j].toLowerCase().equals(words[i])) {
					   state = word;
				   }
			   }
		   }
		   
		   return state;
		   
	   }
	   

}
