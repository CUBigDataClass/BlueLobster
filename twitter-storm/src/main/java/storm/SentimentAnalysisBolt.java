package storm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
		   String country = "N/A";		
		   try {

			   
			   JSONParser parser = new JSONParser();
			   Object obj = parser.parse(tuple);
			   JSONObject jsonObject = (JSONObject) obj;
			   JSONObject coordinatesObj = (JSONObject) jsonObject.get("coordinates");
			   if (coordinatesObj != null) { // only want tweets with locations for sentiment analysis
				   JSONArray coordinatesArray = (JSONArray) coordinatesObj.get("coordinates");
				   
				   // get state, country
				   Double lat = (Double) coordinatesArray.get(0);
				   Double lng = (Double) coordinatesArray.get(1);

				   String[] place = getState(lat,lng);
				   state = place[0].toString();
				   country = place[1].toString();
				   //LOG.info(state);
				   //LOG.info(country.getClass());
				   String us = "US";
				   if (country.equals(us)) {
					   String tweetText = (String) jsonObject.get("text");
					   String cleanTweet = cleanTweet(tweetText);
					   sentiment = getSentiment(cleanTweet);
					   
				   }
				   collector.emit(new Values(state,sentiment));
				   Thread.sleep(2500);
			   }
			   
		   } catch (IOException e) {
			   
		   } catch (NumberFormatException e) {
			   
		   } catch (ParseException e) {
			   
		   } catch (ApiException e) {
	
		   } catch (InterruptedException e) {

		   }
		  
		  

	      //collector.emit(new Values(sentiment));
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
		  
		   String[] words = tweet.split("\\s+");
		   
		   for (int i = 0; i < words.length; i++) {
			   String word = words[i].toString();
			   if (sentimap.containsKey(word.toString())) {
				   sentiment = sentiment + sentimap.get(word.toString());
			   }
		   }
		   
		   return sentiment;
	   }
	   
	   public String[] getState(Double lat, Double lng) throws ApiException, InterruptedException, IOException {
		   String GOOGLE_MAPS_API_KEY = "AIzaSyC75Z4Yh-jLF10CHeJz7uqAPi3-Qtmow10";
		   String state = "N/A";
		   String country = "N/A";

		   LatLng latlng = new LatLng(lng,lat);
		   
		   
		   GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_MAPS_API_KEY);
		   GeocodingResult[] results =  GeocodingApi.reverseGeocode(context, latlng).await();	   
		   
		   if (results.length > 0) {
			   for ( int i=0; i<results[0].addressComponents.length; i++) {
				   
				    for ( int j=0; j<results[0].addressComponents[i].types.length; j++) {
				       
				       if ( results[0].addressComponents[i].types[j].toString() == "administrative_area_level_1" ) {
				    	   state = results[0].addressComponents[i].shortName;
				       }
				       
				       if ( results[0].addressComponents[i].types[j].toString() == "country" ) {
				    		   country = results[0].addressComponents[i].shortName;
				       }
				    
				    }
			   }	   
		   }
		   
		   
		   String[] place = new String[] {state,country};
		   return place;
		   
	   }
	   

}
