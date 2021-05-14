package com;

import java.sql.*; 
public class Order 
{ //Connect to DB

private Connection connect() 
 { 
 Connection con = null; 
 try
 { 
 Class.forName("com.mysql.jdbc.Driver"); 
 
 //DB access denied 
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/orders", "root", "mysql123"); 
 } 
    catch (Exception e) 
    {e.printStackTrace();} 
 
    return con; 
 } 


public String insertOrder(String date,String custName, String address , String phone, String email, String totalPayment) 
{ 
  String output = ""; 

try
{ 
	 
   Connection con = connect(); 
   
   if (con == null) 
   {return "Error while connecting to the database for inserting."; } 

   // create a prepared statement
   String query = " INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?)"; 

   PreparedStatement preparedStmt = con.prepareStatement(query); 

   // binding values
   preparedStmt.setInt(1, 0); 
   preparedStmt.setString(2, date);
   preparedStmt.setString(3, custName); 
   preparedStmt.setString(4, address); 
   preparedStmt.setString(5, phone);
   preparedStmt.setString(6, email);
   preparedStmt.setString(7, totalPayment);
   
   // execute the statement
   preparedStmt.execute(); 
   con.close(); 
    String newOrders = readOrders();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newOrders + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while inserting the order.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}

public String readOrders() 
{ 
   String output = ""; 
   
   try
  { 
     Connection con = connect(); 
 
     if (con == null) 
     {return "Error while connecting to the database for reading."; } 
 
     // Prepare the HTML table to be displayed
     output = "<table border='1'>"
     		+ "<tr>"
     		+ "<th>Date</th>"
     		+ "<th>Customer Name</th>"
     		+ "<th>Address</th>" +
              "<th>Phone</th>" + 
              "<th>Email</th>" +
              "<th>Total Payment</th>" +
              "<th>Update</th><th>Remove</th></tr>"; 
 
   String query = "select * from orders"; 
   Statement stmt = con.createStatement(); 
   ResultSet rs = stmt.executeQuery(query); 
 
   // iterate through the rows in the result set
  while (rs.next()) 
 { 
      String id = Integer.toString(rs.getInt("id")); 
      String date = rs.getString("date"); 
      String custName = rs.getString("custName"); 
      String address = rs.getString("address");
      String phone = rs.getString("phone");
      String email = rs.getString("email");
      String totalPayment = rs.getString("totalPayment");
       
   // Add a row into the HTML table
		 output += "<tr><td><input id='hidIdUpdate' name='hidIdUpdate' type='hidden' value='" + id + "'>"
				 + date + "</td>";
		 output += "<td>" + custName + "</td>"; 
		 output += "<td>" + address + "</td>"; 
		 output += "<td>" + phone + "</td>";
		 output += "<td>" + email + "</td>";
		output += "<td>" + totalPayment + "</td>";
		 		
   // buttons
		 output += "<td><input name='btnUpdate' " 
		 + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-oid='" + id + "'></td>"
		 + "<td><form method='post' action='orders.jsp'>"
		 + "<input name='btnRemove' " 
		 + " type='button' value='Remove' class='btnRemove btn btn-danger' data-oid='" + id + "'>"
		 + "<input name='hidIdDelete' type='hidden' " 
		 + " value='" + id + "'>" + "</form></td></tr>"; 
		 } 
		 con.close(); 
		 // Complete the HTML table
		 output += "</table>"; 
		 } 
		catch (Exception e) 
		 { 
		 output = "Error while reading the orders."; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
	}

public String updateOrder(String ID, String date, String custName, String address, String phone, String email, String totalPayment)
 { 
   
	String output = ""; 
 
	try
   { 
      Connection con = connect(); 
 
      if (con == null) 
      {return "Error while connecting to the database for updating."; } 
 
     // create a prepared statement
     String query = "UPDATE orders SET date=?,custName=?,address=?,phone=?,email=?,totalPayment=? WHERE id=?"; 
     PreparedStatement preparedStmt = con.prepareStatement(query); 
 
 
     // binding values
    preparedStmt.setString(1, date); 
    preparedStmt.setString(2, custName); 
    preparedStmt.setString(3, address);
    preparedStmt.setString(4, phone);
    preparedStmt.setString(5, email);
    preparedStmt.setString(6, totalPayment);
    preparedStmt.setInt(7, Integer.parseInt(ID)); 
 
    // execute the statement
    preparedStmt.execute(); 
    con.close();
	String newOrders = readOrders();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newOrders + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while Updating the order.\"}";  
	
	System.err.println(e.getMessage());
	}
	return output;
	}


public String deleteOrder(String id) 
 { 
 String output = ""; 
 
 try
 { 
    Connection con = connect(); 
    if (con == null) 
    {return "Error while connecting to the database for deleting."; } 
 
    // create a prepared statement
    String query = "delete from orders where id=?"; 
    PreparedStatement preparedStmt = con.prepareStatement(query); 
 
    // binding values
    preparedStmt.setInt(1, Integer.parseInt(id)); 
 
 // execute the statement
 	 preparedStmt.execute(); 
 	 con.close(); 
 	 String newOrders = readOrders();
 	 output =  "{\"status\":\"success\", \"data\": \"" + 
 			 newOrders + "\"}"; 
 	 } 

 	catch (Exception e) 
 	 { 
 		output = "{\"status\":\"error\", \"data\": \"Error while Deleting the order.\"}";  
 	 System.err.println(e.getMessage()); 
 	 } 
 	return output; 
 		}








} 