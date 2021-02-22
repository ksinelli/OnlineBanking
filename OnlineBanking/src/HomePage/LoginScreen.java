package HomePage;
import java.sql.ResultSet;
import java.sql.SQLException;
import Customer.Customer;
import Customer.CustomerDashboard;
import Utility.MyScanner;
import Utility.DatabaseConnection;

public class LoginScreen {
	
	private static ResultSet resultSet;

	public static void main(String[] args) {
		
		DatabaseConnection.getDbConnection();
		
		MyScanner.openScanner();
		
		System.out.println("Hello, and welcome to KAS Financial!\n");
		
		startup();

	}
	
	public static void startup() {
		Customer customer = new Customer();
		System.out.println("Type \"Sign in\" to access your account.");
		System.out.println("Type \"Create account\" to create a new account.");
		System.out.println("Type \"Reset password\" to change your password.\n");
		System.out.println("Type \"Home\" at any time to return to this menu.\n");
		System.out.println("Pro tip: capitalization doesn't matter when typing your choice :)");
		
		String choice = MyScanner.getInputToLower();
		
		if (choice.equals("sign in")) {
			signIn(customer);
		}
		
		else if (choice.equals("create account")) {
			createAccount(customer);
		}
		
		else if (choice.equals("reset password")) {
			resetPassword(customer);
		}
		
		else {
			System.out.println("I'm sorry, I didn't understand that.  Please try again.\n");
			startup();
		}	
	}
	
	public static void signIn(Customer customer) {
		customer.setUsername(getUsernameFromDb(customer));
		customer = (getPasswordFromDb(customer));
		CustomerDashboard.dashboardMenu(customer);
	}
	
	public static void resetPassword(Customer customer) {
		customer.setUsername(getUsernameFromDb(customer));
		customer = getPasswordFromDb(customer);
		updatePassword(customer);		
	}
	
	
	
	//retrieve entered username from database and if it does not exist, redirect to home page.
	public static String getUsernameFromDb(Customer customer) {
			
		System.out.println("Please enter your username.");
			
		String input = MyScanner.getInputToLower();
			
		try {
			
			resultSet = DatabaseConnection.executeQuery("select username from customer where username = '"+input+"'");
			
			if (resultSet.next() == false) {
				System.out.println("That username does not exist in our system.\n");
				startup();
			}
		}
			catch (SQLException e) {
			e.printStackTrace();
			}		
		return input;
	}

	//retrieve database password and compare to entered password.  If they do not match, return to home page.
	public static Customer getPasswordFromDb(Customer customer) {
	
		System.out.println("Please enter your password.");
	
		String password = MyScanner.getInput();

		String dbPassword;
	
		try {
		
			resultSet = DatabaseConnection.executeQuery("select password from customer where username = '"+customer.getUsername()+"'");
	
			resultSet.next();
		
			dbPassword = resultSet.getString("password");
	
			if (!password.equals(dbPassword)) {
				System.out.println("The password you entered does not match our records.  Please try again.\n");
				startup();
			}
			
			resultSet = DatabaseConnection.executeQuery("select customer_id,first_name,last_name,address_line_1,address_line_2,city,state,zip,phone,email from customer where username = '"+customer.getUsername()+"'");
			while (resultSet.next()) {
				customer.setCustomer_id(resultSet.getInt("customer_id"));
				customer.setFirst_name(resultSet.getString("first_name"));
				customer.setLast_name(resultSet.getString("last_name"));
				customer.setAddress_line_1(resultSet.getString("address_line_1"));
				customer.setAddress_line_2(resultSet.getString("address_line_2"));
				customer.setCity(resultSet.getString("city"));
				customer.setState(resultSet.getString("state"));
				customer.setZip(resultSet.getString("zip"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setEmail(resultSet.getString("email"));
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customer;
	}

	//Update SQL database with new user-supplied password
	public static void updatePassword(Customer customer) {
	
		System.out.println("Please enter a new password.");
	
		String newPassword = MyScanner.getInput();
	
		try {
		
			DatabaseConnection.executeUpdate("Update customer set password = '"+newPassword+"' where username = '"+customer.getUsername()+"'");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
		System.out.println("Your password has been updated.  Please login again with your new credentials.\n");
		
		signIn(customer);
	}
	
	//create new customer profile
	public static void createAccount(Customer customer) {
		
		System.out.println("Please enter a username.");
			
		customer.setUsername(MyScanner.getInputToLower());
			
		try {
						
			resultSet = DatabaseConnection.executeQuery("select username from customer where username = '"+customer.getUsername()+"'");
				
			if (resultSet.next()) {
				System.out.println("That username already exists.\n");
				createAccount(customer);
			}
			
			System.out.println("Please create a password for your account.");
				
			customer.setPassword(MyScanner.getInput());	
			
		}
			
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		customer = customer.updateProfile(customer);
		
		try {
			DatabaseConnection.executeUpdate("Insert into customer (username, password, first_name, last_name, address_line_1, address_line_2, city, state, zip, phone, email) values ('"+customer.getUsername()+"','"+customer.getPassword()+"','"+customer.getFirst_name()+"','"+customer.getLast_name()+"','"+customer.getAddress_line_1()+"','"+customer.getAddress_line_2()+"','"+customer.getCity()+"','"+customer.getState()+"','"+customer.getZip()+"','"+customer.getPhone()+"','"+customer.getEmail()+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		System.out.println("Your account has been created.  Please login using your new credentials.\n");
		signIn(customer);
	}	
}
