import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Writer 
{
	FileWriter out;

	ObjectOutputStream oos;
	public Writer(ArrayList<LocalUser>Users,String file) throws IOException 
	{
		System.out.println();
		oos=new  ObjectOutputStream(new	FileOutputStream(file+".data"));
		oos.writeObject(Users);
		oos.flush();
		oos.close();
		System.out.println("In Writer Class, Statrting To Write To File");
		out=new FileWriter(file+".txt",true);
		ArrayList<Link>TempConnection=new ArrayList<Link>();
		for(int i=0;i<Users.size();i++)
		{
			System.out.println("User #: "+i+" From "+Users.size()+" Amount Of Users");
			TempConnection=GetUserConnections(Users.get(i).id,Users.get(i).Followers,Users.get(i).Freinds,Users.get(i).myTweets);
			WriteToFile(TempConnection);
		}
		out.flush();
		out.close();
		
	}

	private void WriteToFile(ArrayList<Link> tempConnection) throws IOException 
	{
		System.out.println("In WriteToFile Method");
		for(int i=0;i<tempConnection.size();i++)
		{
			out.write(tempConnection.get(i).toString()+"\n");
		}
	}

	private ArrayList<Link> GetUserConnections(long id,ArrayList<Long> followers,ArrayList<Long> freinds,ArrayList<MyTweets>myTweets)
	{
		System.out.println("In GetUserConnections Method");
		ArrayList<Link>Temp=new ArrayList<Link>();
		String tweet="";
		for (int i = 0; i < followers.size(); i++) 
		{
			for(int k=0;k<myTweets.size();k++)
			{
				for(int k1=0;k1<myTweets.get(k).didRetweets.length;k1++)
				{
					if(id==myTweets.get(k).didRetweets[k1])
					{
						tweet=myTweets.get(k).statusText;
						break;
					}
				}
			}
			Link l=new Link(id, followers.get(i),tweet);
			Temp.add(l);
		}
		return Temp;
	}
	

	

}
