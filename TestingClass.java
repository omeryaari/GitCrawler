import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusStream;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class TestingClass 
{
	public static void main(String[] args) throws IOException, URISyntaxException, TwitterException
	{
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("MmWRKwai2IM5jRSdkmHing", "X16azXzPLOJ1VAXvpLIZneI9csSOy0FJX7DXQbKxuw");
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		java.awt.Desktop.getDesktop().browse( new URI(requestToken.getAuthorizationURL()));
		String pin = JOptionPane.showInputDialog("Please Enter Pin Number From WebSite: ");
		accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		accessToken = twitter.getOAuthAccessToken();
		long[] myFriends = twitter.getFriendsIDs(-1).getIDs();
		long [] followers = twitter.getFollowersIDs(-1).getIDs();
		User u = twitter.showUser(followers[0]);
		System.out.println();
		ResponseList<Status > s = twitter.getUserTimeline(myFriends[0]);
		
		
		for (int i = 0; i < s.size(); i++) {
			long[] ids = twitter.getRetweetedByIDs(s.get(i).getId(), new Paging(1,100)).getIDs();
			for (int j = 0; j < ids.length; j++) 
			{
				if (u.getId() == ids[j])
				{
					System.out.println("Found Omer");
				}
			}
		}
		System.out.println();
		
	}
}

