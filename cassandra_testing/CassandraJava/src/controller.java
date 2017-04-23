import java.util.ArrayList;
import java.util.Iterator;

public class controller {
    public static void main(String[] args) {
    	
	    System.out.println("here");
		final CassandraController client = new CassandraController();
//		client.connect();

        AllTweets twitter = new AllTweets();
        twitter = client.getTweets();
        
		ArrayList<Tweets> list = twitter.getAllTweets();
        
		for(Iterator<Tweets> iter = list.listIterator(); iter.hasNext(); ) {
			Tweets a = iter.next();
			// manipulate tweet data
	        System.out.println(a.getState_name());
	        System.out.println(a.getState_sentiment());

		}



        client.close();
    	
        System.out.println("Hello World!");
    }

}
