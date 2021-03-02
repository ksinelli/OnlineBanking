package Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Customer.Customer;
import Customer.CustomerDashboard;
import Utility.DatabaseConnection;
import Utility.MyScanner;

public class Account {
	
	private int customerID;
	private String accountNumber;
	private String accountType;
	private int accountBalance;
	
	private static ResultSet resultSet;
	private static String choice;
	private static PreparedStatement stmt;
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}
	

	public static void openAccount(Customer customer, Account account) {
		System.out.println("\nWhich type of account would you like to open?\n");
		System.out.println("Checking");
		System.out.println("Savings\n");
		
		choice = MyScanner.getInputToLower();
		
		if (choice.equals("checking") || choice.equals("savings")) {

			try {
				stmt = DatabaseConnection.prepareStatement("Insert into account (account_number, account_type, account_balance, customer_id) values (nextval('account_number'), ?, 0, ?)");
				stmt.setString(1, choice);
				stmt.setInt(2, customer.getCustomerID());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("The "+choice+" account you requested is now open.  Try typing \"Deposit\" to deposit money into the account.");
			CustomerDashboard.dashboardMenu(customer);
		}
	
		else {
			System.out.println("I didn't understand that.  Please try again.\n");
			CustomerDashboard.dashboardMenu(customer);
		}
	
	}

	public void closeAccount(Customer customer, ArrayList<Account> accountArray, Account account, ArrayList<Transaction> transactionArray) {
		
		System.out.println("\nWARNING: THIS WILL DELETE ALL TRANSACTION HISTORY ASSOCIATED WITH THE ACCOUNT.");
		System.out.println("Remember that you can type \"Home\" at any time to return to the login screen.");
		System.out.println("Type the account number of the account you wish to close.");
		
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Your account has been deleted and let's pretend that any money in the account was returned to you in cash :)");
		CustomerDashboard.dashboardMenu(customer);
	}

	public static ArrayList<Account> createAccountArrayList (Customer customer) {
		
		ArrayList<Account> accountArray = null;
		
		//Create an ArrayList of Account objects.  The size of the ArrayList is determined by the count() function of the SQL query.
		//Then add each account object to the ArrayList
		try {
			stmt = DatabaseConnection.prepareStatement("select count(account_number) from account where customer_id = ?");
			stmt.setInt(1, customer.getCustomerID());
			resultSet = stmt.executeQuery();
			resultSet.next();
			
			int numberOfAccounts = resultSet.getInt(1);
			
			accountArray = new ArrayList<Account>(numberOfAccounts);
			
			for (int i = 1; i <= numberOfAccounts; i++) {
				Account accountInArrayList = new Account();
				accountArray.add(accountInArrayList);	
			}
		
			//retrieve account data from the database about all the accounts that the customer has
			stmt = DatabaseConnection.prepareStatement("select account_number, customer_id, account_type, account_balance from account where customer_id = ?");
			stmt.setInt(1, customer.getCustomerID());
			resultSet = stmt.executeQuery();
			
			//Add the data that was retrieved from the database to Account instances in the Account ArrayList
			//The outer loop iterates over each Account instance
			//The inner loop iterates over each individual property for each Account instance
			int i = 0;
			while (i <= numberOfAccounts-1) {
				while (resultSet.next()) {
					accountArray.get(i).setAccountNumber(resultSet.getString(1));
					accountArray.get(i).setCustomerID(resultSet.getInt(2));
					accountArray.get(i).setAccountType(resultSet.getString(3));
					accountArray.get(i).setAccountBalance(resultSet.getInt(4));
					i++;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return accountArray;
	}
	
	/*
	 * public static void rerouteIfNoAccount(boolean hasAccount, Customer customer)
	 * { if (hasAccount == false) { CustomerDashboard.dashboardMenu(customer); } }
	 */
	
	
	//Checks if the customer has any account open yet
	//Returns a different message based on whether hasAccount is true or false
	public void checkForOpenAccount(Customer customer, ArrayList<Account> accountArray, Account account) {

		try {
				
			//retrieve number of accounts from database
			stmt = DatabaseConnection.prepareStatement("select count(account_number) from account where customer_id = ?");
			stmt.setInt(1, customer.getCustomerID());
			resultSet = stmt.executeQuery();
			resultSet.next();
				
			//if no accounts open
			if (resultSet.getInt(1) == 0) {
				System.out.println("You do not currently have any open accounts.");
				CustomerDashboard.dashboardMenu(customer);
			}
				
			//if 1 or more accounts open, display each account and its properties in console with formatting
			else if (!(resultSet.getInt(1) == 0)) {
					
				System.out.println("You currently have the following accounts open:\n");
				System.out.printf("%-20s%-20s%-20s%n", "Account Number", "Balance", "Account Type");
					
				stmt = DatabaseConnection.prepareStatement("select account_number, account_balance, account_type from account where customer_id = ?");
				stmt.setInt(1, customer.getCustomerID());
				resultSet = stmt.executeQuery();
					
				while (resultSet.next()) {
					for (int i = 1; i <= 3; i++) {
						System.out.printf("%-20s", resultSet.getString(i));
							if (i == 3) {
								System.out.println();
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			}
	}
	
	public void checkAccountNumber(Customer customer, ArrayList<Account> accountArray, Account account) {	
		
		String choice = MyScanner.getInput();
		
		for (int i = 0; i<=accountArray.size()-1; i++) {
			if (accountArray.get(i).getAccountNumber().equals(choice)) {
				account.setAccountNumber(accountArray.get(i).getAccountNumber());
				account.setCustomerID(accountArray.get(i).getCustomerID());
				account.setAccountBalance(accountArray.get(i).getAccountBalance());
				account.setAccountType(accountArray.get(i).getAccountType());
			}
			
			else {
				System.out.println("I didn't recognize that account number.  Please try again.");
				checkAccountNumber(customer, accountArray, account);
			}
		}
	}
}
