package Utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	static Connection conn;
	
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
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static PreparedStatement prepareStatement(String SQL) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(SQL);
		return stmt;
	}
}
