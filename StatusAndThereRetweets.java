import twitter4j.Status;


public class StatusAndThereRetweets
{
	long[] didRetweets;
	Status status;
	
	public StatusAndThereRetweets(long[] didRetweets, Status status)
	{
		this.didRetweets = didRetweets;
		this.status = status;
	}
	
}
