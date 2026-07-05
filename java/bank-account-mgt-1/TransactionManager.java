public class TransactionManager {
    // Fixed capacity of 200; transactionCount marks how many slots are used and the next free index
    private Transaction[] transactions = new Transaction[200];
    private int transactionCount = 0;

    public boolean addTransaction(Transaction transaction) {
        if (transactionCount < this.transactions.length) {
            this.transactions[transactionCount++] = transaction;
            return true;
        }
        return false;
    }

    public void viewTransactionsByAccount(String accountNumber) {
        boolean found = false;
        // walk backwards so the newest transaction prints first
        for (int i = transactionCount - 1; i >= 0; i--) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) {
                transactions[i].displayTransactionDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for account: " + accountNumber);
        }
    }

    public double calculateTotalDeposits(String accountNumber) {
        double totalDeposits = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)
                    && transactions[i].getType().equalsIgnoreCase("deposit")) {
                totalDeposits += transactions[i].getAmount();
            }
        }
        return totalDeposits;
    }

    public double calculateTotalWithdrawals(String accountNumber) {
        double totalWithdrawals = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)
                    && transactions[i].getType().equalsIgnoreCase("withdraw")) {
                totalWithdrawals += transactions[i].getAmount();
            }
        }
        return totalWithdrawals;
    }

    public int getTransactionCount() {
        return transactionCount;
    }
}
