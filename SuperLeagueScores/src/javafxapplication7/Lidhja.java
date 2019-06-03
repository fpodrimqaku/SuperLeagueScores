package javafxapplication7;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Lidhja 
{
	public static java.sql.Connection getConnection()
	{
            
            
            
            System.out.println("------------------------------------------------------------");
            
            
		try
		{
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/League", "root", "root");
			
			return conn;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
}
	//LvOa000!