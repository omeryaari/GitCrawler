import java.util.ArrayList;


public class LocalUser 
{
	long id;
	ArrayList<MyTweets> myTweets;
	ArrayList<Long>Followers;
	ArrayList<Long>Freinds;
	
	public LocalUser(long id, ArrayList<Long> followers,ArrayList<Long>freinds, ArrayList<MyTweets> myTweets)
	{
		this.id = id;
		this.Followers = followers;
		this.Freinds = freinds;
		this.myTweets= myTweets;
	}
	ArrayList<Long> PopulateAllUserList()
	{
		ArrayList<Long>negiberhood=new ArrayList<Long>();
		negiberhood.addAll(this.Followers);
		negiberhood.addAll(this.Freinds);
		return negiberhood;
	}

}
