
public class dashboard {
	
	public static void dashboardMenu () {
		System.out.println("You are at the dashboard.  Type one of the options below:\n");
		System.out.println("Open Account");
		System.out.println("Close Account");
		System.out.println("Make Deposit");
		System.out.println("Make Withdrawal");
		System.out.println("Check Balance");
		System.out.println("Transaction History");
		System.out.println("Sign out");
		
		String choice = utility.scan.nextLine().toLowerCase();
		
		if (choice.equals("open account")) {
			openAccount();
		}
		
		else if (choice.equals("close account")) {
			closeAccount();
		}
		
		else if (choice.equals("make deposit")) {
			makeDeposit();
		}
		
		else if (choice.equals("make withdrawal")) {
			makeWithdrawal();
		}
		
		else if (choice.equals("check balance")) {
			checkBalance();
		}
		
		else if (choice.equals("transaction history")) {
			transactionHistory();
		}
		
		else if (choice.equals("sign out")) {
			signOut();
		}
		
		else {
			System.out.println("I'm sorry.  I didn't understand that.  Please check your spelling and try again.\n");
			dashboardMenu();
		}
		
		}
		

		
	
	public static void openAccount() {
		
	}

	public static void closeAccount() {
		
	}
	
	public static void makeDeposit() {
		
	}
	
	public static void makeWithdrawal() {
		
	}
	
	public static void checkBalance() {
		
	}

	public static void transactionHistory() {
		
	}
	
	public static void signOut() {
		
	}
}
