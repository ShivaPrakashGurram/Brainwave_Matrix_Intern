package ATM;

public class Main {
    public static void main(String[] args) {
        JDBCcon database = new JDBCcon();
        ATM atm = new ATM(database);
        System.out.println("******Welcome XYZ Bank ATM******");
        atm.start();
    }
}


