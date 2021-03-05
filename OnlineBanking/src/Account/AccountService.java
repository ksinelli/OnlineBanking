package Account;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import Customer.Customer;
import Utility.DatabaseConnection;
import Utility.MyScanner;

public class AccountService {
	private static String choice;
	private static PreparedStatement stmt;
	
	public void openAccount(Customer customer, Account account) {
		System.out.println("Which type of account would you like to open?\n");
		System.out.println("Checking");
		System.out.println("Savings\n");
		
		choice = MyScanner.getInputToLower();
		
		if (choice.equals("checking") || choice.equals("savings")) {
			try {
				stmt = DatabaseConnection.prepareStatement("Insert into account (account_number, account_type, account_balance, customer_id) values (nextval('account_number'), ?, 0, ?)");
				stmt.setString(1, choice);
				stmt.setInt(2, customer.getCustomerID());
				stmt.executeUpdate();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("The "+choice+" account you requested is now open.  Try typing \"Make Deposit\" to deposit money into the account.\n");
		}
		else {
			System.out.println("I didn't understand that.  Please try again.\n");
		}
	}
	
	public void closeAccount(Customer customer, ArrayList<Account> accountArray, Account account, ArrayList<Transaction> transactionArray) {
		System.out.println("WARNING: THIS WILL DELETE ALL TRANSACTION HISTORY ASSOCIATED WITH THE ACCOUNT.");
		System.out.println("Remember that you can type \"Home\" at any time to return to the login screen.");
		
		account.checkAccountNumber(customer, accountArray, account);
		
		try {
			//delete account record from account table
			stmt = DatabaseConnection.prepareStatement("Delete from account where account_number = ?");
			stmt.setString(1, account.getAccountNumber());
			stmt.executeUpdate();
			
			//delete all transactions associated with account from transaction table
			stmt = DatabaseConnection.prepareStatement("Delete from transaction where account_number = ?");
			stmt.setString(1, account.getAccountNumber());
			stmt.executeUpdate();	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Your account has been closed and let's pretend that any money in the account was returned to you in cash :)\n");
	}
}