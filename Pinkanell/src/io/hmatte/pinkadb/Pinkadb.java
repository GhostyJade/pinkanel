package io.hmatte.pinkadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Pinkadb {

	public static Connection conn;

	public static void main(String[] args) throws Exception {

		getConnection();

	}

	public static Connection getConnection() throws Exception {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://127.0.0.1:3306/pinkadb?useLegacyDatetimeCode=false&serverTimezone=Europe/Amsterdam&useSSL=false";
			String username = "root";
			String password = "toor";
			Class.forName(driver);

			conn = (Connection) DriverManager.getConnection(url, username, password);
			System.out.println("Connected");

			insertData();

		} catch (Exception e) {
			System.out.println(e);
			// handle the error
		}
		return null;
	}

	public static void insertData() throws SQLException {

		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

		 String query1 = " insert into databank ( game_id, x, y, time)" + " values ( ?, ?, ?, ?)";
	
		 java.sql.PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
		 
		 preparedStmt1.setInt (1, 1); preparedStmt1.setInt (2, 5);
		 preparedStmt1.setInt (3, 7); preparedStmt1.setTimestamp (4, timestamp);
		 
		 String query = " insert into games ( start_time, end_time, points_p1, points_p2)" + " values ( ?, ?, ?, ?)";
		
		 java.util.Random randomGenerator = new java.util.Random(); int randomInt =
		 randomGenerator.nextInt(50) + 1;
		 
		 java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
		 
		 preparedStmt.setTimestamp (1, timestamp); preparedStmt.setTimestamp (2,
		 timestamp); preparedStmt.setInt (3, randomInt); preparedStmt.setInt (4,
		 randomInt);
		 
		 
		 // execute the preparedstatement
		 
		 preparedStmt1.execute(); preparedStmt.execute();
		 

		 String query2 = "update games set points_p1 = ? where game_id = ?";
		 java.sql.PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
		 preparedStmt2.setInt(1, 6); preparedStmt2.setInt(2, 8);
		 preparedStmt2.execute();

		 

		System.out.println("Inserted data");
		conn.close();
		System.out.println("Connection closed");
	}

}