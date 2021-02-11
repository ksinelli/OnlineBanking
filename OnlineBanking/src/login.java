import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class login {
	
	private final static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Hello, and welcome to KAS Financial!\n");
		
		startup();

		}
	
	public static void startup() {
		System.out.println("Type \"Sign in\" to access your account.");
		System.out.println("Type \"Create account\" to create a new account.");
		System.out.println("Type \"Reset password\" to change your password.\n");
		System.out.println("Pro tip: capitalization doesn't matter when typing your choice :)");
		
		String choice = scan.nextLine().toLowerCase();
		
		if (choice.equals("sign in")) {
			signIn();
		}
		
		else if (choice.equals("create account")) {
			createAccount();
		}
		
		else if (choice.equals("reset password")) {
			resetPassword();
		}
		
		else {
			System.out.println("I'm sorry, I didn't understand that.  Please try again.\n");
			startup();
		}
		
	}
	
	
	public static void signIn() {
		System.out.println("Signing in..");
	}
	
	public static void createAccount() {
		System.out.println("Please enter a username.  Type \"Home\" to return to the main screen.");
		
		String username = scan.nextLine().toLowerCase();
		
		if (username.equals("home")) {
			startup();
		}
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:postgresql://ziggy.db.elephantsql.com/wxjpiraq","wxjpiraq","D6DpgE-lOUMD-QRrIBLiSUBhmxs5FW-_");
			
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("select username from logins where username = '"+username+"'");
			
			if (resultSet.next() == false) {
				System.out.println("Please enter a password");
				
				String password = scan.nextLine();
				
				stmt.executeUpdate("Insert into logins (username,password) values ('"+username+"','"+password+"')");
				
				System.out.println("Your account has been created.  Please login using your new credentials.\n");
				
				startup();
			}
			
			else {
				System.out.println("That username already exists in the database.  Please choose a different username.");
				createAccount();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void resetPassword() {
		System.out.println("Please enter your username.  Type \"Home\" to return to the main screen.");
		
		String username = scan.nextLine().toLowerCase();
		
		if (username.equals("home")) {
			startup();
		}
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://ziggy.db.elephantsql.com/wxjpiraq","wxjpiraq","D6DpgE-lOUMD-QRrIBLiSUBhmxs5FW-_");
			
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("select username from logins where username = '"+username+"'");
			
			if (resultSet.next() == false) {
				System.out.println("That username does not exist in our system.  Please check your spelling and try again or create a new account.\n");
				resetPassword();
			}		
			
			System.out.println("Please enter your current password.");
			
			String enteredPassword = scan.nextLine();
			
			resultSet = stmt.executeQuery("Select password from logins where username = '"+username+"'");
			
			resultSet.next();
			
			String currentPassword = resultSet.getString("Password");
			
			if (enteredPassword.equals(currentPassword)) {
				System.out.println("Please enter a new password.");
				
				String newPassword = scan.nextLine();
				
				stmt.executeUpdate("Update logins set password = '"+newPassword+"' where username = '"+username+"'");
				
				System.out.println("Your password has been updated.  Please login again using your new credentials.\n");
				
				startup();
			}
			
			else {
				System.out.println("The password you entered does not match our records.  Please try again.\n");
				resetPassword();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
