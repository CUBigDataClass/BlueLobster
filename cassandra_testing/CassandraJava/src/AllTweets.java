

import java.util.ArrayList;
import java.util.Iterator;

public class AllTweets {
	ArrayList<Tweets> allTweets = new ArrayList<Tweets>();
	
	public ArrayList<Tweets> getAllTweets() {
		return allTweets;
	}

	public void setAllTweets(ArrayList<Tweets> allTweets) {
		this.allTweets = allTweets;
	}

	public void addTweet(Tweets tweet){
		allTweets.add(tweet);
	}
	
	public Tweets findTweetID(int search){
		ArrayList<Tweets> list = allTweets;
		for(Iterator<Tweets> iter = list.listIterator(); iter.hasNext(); ) {
			Tweets a = iter.next();
			if(a.getTweets_id() == search ){
				return a;
			}
		}
		return null;	
	}
}

