package Customer;
import Utility.DatabaseConnection;
import Utility.MyScanner;
import java.util.ArrayList;
import Account.Account;
import Account.AccountService;
import Account.Transaction;

public class CustomerDashboard {
	private static String choice;
	
	public static void dashboardMenu (Customer customer) {
		boolean isLoggedIn = true;
		boolean hasAccount;
		
		while (isLoggedIn == true) {
		
			Account account = new Account();
			Transaction transaction = new Transaction();
			AccountService accountService = new AccountService();
			
			ArrayList<Account> accountArray = Account.createAccountArrayList(customer);
			ArrayList<Transaction> transactionArray = Transaction.createTransactionArrayList(customer);
			
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
		
			switch(choice) {
				case "open account":
					account.checkForOpenAccount(customer, accountArray, account);
					accountService.openAccount(customer, account);
					break;
				case "close account":
					hasAccount = account.checkForOpenAccount(customer, accountArray, account);
					while (hasAccount == true) {
						accountService.closeAccount(customer, accountArray, account, transactionArray);
						break;
					}
					break;
				case "make deposit":
					account.checkForOpenAccount(customer, accountArray, account);
					account.checkAccountNumber(customer, accountArray, account);
					transaction.makeDeposit(customer, account,transaction, transactionArray);
					break;
				case "make withdrawal":
					account.checkForOpenAccount(customer, accountArray, account);
					account.checkAccountNumber(customer, accountArray, account);
					transaction.makeWithdrawal(customer, account, transaction, transactionArray);
					break;
				case "check balance":
					account.checkForOpenAccount(customer, accountArray, account);
					break;
				case "transaction history":
					account.checkForOpenAccount(customer, accountArray, account);
					account.checkAccountNumber(customer, accountArray, account);
					transaction.transactionHistory(customer, accountArray, account, transaction, transactionArray);
					break;
				case "update profile":
					customer.createOrUpdateProfile(customer);
					customer.pushProfileToDatabase(customer);
					break;
				case "sign out":
					isLoggedIn = false;
					signOut(customer);
					break;
				default:
					System.out.println("I'm sorry.  I didn't understand that.  Please check your spelling and try again.\n");
			}
		}
	}
	
	public static void signOut(Customer customer) {
		System.out.println("Thanks for using KAS Financial today!  You have been signed out of your account.");
		
		DatabaseConnection.closeDbConnection();
		MyScanner.closeScanner();
	}
}