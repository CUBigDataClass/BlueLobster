import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraController {
	   public Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	   /** Cassandra Session. */
	   
	   public Session session = cluster.connect("json_data");
	   /**
	    * Connect to Cassandra Cluster specified by provided node IP
	    * address and port number.
	    *
	    * @param node Cluster node IP address.
	    * @param port Port of cluster host.
	    */
	   public void connect()
	   {
		    cluster = Cluster.builder()                                                    // (1)
		            .addContactPoint("127.0.0.1")
		            .build();

	   }

	   
	   public Session getSession()
	   {
	      return this.session;
	   }
	   
	   public AllTweets getTweets(){
           AllTweets allTweets = new AllTweets();
		   ResultSet results = session.execute("SELECT * FROM tweets");
		   for (Row row: results){
			   Tweets tweets = new Tweets();
			   tweets.setTweets_content(row.getString("tweets_contents"));
			   tweets.setTweets_id(row.getInt("tweets_id"));
			   tweets.setTweets_name(row.getString("tweets_name"));
			   
			   allTweets.addTweet(tweets);		   

		   }
		   return allTweets;
	   }
	   
	   /** Close cluster. */
	   public void close()
	   {
	      cluster.close();
	   }
}
