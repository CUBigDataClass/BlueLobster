package com.saurzcode.twitter;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.Location;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.joauth.UrlCodec;

import org.junit.Assert;

public class TwitterKafkaProducer {
	
	public Client client;

	private static final String topic = "twitter-topic";

	@SuppressWarnings("deprecation")
	public static void run(String consumerKey, String consumerSecret,
			String token, String secret) throws InterruptedException {

		Properties properties = new Properties();
		properties.put("metadata.broker.list", "localhost:9092");
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		properties.put("client.id","camus");
		ProducerConfig producerConfig = new ProducerConfig(properties);

		kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(
				producerConfig);

		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
	
		

		// add some track terms
		/*
		endpoint.trackTerms(Lists.newArrayList("twitterapi",
				"#AAPSweep"));
        
		*/
		
	    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
	    endpoint.locations(Lists.newArrayList(
	            new Location(new Location.Coordinate(-124.8, 24.4), new Location.Coordinate(-66.9, 49.4))));
	    Assert.assertEquals(endpoint.getPostParamString(), "locations=" + UrlCodec.encode("-124.8,24.4,-66.9,49.4"));
	    
		
//		endpoint.locations(Lists.newArrayList("locations" + UrlCodec.encode("")));

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token,
				secret);
		// Authentication auth = new BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();

		// Do whatever needs to be done with messages
		for (int msgRead = 0; msgRead < 100000; msgRead++) {
			KeyedMessage<String, String> message = null;
			try {
				message = new KeyedMessage<String, String>(topic, queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
			producer.send(message);
		}
		producer.close();
		client.stop();


	}
	


	public static void main(String[] args) {
		try {
			String consumerKey = "84rkPla1s8RwhMes4i7DJh0KW";
			String consumerSecret = "113JMGoTaw1loYPyH31vWIrxkH8WmSD59UZzaFfdk7q7XHSiff";
			String accessToken = "174192901-LPIE59jibzDWqI1q6ZIwjYPWyr2RThMPSTzOK7M0";
			String accessSecret = "gM0OzHZ3zaxycmEIE34m7CqtSg5SUsciI1mp7nLFfeHcP";
			TwitterKafkaProducer.run(consumerKey, consumerSecret, accessToken, accessSecret);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
