
public class Link 
{
	long from;
	long to;
	String Twitted;
	public Link(long from, long to, String twitted)
	{
		this.from = from;
		this.to = to;
		Twitted = twitted;
	}
	@Override
	public String toString()
	{
		return from+","+to+": "+Twitted;	
	}
}
