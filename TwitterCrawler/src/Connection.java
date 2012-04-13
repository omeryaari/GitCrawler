import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Connection 
{
	public Connection(int crawlerSize,String file) throws TwitterException, IOException, URISyntaxException, InterruptedException
	{
	/* Creating Main Twitter User */
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("MmWRKwai2IM5jRSdkmHing", "X16azXzPLOJ1VAXvpLIZneI9csSOy0FJX7DXQbKxuw");

		RequestToken requestToken = twitter.getOAuthRequestToken();
		@SuppressWarnings("unused")
		AccessToken accessToken = null;
		
		java.awt.Desktop.getDesktop().browse( new URI(requestToken.getAuthorizationURL()));
		String pin = JOptionPane.showInputDialog("Please Enter Pin Number From WebSite: ");
		
		accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		accessToken = twitter.getOAuthAccessToken();
		System.out.println("Connected Succeffuly Starting CrawlGraph");
		long[] l = twitter.getFriendsIDs(twitter.getId(), -1).getIDs();
		Crawler.crawlerSize=crawlerSize;
		Crawler.file=file;
		Crawler.toCrawl.add(l[0]);
		Crawler.twitter=twitter;
		Crawler.CrawlGraph();
	}
}
