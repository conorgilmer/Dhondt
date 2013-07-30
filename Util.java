public class Util
{
	
	
	public static String getVersion()
	{
		return new String("$Revision: 2 $ $Date: 15.05.00 16:32 $");
	}
	static boolean debug = false;
	public static void debugOn()
	{
		debug = true;
	}
	
	public static void debugOff()
	{
		debug = false;
	}
	
	public static void debug(String message)
	{
		if(debug)
		{
			System.out.println(message);
		}
	}
}
