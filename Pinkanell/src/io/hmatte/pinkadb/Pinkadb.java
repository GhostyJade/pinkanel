package io.hmatte.pinkadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.ghostyjade.utils.Constants;

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

			insertNewGame(0,0);
			
		} catch (Exception e) {
			System.out.println(e);
			// handle the error
		}
		return null;
	}

	private static int insertNewGame(int i, int e) throws SQLException {

		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		
		
		 String query = " insert into games ( start_time, end_time, points_p1, points_p2)" + " values ( ?, ?, ?, ?)";
		 
		 java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
		 
		 preparedStmt.setTimestamp (1, timestamp);
		 preparedStmt.setTimestamp (2,timestamp); 
		 preparedStmt.setInt (3, i);
		 preparedStmt.setInt (4,e);
		 
		 
		preparedStmt.execute();
		 
		String queryR = "SELECT * FROM mydb.games order by game_id desc Limit 1;";
		Statement st = conn.createStatement();
		st.execute(queryR);
		ResultSet rs = st.executeQuery(queryR);
		
		rs.next();
		String foundType = rs.getString(1);
		
		int gameId = rs.getInt("game_id");
		
		
		Constants.GAME_ID = gameId;
		System.out.println(gameId);
		System.out.println(foundType);
		return gameId;
	}
	
	
	public static void insertPoint(int x, int y) throws SQLException {
		String query = " insert into dataBank (game_id, X, Y)" + " values (?, ?, ?)";
		
		java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt (1, Constants.GAME_ID);
		//preparedStmt.setInt(2, 0);
		preparedStmt.setInt(2, x);
		preparedStmt.setInt(3, y);
		
		preparedStmt.execute();
		
		System.out.println("Added a point");
	}
}
	