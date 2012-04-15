import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;


public class TweetsData 
{
	Status status;
	ArrayList<Long>userRetweet;
	
	public TweetsData(Status stat, ArrayList<Long> userRetweet) 
	{
		this.status = stat;
		this.userRetweet = userRetweet;
	}
	

}
