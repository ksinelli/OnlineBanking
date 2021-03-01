package Customer;
import Utility.DatabaseConnection;
import Utility.MyScanner;
import java.util.ArrayList;
import Account.Account;
import Account.Transaction;

public class CustomerDashboard {
	
	private static String choice;
	private static boolean hasAccount;
	
	public static void dashboardMenu (Customer customer) {
		
		ArrayList<Account> accountArray = Account.createAccountArrayList(customer);
		Account account = new Account();
		
		ArrayList<Transaction> transactionArray = Transaction.createTransactionArrayList(customer);
		Transaction transaction = new Transaction();
		
		System.out.println("\nYou are at the dashboard.  Type one of the options below:\n");
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
			account.hasAccount(customer);
			Account.openAccount(customer, account);
		}
		
		else if (choice.equals("close account")) {
			hasAccount = account.hasAccount(customer);
			Account.rerouteIfNoAccount(hasAccount, customer);
			account.closeAccount(customer, accountArray, account, transactionArray);
			dashboardMenu(customer);
		}
		
		else if (choice.equals("make deposit")) {
			hasAccount = account.hasAccount(customer);
			Account.rerouteIfNoAccount(hasAccount, customer);
			System.out.println("\nPlease enter the six digit account number for the account into which you would like to deposit money.");
			account.checkAccountNumber(customer, accountArray, account);
			transaction.makeDeposit(customer, account,transaction, transactionArray);
			dashboardMenu(customer);
		}
		
		else if (choice.equals("make withdrawal")) {
			hasAccount = account.hasAccount(customer);
			Account.rerouteIfNoAccount(hasAccount, customer);
			System.out.println("\nPlease enter the six digit account number for the account from which you would like to withdraw money.");
			account.checkAccountNumber(customer, accountArray, account);
			transaction.makeWithdrawal(customer, account, transaction, transactionArray);
			dashboardMenu(customer);
		}
		
		else if (choice.equals("check balance")) {
			hasAccount = account.hasAccount(customer);
			Account.rerouteIfNoAccount(hasAccount, customer);
			dashboardMenu(customer);
		}
		
		else if (choice.equals("transaction history")) {
			hasAccount = account.hasAccount(customer);
			Account.rerouteIfNoAccount(hasAccount, customer);
			transaction.transactionHistory(customer, accountArray, account, transaction, transactionArray);
			dashboardMenu(customer);
		}
		
		else if (choice.equals("update profile")) {
			customer = customer.createOrUpdateProfile(customer);
			
			System.out.println(customer.toString());
			MyScanner.getInput();
			
			customer.pushProfileToDatabase(customer);
			
			System.out.println("Your profile has been updated.");
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
	
	public static void signOut(Customer customer) {
		System.out.println("Thanks for using KAS Financial today!  You have been signed out of your account.");
		DatabaseConnection.closeDbConnection();
		MyScanner.closeScanner();
	}
}
