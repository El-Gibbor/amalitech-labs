public class SavingsAccount extends Account {
    private double interestRate;
    private double minimumBalance;

    public SavingsAccount(Customer customer, double balance) {
        super(customer, balance);
        this.interestRate = 0.035;
        this.minimumBalance = 500;
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Customer ID: " + getCustomer().getCustomerId());
        System.out.println("Customer Name: " + getCustomer().getName());
        System.out.println("Balance: " + getBalance());
        System.out.println("Interest Rate: " + interestRate);
        System.out.println("Minimum Balance: " + minimumBalance);
        System.out.println("Status: " + getStatus());
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    // reject if min balance is not met, then let Account do the subtraction
    @Override
    public boolean withdraw(double amount) {
        if (getBalance() - amount < minimumBalance) {
            return false;
        }
        return super.withdraw(amount);
    }

    public double calculateInterest() {
        return getBalance() * interestRate;     
    }
}