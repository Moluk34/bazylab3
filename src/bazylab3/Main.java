package bazylab3;

public class Main
{

	public static void main(String[] args)
	{
		Permissions user1 =  new Permissions("admin","pwsz");
		Mysql db1 = new Mysql(user1);
		db1.select("select * from filmy;");
		db1.close();
	}
}
