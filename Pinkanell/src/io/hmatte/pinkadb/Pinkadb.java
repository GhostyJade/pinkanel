package io.hmatte.pinkadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pinkadb {

	public static Connection conn;
	
	public Pinkadb() {
		
	}

	public static Connection getConnection() throws Exception {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			String driver = "com.mysql.cj.jdbc.Driver";
			   String url = "jdbc:mysql://127.0.0.1:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=Europe/Amsterdam&useSSL=false";
			   String username = "pinkanell";
			   String password = "toor";
			   Class.forName(driver);

			conn = (Connection) DriverManager.getConnection(url, username, password);
			System.out.println("Connected");

			//insertNewGame(0,0);
			insertData(0,0);
			
		} catch (Exception e) {
			System.out.println(e);
			// handle the error
		}
		return null;
	}

	public static int insertData(int i, int e) throws SQLException {

		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		
		
		 String query = " insert into games ( start_time, end_time, points_p1, points_p2)" + " values ( ?, ?, ?, ?)";
		
		 
		 
		 java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
		 
		 preparedStmt.setTimestamp (1, timestamp); preparedStmt.setTimestamp (2,
		 timestamp); preparedStmt.setInt (3, i); preparedStmt.setInt (4,
		 e);
		 
		 
		 // execute the preparedstatement
		 
		 
		 preparedStmt.execute();
		 
/*
		 String query2 = "update games set points_p1 = ? where game_id = ?";
		 java.sql.PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
		 preparedStmt2.setInt(1, i); preparedStmt2.setInt(2, e);
		 preparedStmt2.execute();

		 

		System.out.println("Inserted data");
		
		conn.close();
		System.out.println("Connection closed");
		*/
		String queryR = "SELECT * FROM mydb.games order by game_id desc Limit 1;";
		Statement st = conn.createStatement();
		st.execute(queryR);
		ResultSet rs = st.executeQuery(queryR);
		
		rs.next();
		String foundType = rs.getString(1);
		
		int gameId = rs.getInt("game_id");
		
		
		
		System.out.println(gameId);
		System.out.println(foundType);
		return gameId;
	}
	
}
	/*
	public static int insertNewGame(int score1, int score2 ) throws SQLException {
		
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		
		String query = " insert into games ( start_time, end_time, points_p1, points_p2)" + " values ( ?, ?, ?, ?)";
		
		java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
		
		preparedStmt.setTimestamp (1, timestamp); preparedStmt.setTimestamp (2,
				 timestamp); preparedStmt.setInt (3, score1); preparedStmt.setInt (4,
				 score1);
	 
				 
		preparedStmt.execute();		 
				 
		String queryR = "SELECT game_id FROM mydb.games order by game_id desc Limit 1;";
		java.sql.PreparedStatement preparedStmtR = conn.prepareStatement(queryR);
		int rs = preparedStmtR.executeUpdate(query);
		//int gameId = rs.getInt("game_id");
		
		System.out.println(rs);
		
		return rs;
		
	}
	*/
