import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();
        TransactionManager transactionManager = new TransactionManager();

        // Seed starter accounts so View Accounts has data on first launch
        seedAccounts(accountManager);

        String title = "BANK ACCOUNT MANAGEMENT - MAIN MENU";
        int width = title.length() + 6; // padding on each side
        String topBottom = "═".repeat(width - 2);

        Scanner scanner = new Scanner(System.in);
        boolean programRunning = true;

        while (programRunning) {
            System.out.println("\n╔" + topBottom + "╗");
            System.out.println("║  " + title + "  ║");
            System.out.println("╚" + topBottom + "╝");

            System.out.println("\n1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Process Transaction");
            System.out.println("4. View Transactions history");
            System.out.println("5. Exit\n");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1: {
                    System.out.println("\nACCOUNT CREATION");
                    System.out.println("─────────────────────────────────────────────\n");

                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter customer age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // discard leftover newline

                    System.out.print("Enter customer contact: ");
                    String contact = scanner.nextLine();

                    System.out.print("Enter customer address: ");
                    String address = scanner.nextLine();

                    System.out.println("\nCustomer type:");
                    System.out.println("1. Regular Customer (Standard banking services)");
                    System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
                    System.out.print("\nSelect type (1-2): ");
                    int customerType = scanner.nextInt();
                    scanner.nextLine();

                    Customer customer;
                    if (customerType == 2) {
                        customer = new PremiumCustomer(name, age, contact, address);
                    } else {
                        customer = new RegularCustomer(name, age, contact, address);
                    }

                    System.out.println("\nAccount type:");
                    System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
                    System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
                    System.out.print("\nSelect type (1-2): ");
                    int accountType = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("\nEnter initial deposit amount: $");
                    double initialDeposit = scanner.nextDouble();
                    scanner.nextLine();

                    Account account;
                    if (accountType == 2) {
                        account = new CheckingAccount(customer, initialDeposit);
                    } else {
                        account = new SavingsAccount(customer, initialDeposit);
                    }

                    boolean added = accountManager.addAccount(account);
                    if (!added) {
                        System.out.println("\nAccount storage is full. Could not create account.");
                        break;
                    }

                    System.out.println("\n[OK] Account created successfully!");
                    account.displayAccountDetails();

                    System.out.print("\nPress Enter to continue... ");
                    scanner.nextLine();
                    break;
                }
                case 2:
                    accountManager.viewAllAccounts();
                    System.out.println("\nTotal Accounts: " + accountManager.getAccountCount());
                    System.out.printf("Total Bank Balance: $%,.2f%n", accountManager.getTotalBalance());
                    System.out.print("\nPress Enter to continue... ");
                    scanner.nextLine();
                    break;
                case 3: {
                    System.out.println("\nPROCESS TRANSACTION");
                    System.out.println("─────────────────────────────────────────────\n");

                    System.out.print("Enter Account Number: ");
                    String accountNumber = scanner.nextLine();

                    Account account = accountManager.findAccount(accountNumber);
                    if (account == null) {
                        System.out.println("\nError: Account not found. Please check the number and try again.");
                        System.out.print("\nPress Enter to continue... ");
                        scanner.nextLine();
                        break;
                    }

                    System.out.println("\nAccount Details:");
                    System.out.println("Customer: " + account.getCustomer().getName());
                    System.out.println("Account Type: " + account.getAccountType());
                    System.out.printf("Current Balance: $%,.2f%n", account.getBalance());

                    System.out.println("\nTransaction type:");
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdrawal");
                    System.out.print("\nSelect type (1-2): ");
                    int transactionType = scanner.nextInt();
                    scanner.nextLine();

                    String type;
                    if (transactionType == 2) {
                        type = "Withdrawal";
                    } else {
                        type = "Deposit";
                    }

                    System.out.print("\nEnter amount: $");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    double previousBalance = account.getBalance();
                    boolean success = account.processTransaction(amount, type);
                    if (!success) {
                        System.out.println(
                                "\nTransaction Failed: the amount is invalid or the balance rules were not met.");
                        System.out.printf("Current balance: $%,.2f%n", account.getBalance());
                        System.out.print("\nPress Enter to continue... ");
                        scanner.nextLine();
                        break;
                    }

                    double newBalance = account.getBalance();
                    Transaction transaction = new Transaction(account.getAccountNumber(), type, amount, newBalance);

                    System.out.println("\nTRANSACTION CONFIRMATION");
                    System.out.println("─────────────────────────────────────────────");
                    System.out.println("Transaction ID: " + transaction.getTransactionId());
                    System.out.println("Account: " + account.getAccountNumber());
                    System.out.println("Type: " + type.toUpperCase());
                    System.out.printf("Amount: $%,.2f%n", amount);
                    System.out.printf("Previous Balance: $%,.2f%n", previousBalance);
                    System.out.printf("New Balance: $%,.2f%n", newBalance);
                    System.out.println("Date/Time: " + transaction.getTimestamp());
                    System.out.println("─────────────────────────────────────────────");

                    System.out.print("\nConfirm transaction? (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        transactionManager.addTransaction(transaction);
                        System.out.println("\n[OK] Transaction completed successfully!");
                    } else {
                        // undo the balance change; the transaction is not recorded
                        if (type.equalsIgnoreCase("Deposit")) {
                            account.withdraw(amount);
                        } else {
                            account.deposit(amount);
                        }
                        System.out.println("\nTransaction cancelled. No changes were saved.");
                    }

                    System.out.print("\nPress Enter to continue... ");
                    scanner.nextLine();
                    break;
                }
                case 4: {
                    System.out.println("\nVIEW TRANSACTION HISTORY");
                    System.out.println("─────────────────────────────────────────────\n");

                    System.out.print("Enter Account Number: ");
                    String accountNumber = scanner.nextLine();

                    Account account = accountManager.findAccount(accountNumber);
                    if (account == null) {
                        System.out.println("\nError: Account not found. Please check the number and try again.");
                        System.out.print("\nPress Enter to continue... ");
                        scanner.nextLine();
                        break;
                    }

                    System.out.println(
                            "\nAccount: " + account.getAccountNumber() + " - " + account.getCustomer().getName());
                    System.out.println("Account Type: " + account.getAccountType());
                    System.out.printf("Current Balance: $%,.2f%n", account.getBalance());

                    double totalDeposits = transactionManager.calculateTotalDeposits(account.getAccountNumber());
                    double totalWithdrawals = transactionManager.calculateTotalWithdrawals(account.getAccountNumber());
                    boolean hasTransactions = totalDeposits > 0 || totalWithdrawals > 0;

                    if (!hasTransactions) {
                        System.out.println();
                        transactionManager.viewTransactionsByAccount(account.getAccountNumber());
                    } else {
                        System.out.println("\nTRANSACTION HISTORY");
                        transactionManager.viewTransactionsByAccount(account.getAccountNumber());

                        double netChange = totalDeposits - totalWithdrawals;
                        String sign;
                        if (netChange >= 0) {
                            sign = "+";
                        } else {
                            sign = "-";
                        }
                        System.out.println();
                        System.out.printf("Total Deposits: $%,.2f%n", totalDeposits);
                        System.out.printf("Total Withdrawals: $%,.2f%n", totalWithdrawals);
                        System.out.printf("Net Change: %s$%,.2f%n", sign, Math.abs(netChange));
                    }

                    System.out.print("\nPress Enter to continue... ");
                    scanner.nextLine();
                    break;
                }
                case 5:
                    programRunning = false;
                    System.out.println("\nThank you for using Bank Account Management System!\n");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Bootstrap: five demo accounts (3 Savings, 2 Checking)
    private static void seedAccounts(AccountManager accountManager) {
        RegularCustomer customer1 = new RegularCustomer("John Smith", 35, "+1-555-0101", "12 Elm Street, Springfield");
        SavingsAccount account1 = new SavingsAccount(customer1, 5250);
        accountManager.addAccount(account1);

        RegularCustomer customer2 = new RegularCustomer("Sarah Johnson", 29, "+1-555-0102", "48 Oak Avenue, Springfield");
        CheckingAccount account2 = new CheckingAccount(customer2, 3450);
        accountManager.addAccount(account2);

        RegularCustomer customer3 = new RegularCustomer("Michael Chen", 41, "+1-555-0103", "7 Pine Road, Springfield");
        SavingsAccount account3 = new SavingsAccount(customer3, 15750);
        accountManager.addAccount(account3);

        RegularCustomer customer4 = new RegularCustomer("Emily Brown", 33, "+1-555-0104", "90 Maple Lane, Springfield");
        CheckingAccount account4 = new CheckingAccount(customer4, 890);
        accountManager.addAccount(account4);

        RegularCustomer customer5 = new RegularCustomer("David Wilson", 52, "+1-555-0105", "23 Birch Court, Springfield");
        SavingsAccount account5 = new SavingsAccount(customer5, 25300);
        accountManager.addAccount(account5);
    }

}
