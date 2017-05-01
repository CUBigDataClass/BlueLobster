import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraController {
	   public Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	   /** Cassandra Session. */
	   
	   public Session session = cluster.connect("storm");
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
		   ResultSet results = session.execute("SELECT * FROM storm_data");
		   for (Row row: results){
			   Tweets tweets = new Tweets();
			   tweets.setId(row.getInt("id"));
			   tweets.setState_name(row.getString("state_name"));
			   tweets.setState_sentiment(row.getInt("state_sentiment"));
			   row.getUUID(arg0)
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
