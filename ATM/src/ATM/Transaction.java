package ATM;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private String accountNo;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;

    public Transaction(int id, String accountNo, String transactionType, double amount, Timestamp transactionDate) {
        this.id = id;
        this.accountNo = accountNo;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +"id=" + id +
                ", accountNo='" + accountNo + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}

