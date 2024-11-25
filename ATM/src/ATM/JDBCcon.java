package ATM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class JDBCcon {
    private static final String URL = "jdbc:mysql://localhost:3306/ATM";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Account getAccount(String accountNumber, String pin) {
        String query = "SELECT * FROM Account WHERE Ac_no = ? AND Mpin = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, accountNumber);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getString("Ac_no"), rs.getDouble("balance"), rs.getString("Mpin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Account getAccountByNumber(String accountNumber) { 
    	String query = "SELECT * FROM Account WHERE Ac_no = ?"; 
    	try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) { 
    		stmt.setString(1, accountNumber); 
    		ResultSet rs = stmt.executeQuery(); 
    		if(rs.next()) { 
    			return new Account(rs.getString("Ac_no"), rs.getDouble("balance"), rs.getString("Mpin")); 
    			} 
    		} 
    	catch (SQLException e) {
    		e.printStackTrace(); 
    		} 
    	return null; 
    	}

    public void updateAccountBalance(String accountNumber, double balance) {
        String query = "UPDATE Account SET balance = ? WHERE Ac_no = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, balance);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void recordTransaction(String accountNumber, String transactionType, double amount) { 
    	String query = "INSERT INTO Transactions (accountNo, transactionType, amount) VALUES (?, ?, ?)"; 
    	try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) { 
    		stmt.setString(1, accountNumber); 
    		stmt.setString(2, transactionType); 
    		stmt.setDouble(3, amount); 
    		stmt.executeUpdate(); 
    		} 
    	catch (SQLException e) {
    		e.printStackTrace(); 
    		} 
    	}
    
    public List<Transaction> getTransactionHistory(String accountNumber) { 
    	List<Transaction> transactions = new ArrayList<>(); 
    	String query = "SELECT * FROM Transactions WHERE accountNo = ?"; 
    	try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) { 
    		stmt.setString(1, accountNumber); 
    		ResultSet rs = stmt.executeQuery(); 
    		while (rs.next()) { 
    			transactions.add(new Transaction( rs.getInt("id"), rs.getString("accountNo"), rs.getString("transactionType"), rs.getDouble("amount"), rs.getTimestamp("transactionDate") )); 
    			} 
    		}
    	catch (SQLException e) { 
    		e.printStackTrace(); 
    		} 
    	return transactions; 
    	}
    public void changePin(String accountNumber, String newPin) { 
    	String query = "UPDATE Account SET Mpin = ? WHERE Ac_no = ?"; 
    	try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
    		stmt.setString(1, newPin);
    		stmt.setString(2, accountNumber);
    		stmt.executeUpdate();
    		} 
    	catch (SQLException e) { 
    		e.printStackTrace(); 
    		} 
    	}
    public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount) throws SQLException { 
    	Connection connection = null; 
    	try { 
    		connection = getConnection(); 
    		connection.setAutoCommit(false); 
    		Account fromAccount = getAccountByNumber(fromAccountNumber); 
    		Account toAccount = getAccountByNumber(toAccountNumber); 
    		if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) { 
    			fromAccount.withdraw(amount); 
    			toAccount.deposit(amount); 
    			updateAccountBalance(fromAccountNumber, fromAccount.getBalance()); 
    			updateAccountBalance(toAccountNumber, toAccount.getBalance()); 
    			recordTransaction(fromAccountNumber, "Transfer Out", amount); 
    			recordTransaction(toAccountNumber, "Transfer In", amount); connection.commit(); 
    			} 
    		else { 
    			connection.rollback(); 
    			throw new SQLException("Transfer failed: insufficient funds or invalid account number."); 
    			} 
    		} 
    	catch (SQLException e) { 
    		if (connection != null) { 
    			connection.rollback(); 
    			} 
    		throw e; 
    			} 
    	finally { 
    		if (connection != null) { 
    			connection.setAutoCommit(true); 
    			connection.close(); 
    			} 
    		} 
    	}
}

