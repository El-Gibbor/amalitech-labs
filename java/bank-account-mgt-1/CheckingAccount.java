public class CheckingAccount extends Account {
    private double overdraftLimit;
    private double monthlyFee;
    
    public CheckingAccount(Customer customer, double balance) {
        super(customer, balance);
        this.overdraftLimit = 1000;
        this.monthlyFee = 10;
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Customer ID: " + getCustomer().getCustomerId());
        System.out.println("Customer Name: " + getCustomer().getName());
        System.out.println("Balance: " + getBalance());
        System.out.println("Overdraft Limit: " + overdraftLimit);
        System.out.println("Monthly Fee: " + monthlyFee);
        System.out.println("Status: " + getStatus());
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    // Override to allow overdraft, unlike SavingsAccount
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > (getBalance() + overdraftLimit)) {
            return false;
        }
        setBalance(getBalance() - amount);
        return true;
    }

    public double applyMonthlyFee() {
        if (getCustomer().hasWaivedFees()) {
            return 0; // No fee for premium customers
        }
        setBalance(getBalance() - monthlyFee);
        return monthlyFee;
    }
}
