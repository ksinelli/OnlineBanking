package Utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	
	static Connection conn;
	
	//establish DB connection
	public static void getDbConnection() {
		
		try {	
			conn = DriverManager.getConnection("jdbc:postgresql://ziggy.db.elephantsql.com/wxjpiraq","wxjpiraq","D6DpgE-lOUMD-QRrIBLiSUBhmxs5FW-_");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void closeDbConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//query the database and return the results in a ResultSet object
	public static ResultSet executeQuery(String SQL) throws SQLException {
		
	Statement stmt = conn.createStatement();
	ResultSet resultSet = stmt.executeQuery(SQL);
		
	return resultSet;
	}
	
	//send an update statement to the database
	public static void executeUpdate(String SQL) throws SQLException {
		
	Statement stmt = conn.createStatement();
	stmt.executeUpdate(SQL);	
	}
}
