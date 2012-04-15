import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Crawler 
{
	public static HashMap<Long, LocalUser>crawlerList=new HashMap<Long, LocalUser>();
	public static ArrayList<Long>toCrawl=new ArrayList<Long>();
	public static Twitter twitter;
	public static int crawlerSize;
	public static String file;

	public static void CrawlGraph() throws InterruptedException, IOException 
	{
		LocalUser tempRoot = null;
		while(crawlerSize<5000)
		{
			if(!crawlerList.containsKey(toCrawl.get(0))) //checking if user in list
			{

				try
				{
					tempRoot=Utils.BuildLocalUser(toCrawl.get(0));
					System.out.println("Succeded In LocalUser, ID: " + tempRoot.id);
				} 

				catch (TwitterException e) 
				{
					System.out.println("Faild In Getting LocalUser");
					int res=Utils.HandleException(e);
					if(res==0)
					{
						continue;
					}
					else
					{
						toCrawl.remove(0);
						continue;
					}
				}
				System.out.println("Starting Evaluation Of Followers For User: " + tempRoot.id);
				for(int i=0;i<tempRoot.Followers.size();i++)
				{
					if(!crawlerList.containsKey(tempRoot.Followers.get(i)))
					{
						try 
						{
							if(!Utils.evaluateUser(tempRoot,tempRoot.Followers.get(i)))
							{ 
								System.out.println(tempRoot.Followers.get(i)+ " Got Kicked Out");
								tempRoot.Followers.remove(i);
								i--;
							}
						} 
						
						//This Is A test for github
						catch (TwitterException e) 
						{
							int val=Utils.HandleException(e);
							if(val==0)
							{
								i--;
								continue;
							}
							else
							{
								tempRoot.Followers.remove(i);
								i--;
								continue;
							}
						}
					}
//Adding Just TO tEST
					else
					{
						System.out.println("User Was Visted");
						toCrawl.remove(0);
					}
				}

				System.out.println("Finished Evaluating User " + tempRoot.id);
				if(tempRoot.Followers.size()>0)
				{
					System.out.println("Adding All The Remaning Friends Of User " + tempRoot.id);
					crawlerList.put(tempRoot.id, tempRoot);
					toCrawl.addAll(tempRoot.Followers);
				}
				toCrawl.remove(0);
			}

		}
		ArrayList<LocalUser>Users=new ArrayList<LocalUser>();
		for (Entry<Long, LocalUser> User : crawlerList.entrySet())
		{
			Users.add(User.getValue());
		}
		@SuppressWarnings("unused")
		Writer writer=new Writer(Users,file);
	}
}


