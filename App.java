import java.io.IOException;
import java.net.URISyntaxException;
import twitter4j.TwitterException;

public class App
{
	public static void main(String[] args) throws TwitterException, IOException, URISyntaxException, InterruptedException 
	{
		@SuppressWarnings("unused")
		Connection con=new Connection(500,"TwitterCrawler");
	}

}
