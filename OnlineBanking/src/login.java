import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login {
	
	static Connection conn;

	public static void main(String[] args) {
		System.out.println("Hello, and welcome to KAS Financial!\n");
		
		conn = utility.getDbConnection();
		
		startup();

		}
	
	public static void startup() {
		System.out.println("Type \"Sign in\" to access your account.");
		System.out.println("Type \"Create account\" to create a new account.");
		System.out.println("Type \"Reset password\" to change your password.\n");
		System.out.println("Pro tip: capitalization doesn't matter when typing your choice :)");
		
		String choice = utility.scan.nextLine().toLowerCase();
		
		if (choice.equals("sign in")) {
			signIn();
		}
		
		else if (choice.equals("create account")) {
			createAccount(conn);
		}
		
		else if (choice.equals("reset password")) {
			resetPassword(conn);
		}
		
		else {
			System.out.println("I'm sorry, I didn't understand that.  Please try again.\n");
			startup();
		}	
	}
	
	public static void signIn() {
		String username = getUsername(conn);
		getPassword(conn, username);
		dashboard.dashboardMenu();
	}
	
	public static void resetPassword(Connection conn) {
		String username = getUsername(conn);
		getPassword(conn, username);
		updatePassword(conn, username);		
	}
	
	//retrieve entered username from database and if it does not exist, redirect to home page.
	public static String getUsername(Connection conn) {
			
		System.out.println("Please enter your username.  Type \"Home\" to return to the main screen.");
			
		String username = utility.scan.nextLine().toLowerCase();
			
		if (username.equals("home")) {
				login.startup();
		}
			
		try {
				
			Statement stmt = conn.createStatement();
				
			ResultSet resultSet = stmt.executeQuery("select username from logins where username = '"+username+"'");
				
			if (resultSet.next() == false) {
				System.out.println("That username does not exist in our system.\n");
				login.signIn();
			}
			
		}
				
		catch (SQLException e) {
			e.printStackTrace();
		}
			
		return username;
	}
		
	//retrieve database password and compare to entered password.  If they do not match, return to home page.
	public static void getPassword(Connection conn, String username) {
			
		System.out.println("Please enter your password.");
			
		String password = utility.scan.nextLine();
		
		String dbPassword = null;
			
		try {
			Statement stmt = conn.createStatement();
				
			ResultSet resultSet = stmt.executeQuery("select password from logins where username = '"+username+"'");
			
			resultSet.next();
				
			dbPassword = resultSet.getString("password");
				
			if (!password.equals(dbPassword)) {
				System.out.println("The password you entered does not match our records.  Please try again.\n");
				login.signIn();
			}
		}
			
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	//Update SQL database with new user-supplied password
	public static void updatePassword(Connection conn, String username) {
			
		System.out.println("Please enter a new password.");
			
		String newPassword = utility.scan.nextLine().toLowerCase();
			
		try {
			Statement stmt = conn.createStatement();
				
			stmt.executeUpdate("Update logins set password = '"+newPassword+"' where username = '"+username+"'");
				
			System.out.println("Your password has been updated.  Please login again with your new credentials.\n");
				
			login.signIn();
				
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}
	
	//update SQL database with new username and password
	public static void createAccount(Connection conn) {
		System.out.println("Please enter a username.  Type \"Home\" to return to the main screen.");
			
		String username = utility.scan.nextLine().toLowerCase();
			
		if (username.equals("home")) {
			login.startup();
		}
			
		try {
				
			Statement stmt = conn.createStatement();
				
			ResultSet resultSet = stmt.executeQuery("select username from logins where username = '"+username+"'");
				
			if (resultSet.next()) {
				System.out.println("That username already exists.\n");
				createAccount(conn);
			}
				
			System.out.println("Please create a password for your account.");
				
			String password = utility.scan.nextLine();
				
			stmt.executeUpdate("Insert into logins (username, password) values ('"+username+"','"+password+"')");
				
			System.out.println("Your account has been created.  Please login using your new credentials.\n");
			login.signIn();
		}	
		catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
