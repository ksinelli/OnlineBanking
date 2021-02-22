package Customer;
import Utility.DatabaseConnection;
import Utility.MyScanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import Account.Account;
import Account.Transaction;

public class CustomerDashboard {
	
	private static ResultSet resultSet;
	private static String choice;
	
	public static void dashboardMenu (Customer customer) {
		
		Account account = new Account();
		Transaction transaction = new Transaction();
		
		System.out.println("You are at the dashboard.  Type one of the options below:\n");
		System.out.println("Open Account");
		System.out.println("Close Account");
		System.out.println("Make Deposit");
		System.out.println("Make Withdrawal");
		System.out.println("Check Balance");
		System.out.println("Transaction History");
		System.out.println("Update Profile");
		System.out.println("Sign Out");
		
		choice = MyScanner.getInputToLower();
		
		if (choice.equals("open account")) {
			account.openAccount(customer, account);
		}
		
		else if (choice.equals("close account")) {
			account.closeAccount(customer, account);
		}
		
		else if (choice.equals("make deposit")) {
			makeDeposit(customer);
		}
		
		else if (choice.equals("make withdrawal")) {
			makeWithdrawal(customer);
		}
		
		else if (choice.equals("check balance")) {
			checkBalance(customer);
		}
		
		else if (choice.equals("transaction history")) {
			transactionHistory(customer);
		}
		
		else if (choice.equals("update profile")) {
			customer.updateProfile(customer);
			
			try {
				DatabaseConnection.executeUpdate("Update customer set first_name = '"+customer.getFirst_name()+"', last_name = '"+customer.getLast_name()+"', address_line_1 = '"+customer.getAddress_line_1()+"', address_line_2 = '"+customer.getAddress_line_2()+"', city = '"+customer.getCity()+"', state = '"+customer.getState()+"', zip = '"+customer.getZip()+"', phone = '"+customer.getPhone()+"', email = '"+customer.getEmail()+"' where customer_id = "+customer.getCustomer_id());
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("Your profile has been updated.\n");
			dashboardMenu(customer);
		}
		
		else if (choice.equals("sign out")) {
			signOut(customer);
		}
		
		else {
			System.out.println("I'm sorry.  I didn't understand that.  Please check your spelling and try again.\n");
			dashboardMenu(customer);
		}
	}
	
	public static void makeDeposit(Customer customer) {
		
	}
	
	public static void makeWithdrawal(Customer customer) {
		
	}
	
	public static void checkBalance(Customer customer) {
		
	}

	public static void transactionHistory(Customer customer) {
		
	}
	
	public static void signOut(Customer customer) {
		System.out.println("Thanks for using KAS Financial today!  You have been signed out of your account.");
		DatabaseConnection.closeDbConnection();
		MyScanner.closeScanner();
	}
}
