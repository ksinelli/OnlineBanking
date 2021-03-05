package Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Customer.Customer;
import Utility.DatabaseConnection;
import Utility.MyScanner;

public class Account {
	
	private int customerID;
	private String accountNumber;
	private String accountType;
	private int accountBalance;
	private static ResultSet resultSet;
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
			
			//Add the data that was retrieved from the database to Account instances and store each instance in the Account ArrayList
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
	
	//Checks if the customer has any account open yet
	public boolean checkForOpenAccount(Customer customer, ArrayList<Account> accountArray, Account account) {
		boolean hasAccount = false;

		try {
			//retrieve number of accounts from database
			stmt = DatabaseConnection.prepareStatement("select count(account_number) from account where customer_id = ?");
			stmt.setInt(1, customer.getCustomerID());
			resultSet = stmt.executeQuery();
			resultSet.next();
				
			//if no accounts open
			if (resultSet.getInt(1) == 0) {
				System.out.println("You do not currently have any open accounts.\n");
			}
			//if 1 or more accounts open, display each account and its properties in console with formatting
			else if (!(resultSet.getInt(1) == 0)) {
				hasAccount = true;
					
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
				System.out.println();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return hasAccount;
	}
	
	//make sure the account number entered matches the account number of an account that is in the accountArray
	public void checkAccountNumber(Customer customer, ArrayList<Account> accountArray, Account account) {
		boolean isValidAccountNumber = false;
		String choice;
		
		while (isValidAccountNumber == false) {
			System.out.println("Please enter the six digit account number for the account for which you would like to perform this action.");
		
			choice = MyScanner.getInput();
		
			for (int i = 0; i < accountArray.size(); i++) {
				if (accountArray.get(i).getAccountNumber().equals(choice)) {
					account.setAccountNumber(accountArray.get(i).getAccountNumber());
					account.setCustomerID(accountArray.get(i).getCustomerID());
					account.setAccountBalance(accountArray.get(i).getAccountBalance());
					account.setAccountType(accountArray.get(i).getAccountType());
					isValidAccountNumber = true;
				}
			}
			if (isValidAccountNumber == false){
				System.out.println("I didn't recognize that account number.  Please try again.\n");
			}
		}
	}
}