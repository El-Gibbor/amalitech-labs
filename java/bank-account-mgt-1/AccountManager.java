public class AccountManager {
    // Fixed capacity of 50; accountCount marks how many slots are used and the next free index
    private Account[] accounts = new Account[50];
    private int accountCount = 0;

    public boolean addAccount(Account account) {
        if (accountCount < this.accounts.length) {
            this.accounts[accountCount++] = account;
            return true;
        }
        return false;
    }

    // Linear search over the used portion; returns null when no account matches
    public Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    public void viewAllAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }
        for (int i = 0; i < accountCount; i++) {
            accounts[i].displayAccountDetails();
        }
    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (int i = 0; i < accountCount; i++) {
            totalBalance += accounts[i].getBalance();
        }
        return totalBalance;
    }

    public int getAccountCount() {
        return accountCount;
    }
}
