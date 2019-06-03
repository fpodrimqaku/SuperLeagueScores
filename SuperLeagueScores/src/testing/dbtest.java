/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;



import java.sql.Array;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

class databaza {
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	{
		
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} 
	
		
	}
	
	String user,password;
databaza(String user,String password) {
		
	this.user=user;this.password=password;	
	
	try {
		
		
		
		
		myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/basketball", user , password);
					
					// krijo nje statement
					myStmt = (Statement) myConn.createStatement();
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
		
}	

		public void execquery(String query){
			try {
				
				
			if((myConn==null)||myConn.isClosed())	
			
myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/basketball", user , password);
			
			// krijo nje statement
			if(!(myStmt==null)&&!myStmt.isClosed())	
			myStmt = (Statement) myConn.createStatement();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			
			try{
			myRs = (ResultSet) myStmt.executeQuery(query);
			}catch(SQLException d){
				System.out.println(d);
			}
			
		}
		ArrayList<String> al=new ArrayList(10);
		
		
		public Object[] getRow(int o){
			Object [] rresht=new Object[o];
			 
			
				try{if(myRs.next())
					for(int i=0;i<o;i++)
					rresht[i]=myRs.getString(i+1);
				else return null;
				}catch(Exception m){System.out.println(m);}
				return rresht;
			
		}

		public void execute(String string) {
			// TODO Auto-generated method stub
			
		
			try{
		
				
				myStmt.execute(string);
			}catch(Exception d){System.out.println(d);}
			
		}
		
		/*public ResultSet rezulati(){
			
			return myRs;
		}
		public void close(){
			
			try{
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
			}catch(Exception f){System.out.println(f);}
		}*/
	
public static void main(String args[]){
databaza d=new databaza("root","root");
    
    
}
}





		
		
	


