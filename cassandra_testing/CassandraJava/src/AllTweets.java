import java.util.ArrayList;

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

}

