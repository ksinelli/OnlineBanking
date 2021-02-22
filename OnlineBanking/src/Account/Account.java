package Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import Customer.Customer;
import Utility.DatabaseConnection;
import Utility.MyScanner;

public class Account {
	
	private static ResultSet resultSet;
	private static String choice;
	private String accountNumber;
	private String accountType;
	private int accountBalance;
	
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

public void openAccount(Customer customer, Account account) {
	System.out.println("Which type of account would you like to open?\n");
	System.out.println("Checking");
	System.out.println("Savings\n");
	System.out.println("You currently have the following accounts open:\n");
	
	try {
		
		resultSet = DatabaseConnection.executeQuery("select account_number, account_type, balance from account where customer_id = '"+customer.getCustomer_id()+"'");
		
		while (resultSet.next()) {
			for(int i = 1; i < 3; i++)
		        System.out.print(resultSet.getString(i) + " ");
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	choice = MyScanner.getInputToLower();
	
	if (choice.equals("checking")) {
		
	}
	
	else if (choice.equals("savings")) {
		
	}
	
	else {
		System.out.println("I did not understand that.  Please type your selection again.");
	}
	
}

public void closeAccount(Customer customer, Account account) {
	
}


}
