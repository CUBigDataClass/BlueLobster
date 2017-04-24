import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.task.TopologyContext;

import com.opencsv.*;
 

@SuppressWarnings("serial")
public class SentimentAnalysisBolt implements IRichBolt {
	
	   private OutputCollector collector;
	   
	   @Override
	   public void prepare(Map stormConf, TopologyContext context,
	      OutputCollector collector) {
	      this.collector = collector;
	   }
	    
	   @Override
	   public void execute(Tuple input) {
		  
		  String tweetText = input.getStringByField("text");
		  String cleanTweet = cleanTweet(tweetText);
		  Double sentiment = 0.0;
		try {
			sentiment = getSentiment(cleanTweet);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	      collector.emit(new Values(sentiment));
	   }
	   
	   @Override
	   public void declareOutputFields(OutputFieldsDeclarer declarer) {
	      declarer.declare(new Fields("sentiment"));
	   }

	   @Override
	   public void cleanup() {}
	   
	   @Override
	   public Map<String, Object> getComponentConfiguration() {
	      return null;
	   }
	   
	   public String cleanTweet(String tweetText) {
		   String cleanTweet = null;
		   String withoutHashtags = tweetText.replace("#", "");
		   
		   Pattern urlPattern = Pattern.compile("((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*");
		   
		   Matcher matcher = urlPattern.matcher(withoutHashtags);
		   
		   //Check if matcher finds url
		   if(matcher.find()) {
		       cleanTweet = matcher.replaceAll(""); 
		   } else {
		       cleanTweet = withoutHashtags;
		   }
		   
		   return cleanTweet;
	   }
	   
	   public double getSentiment(String tweet) throws NumberFormatException, IOException {
		  
		   
		   double sentiment = 0; // set to 0 since tweets are neutral if they can't be scored
		   CSVReader reader = new CSVReader(new FileReader("AFINN-111.csv"));
		   Map<String,Double> sentimap=new HashMap<String,Double>();  
		   String [] nextLine;
		   while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		        //System.out.println(nextLine[0] + nextLine[1] + "etc...");
			   String word = nextLine[0];
			   double score = Double.parseDouble(nextLine[1]);
			   sentimap.put(word,score);
		   }
		   
		   String[] words = tweet.split("\\s");
		   
		   for (String w:words) {
			   if (sentimap.containsKey(w)) {
				   sentiment = sentimap.get(w);
			   }
		   }
		   
		   
		   return sentiment;
	   }
	   

}
