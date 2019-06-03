package javafxapplication7;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;

public class Helper 
{
	public static ResultSet fetchSeasons(java.sql.Connection conn)
	{
		try 
		{
			Statement st = conn.createStatement();	
			ResultSet res = st.executeQuery("select * from Seasons;");
			
			return res;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static ResultSet fetchMatches(java.sql.Connection conn)
	{
		try
		{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from Matches order by date desc limit 5;");
			
			return res;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		
			return null;
		}
	}
	
	public static ResultSet fetchWeeks(java.sql.Connection conn, int from_season)
	{
		try 
		{
			Statement st = conn.createStatement();	
			ResultSet res = st.executeQuery("select * from Weeks where season="+from_season+";");
			
			return res;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static ResultSet fetchTeams(java.sql.Connection conn)
	{
		try
		{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select name from Teams;");
			
			return res;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static void clearComboBox (java.sql.Connection conn, JComboBox combobox)
	{
		for (int i = 0; i < combobox.getItemCount(); i++)
		{
			combobox.removeItemAt(i);
		}
	}

	public static String findTeamById(java.sql.Connection conn, int team_id)
	{	
		try
		{
			Statement st = conn.createStatement();
			ResultSet query = st.executeQuery("select name from teams where id="+team_id+";");
			
			return query.getString(1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
}
