package ATM;

import java.sql.SQLException;
import java.util.Scanner;

public class ATM {
    private Account currentAccount;
    private JDBCcon database;

    public ATM(JDBCcon database) {
        this.database = database;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        currentAccount = database.getAccount(accountNumber, pin);

        if (currentAccount != null) {
            showMenu(scanner);
        } else {
            System.out.println("Invalid account number or PIN.");
            System.out.println("______________________________________________\n");
            start();
        }

        scanner.close();
    }

    private void showMenu(Scanner scanner) {
        while (true) {
        	System.out.println("______________________________________________\n");
            System.out.println("ATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History"); 
            System.out.println("5. Change PIN"); 
            System.out.println("6. Transfer"); 
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
            	System.out.println("______________________________________________\n");
            	System.out.println("Balance: $"+ currentAccount.getBalance());
                break;
            case 2:
            	System.out.println("______________________________________________\n");
                System.out.print("Enter deposit amount: ");
                double depositAmount = scanner.nextDouble();
                currentAccount.deposit(depositAmount);
                database.updateAccountBalance(currentAccount.getAccountNumber(), currentAccount.getBalance());
                database.recordTransaction(currentAccount.getAccountNumber(), "Deposit", depositAmount);
                break;
            case 3:
            	System.out.println("______________________________________________\n");
                System.out.print("Enter withdraw amount: ");
                double withdrawAmount = scanner.nextDouble();
                currentAccount.withdraw(withdrawAmount);
                database.updateAccountBalance(currentAccount.getAccountNumber(), currentAccount.getBalance());
                database.recordTransaction(currentAccount.getAccountNumber(), "Withdraw", withdrawAmount);
                break;
            case 4:
            	System.out.println("______________________________________________\n");
                System.out.println("Transaction History:");
                for (Transaction transaction : database.getTransactionHistory(currentAccount.getAccountNumber())) {
                    System.out.println(transaction);
                }
                break;
            case 5:
            	System.out.println("______________________________________________\n");
                System.out.print("Enter new PIN: ");
                String newPin = scanner.next();
                database.changePin(currentAccount.getAccountNumber(), newPin);
                System.out.println("PIN successfully changed.");
                break;
            case 6:
            	System.out.println("______________________________________________\n");
                System.out.print("Enter target account number: ");
                String targetAccountNumber = scanner.next();
                System.out.print("Enter transfer amount: ");
                double transferAmount = scanner.nextDouble();
                try {
                    database.transferMoney(currentAccount.getAccountNumber(), targetAccountNumber, transferAmount);
                    System.out.println("Transfer successful.");
                } catch (SQLException e) {
                    System.out.println("Transfer failed: " + e.getMessage());
                }
                break;
            case 7:
            	System.out.println("______________________________________________\n");
                System.out.println("Thank you for using our ATM.");
                return;
            default:
            	System.out.println("______________________________________________\n");
                System.out.println("Invalid option. Please try again.");
        }

        }
    }
}
