package Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import Customer.Customer;
import Customer.CustomerDashboard;
import Utility.DatabaseConnection;
import Utility.MyScanner;

public class Transaction {
	private int transactionID;
	private String transactionDate;
	private int transactionAmount;
	private String transactionType;
	private int customerID;
	private String accountNumber;
	private static ResultSet resultSet;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YYYY");
	LocalDate localDate = LocalDate.now();
	
	private static PreparedStatement stmt;
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(int transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
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
	
	
	public static ArrayList<Transaction> createTransactionArrayList (Customer customer) {
		
		ArrayList<Transaction> transactionArray = null;
		
		//Create an ArrayList of Transaction objects.  The size of the ArrayList is determined by the count() function of the SQL query.
		//Then add each transaction object to the ArrayList
		try {
			stmt = DatabaseConnection.prepareStatement("select count(transaction_id) from transaction where customer_id = ?");
			stmt.setInt(1, customer.getCustomerID());
			resultSet = stmt.executeQuery();
			resultSet.next();
			int numberOfTransactions = resultSet.getInt(1);
			transactionArray = new ArrayList<Transaction>(numberOfTransactions);
			for (int i = 1; i <= numberOfTransactions; i++) {
				Transaction transactionInArrayList = new Transaction();
				transactionArray.add(transactionInArrayList);	
			}
		
			//retrieve transaction data from the database about all the transactions that the customer has
			stmt = DatabaseConnection.prepareStatement("select transaction_id, transaction_date, transaction_amount, transaction_type, customer_id, account_number from transaction where customer_id = ?");
			stmt.setInt(1, customer.getCustomerID());
			resultSet = stmt.executeQuery();
			
			//Add the data that was retrieved from the database to Transaction instances in the Transaction ArrayList
			//The outer loop iterates over each Transaction instance
			//The inner loop iterates over each individual property for each Transaction instance		
			int i = 0;
			while (i <= numberOfTransactions-1) {
				while (resultSet.next()) {
					transactionArray.get(i).setTransactionID(resultSet.getInt(1));
					transactionArray.get(i).setTransactionDate(resultSet.getString(2));
					transactionArray.get(i).setTransactionAmount(resultSet.getInt(3));
					transactionArray.get(i).setTransactionType(resultSet.getString(4));
					transactionArray.get(i).setCustomerID(resultSet.getInt(5));
					transactionArray.get(i).setAccountNumber(resultSet.getString(6));
					i++;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return transactionArray;
	}
	
	
	
	public void makeDeposit(Customer customer, Account account, Transaction transaction, ArrayList<Transaction> transactionArray) {
		System.out.println("How much would you like to deposit?");
		int depositAmount = MyScanner.getNumber();
		account.setAccountBalance(account.getAccountBalance() + depositAmount);
		try {
			//update account table
			stmt = DatabaseConnection.prepareStatement("Update account set account_balance = ? where account_number = ?");
			stmt.setInt(1, account.getAccountBalance());
			stmt.setString(2, account.getAccountNumber());
			stmt.executeUpdate();
			
			//update transaction table
			stmt = DatabaseConnection.prepareStatement("Insert into transaction (customer_id, transaction_date, transaction_amount, transaction_type, account_number) values (?,?,?,?,?)");
			stmt.setInt(1, customer.getCustomerID());
			stmt.setString(2, dtf.format(localDate));
			stmt.setInt(3, depositAmount);
			stmt.setString(4, "Deposit");
			stmt.setString(5, account.getAccountNumber());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Your funds have been deposited.");
	}

	public void makeWithdrawal(Customer customer, Account account, Transaction transaction, ArrayList<Transaction> transactionArray) {
		System.out.println("How much would you like to withdraw?");
		int withdrawalAmount = MyScanner.getNumber();
		if (account.getAccountBalance() - withdrawalAmount < 0) {
			System.out.println("You don't have that much money in your account!  Try withdrawing a lesser amount or withdrawing from a different account, if available.\n");
			CustomerDashboard.dashboardMenu(customer);
		}
		int newBalance = account.getAccountBalance() - withdrawalAmount;
		
		try {
			//update account table
			stmt = DatabaseConnection.prepareStatement("Update account set account_balance = ? where account_number = ?");
			stmt.setInt(1, newBalance);
			stmt.setString(2, account.getAccountNumber());
			stmt.executeUpdate();
			
			//update transaction table
			stmt = DatabaseConnection.prepareStatement("Insert into transaction (customer_id, transaction_date, transaction_amount, transaction_type, account_number) values (?,?,?,?)");
			stmt.setInt(1, customer.getCustomerID());
			stmt.setString(2, dtf.format(localDate));
			stmt.setInt(3, withdrawalAmount);
			stmt.setString(4, "Withdrawal");
			stmt.setString(5, account.getAccountNumber());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Your funds have been withdrawn.");
	}
	
	public void transactionHistory(Customer customer, ArrayList<Account> accountArray, Account account, Transaction transaction, ArrayList<Transaction> transactionArray) {
		account.hasAccount(customer);
		System.out.println("\nPlease enter the six digit account number for which you would like to get the transaction history.");
		account = account.checkAccountNumber(customer, accountArray, account);
		System.out.println("Transaction ID\t\tTransaction Date\t\tTransaction Amount\t\tTransaction Type");
		for (int i = 0; i<=transactionArray.size()-1; i++) {
			if (transactionArray.get(i).getAccountNumber().equals(account.getAccountNumber())) {
				System.out.print(transactionArray.get(i).getTransactionID()+"\t\t\t");
				System.out.print(transactionArray.get(i).getTransactionDate()+"\t\t\t");
				System.out.print(transactionArray.get(i).getTransactionAmount()+"\t\t\t\t");
				System.out.print(transactionArray.get(i).getTransactionType());
				System.out.println();
			}
		}
	}
}