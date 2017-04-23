package com.saurzcode.twitter;

import java.io.IOException;
import java.util.Map;



import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.task.TopologyContext;

// twitter4j libraries for tweet parsing
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;

// Google Maps Java API for reverse geo-encoding
import com.google.maps.*;
import com.google.gson.FieldNamingPolicy;
import com.google.maps.errors.ApiException;
import com.google.maps.internal.ApiConfig;
import com.google.maps.internal.ApiResponse;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.GeolocationPayload;
import com.google.maps.model.GeolocationResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;


@SuppressWarnings("serial")
public class LocationBolt implements IRichBolt {
   private OutputCollector collector;
   

   @Override
   public void prepare(Map stormConf, TopologyContext context,
      OutputCollector collector) {
      this.collector = collector;
   }
   
   @Override
   public void execute(Tuple input) {
	  // TODO get state
	   Status status = (Status) input.getValueByField("tweet");
	   String state = null;
	   try {
		state = getState(status);
	} catch (ApiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  // TODO get tweet text
	   String tweet = input.getStringByField("text");
	   
      collector.emit(new Values(state,tweet));
   }
   
   @Override
   public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("state","tweet"));
   }

   @Override
   public void cleanup() {}
   
   @Override
   public Map<String, Object> getComponentConfiguration() {
      return null;
   }
   
   // Helper methods
   
   public String getState(Status tweet) throws ApiException, InterruptedException, IOException {
	   String GOOGLE_MAPS_API_KEY = "AIzaSyD7G2tUcjq2mKMlMSjF5qmDOn7BRjeXUfc";
	   String state = null;
	   
	   GeoLocation geoLoc = tweet.getGeoLocation();
	   
	   Double lat = geoLoc.getLatitude();
	   Double lng = geoLoc.getLongitude();
	   LatLng latlng = new LatLng(lat,lng);
	   
	   
	   GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_MAPS_API_KEY);
	   GeocodingResult[] results =  GeocodingApi.reverseGeocode(context, latlng).await();
	   
	   
	   
	   for ( int i=0; i<results[0].addressComponents.length; i++) {
		   
		    for ( int j=0; j<results[0].addressComponents[i].types.length; j++) {
		    	
		       if ( results[0].addressComponents[i].types[j].toString() == "ADMINISTRATIVE_AREA_LEVEL_1" )
		          state = results[0].addressComponents[i].shortName;
		    }
		}	   
	   
	   return state;
	   
   }
   
}