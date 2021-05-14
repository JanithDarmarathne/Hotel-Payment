package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Payment {
	
	public Connection connect()
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user", 
	 "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
		}
	
	public String insertItem(String payid, String date, String amount, String desc) {
		
		 String output = "";
		 
		 try {
		
		Connection con = connect();
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		// create a prepared statement
		String query = "insert into payment (id, paymentID, date, amount, Description)"
				 + " values (?, ?, ?, ?, ?)"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, payid); 
		preparedStmt.setString(3, date); 
		preparedStmt.setDouble(4, Double.parseDouble(amount)); 
		preparedStmt.setString(5, desc);
		
		System.out.println(payid);
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newItems = readpayment();
		 output =  "{\"status\":\"success\", \"data\": \"" + 
				 newItems + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";  
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}
	
	public String readpayment()
	{ 
			 String output = ""; 
			try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Payment ID</th>" 
			 +"<th>Date</th><th>Amount</th>"
			 + "<th>Description</th>" 
			 + "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from payment"; 
			 java.sql.Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String id = Integer.toString(rs.getInt("id")); 
			 String payid = rs.getString("paymentID"); 
			 String date = rs.getString("date"); 
			 String amount = Double.toString(rs.getDouble("amount")); 
			 String desc = rs.getString("Description"); 
			 // Add a row into the html table
			 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + id + "'>"
					 + payid + "</td>";
			 output += "<td>" + date + "</td>"; 
			 output += "<td>" + amount + "</td>"; 
			 
			 output += "<td>" + desc + "</td>";
			 // buttons
			 output += "<td><input name='btnUpdate' " 
			 + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-itemid='" + id + "'></td>"
			 + "<td><form method='post' action='Item.jsp'>"
			 + "<input name='btnRemove' " 
			 + " type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + id + "'>"
			 + "<input name='hidItemIDDelete' type='hidden' " 
			 + " value='" + id + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
			 } 
			catch (Exception e) 
			 { 
			 output = "Error while reading the items."; 
			 System.err.println(e.getMessage()); 
			 } 
			return output; 
		}
	
	public String deleteItem(String id)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 // create a prepared statement
	 String query = "delete from payment where id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(id)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newItems = readpayment();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
		}

	public String updateItem(String id, String payid, String date, String amount, String desc)
	//4
	{
	String output = "";
	try {
	Connection conn = connect();
	if (conn == null) {
	return "Error while connecting to the database for updating.";
	}

	// create a prepared statement
	String query = "UPDATE payment SET paymentID=?,date=?,amount=?,Description=? WHERE id=?";
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	//binding values
	preparedStmt.setString(1, payid);
	preparedStmt.setString(2, date);
	preparedStmt.setDouble(3, Double.parseDouble(amount));
	preparedStmt.setString(4, desc);
	preparedStmt.setInt(5, Integer.parseInt(id));
	//execute the statement
	preparedStmt.execute();
	conn.close();
	String newItems = readpayment();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while Updating the item.\"}";  
	
	System.err.println(e.getMessage());
	}
	return output;
	}


	
	

}
