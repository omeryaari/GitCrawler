import java.io.IOException;
import java.util.ArrayList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

public  class Utils extends Thread
{
	public  static boolean evaluateUser(LocalUser Root,long id) throws InterruptedException, TwitterException
	{
		System.out.println("In The EvaluateUser Method");
		long[] tempFollow = Crawler.twitter.getFollowersIDs(id,-1).getIDs();
		long[] tempFreind = Crawler.twitter.getFriendsIDs(id,-1).getIDs();
			
		if(DidRetweet(Root, id)||CalcCC(Root.PopulateAllUserList(), PopulateAllUserList(tempFollow, tempFreind))>0)
		{
			System.out.println(id + " Passed");
			return true;
		}
		System.out.println(id + " Failed");
		return false;
	}


	/* The  retweet method works like this:
	 * A. each user has a list of his last 200 tweets
	 * B. in the method we Iterate each tweet and check if the user RETWEETED that status
	 * C. if I find the the user RETWEETED a cretin tweet I break And return true
	 * D. if no RETWEET was found then I return false
	 *  */
	public static boolean DidRetweet(LocalUser root,long id) throws InterruptedException
	{
		System.out.println("In DidRetweet Method");
		for (int i = 0; i < root.myTweets.size(); i++) 
		{
			for(int j=0;j<root.myTweets.get(i).didRetweets.length;j++)
			{
				if(id ==root.myTweets.get(i).didRetweets[j])
				{
					
					System.out.println(id + " DidRetweet ");
					return true;
				}	
			}
		}
		System.out.println(id + " NOT Retweet ");
		return false;
	}

	public static LocalUser BuildLocalUser(Long id) throws InterruptedException, TwitterException, IOException   
	{
		System.out.println("In BuildLocalUser Method");
		ArrayHolder holder=null;
		ResponseList<Status> status = Crawler.twitter.getUserTimeline(id, new Paging(1,50));
		ArrayList<MyTweets> myTweets = new ArrayList<MyTweets>(); 
		for (int i = 0; i < status.size(); i++) 
		{
			long[] retweetersIds = Crawler.twitter.getRetweetedByIDs(status.get(i).getId(), new Paging(1,100)).getIDs();
			myTweets.add(new MyTweets(status.get(i).getText(),retweetersIds));
		}

		holder= holderCreator(Crawler.twitter.getFollowersIDs(id, -1).getIDs(),Crawler.twitter.getFriendsIDs(id, -1).getIDs());
		return new LocalUser(id, holder.followers,holder.friends,myTweets);
	}


	private static double CalcCC(ArrayList<Long>Root,ArrayList<Long>Follow)
	{
		System.out.println(" In CalcCC Method");
		
		double count=0;
		for(int i=0;i<Root.size();i++)
		{
			for(int j=0;j<Follow.size();j++)
			{
				if(Root.get(i)==Follow.get(j))
				{
					count++;
				}
			}
		}
		System.out.println("CC Result is: " + (double)count/(Follow.size()+Root.size()));
		return (double)count/(Follow.size()+Root.size());
	}


	public static int HandleException(TwitterException ex) throws InterruptedException
	{
		System.out.println("In HandleException Method");
		if(ex.isCausedByNetworkIssue())
		{
			System.out.println("Exception Caused By Network Issue's Sleeping For One Minute");
			sleep(60000);
			return 0;
		}

		else if(ex.exceededRateLimitation())
		{
			System.out.println("Passed The 350 Request Per Hour Limit, Sleepig For One Hour");
			sleep(3600000);
			return 0;
		}
		else if(ex.resourceNotFound())
		{
			System.out.println("Resource Not Found 404 Exception, Sleeping For One Minute");
			sleep(60000);
			return 0;
		}
		else
		{
			if(ex.isErrorMessageAvailable())
			{
				System.out.println("Error Message = " + ex.getErrorMessage());
				return 1;

			}
			else
			{
				System.out.println("Error Message Is Null , Other Info: Cause Is: " +ex.getCause() + " Class Is: " + ex.getClass() + " Exception Code Is: " + ex.getExceptionCode());
				return 1;
			}
		}
	}


	public static ArrayList<Long> allListCreator(long[] followers,long[] friends)
	{
		ArrayList<Long> allList = new ArrayList<Long>();

		for (int i = 0; i < friends.length; i++) 
		{
			allList.add(friends[i]);
		}

		for (int i = 0; i < followers.length; i++)
		{
			allList.add(followers[i]);
		}
		return allList;
	}
	public static ArrayHolder holderCreator(long[] followers,long[] friends)
	{
		ArrayList<Long> tempFollowers = new ArrayList<Long>();
		ArrayList<Long> tempFriends = new ArrayList<Long>();

		for (int i = 0; i < friends.length; i++) 
		{
			tempFriends.add(friends[i]);
		}

		for (int i = 0; i < followers.length; i++)
		{
			tempFollowers.add(followers[i]);
		}
		return new ArrayHolder(tempFollowers, tempFriends);
	}

	public static ArrayList<Long> arrayToList(long[] arr)
	{
		ArrayList<Long> tempList = new ArrayList<Long>();
		for (int i = 0; i < arr.length; i++)
		{
			tempList.add(arr[i]);
		}
		return tempList;
	}
	static ArrayList<Long> PopulateAllUserList(long[]fre,long[]fol)
	{
		ArrayList<Long>negiberhood=new ArrayList<Long>();
		negiberhood.addAll(arrayToList(fre));
		negiberhood.addAll(arrayToList(fol));
		return negiberhood;
	}
}
