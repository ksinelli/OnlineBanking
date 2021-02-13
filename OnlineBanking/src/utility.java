import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class utility {

	public final static Scanner scan = new Scanner(System.in);
	
	//establish DB connection
	public static Connection getDbConnection() {
		Connection conn = null;
		
		try {	
			conn = DriverManager.getConnection("jdbc:postgresql://ziggy.db.elephantsql.com/wxjpiraq","wxjpiraq","D6DpgE-lOUMD-QRrIBLiSUBhmxs5FW-_");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
