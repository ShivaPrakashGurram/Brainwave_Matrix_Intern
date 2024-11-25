package ATM;
public class Account {
    private String accountNumber;
    private double balance;
    private String pin;

    public Account(String accountNumber, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }
}

//public class Account {
//    private String accountNumber;
//    private String pin;
//    private double balance;
//
//    public Account(String accountNumber, String pin, double balance) {
//        this.accountNumber = accountNumber;
//        this.pin = pin;
//        this.balance = balance;
//    }
//
//    public boolean validatePin(String inputPin) {
//        return this.pin.equals(inputPin);
//    }
//
//    public double getBalance() {
//        return balance;
//    }
//
//    public void deposit(double amount) {
//        if (amount > 0) {
//            balance += amount;
//        }
//    }
//
//    public void withdraw(double amount) {
//        if (amount > 0 && amount <= balance) {
//            balance -= amount;
//        }
//    }
//
//	public String getAccountNumber() {
//		return accountNumber;
//	}
//	 
//}

