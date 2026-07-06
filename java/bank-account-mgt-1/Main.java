import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();
        TransactionManager transactionManager = new TransactionManager();

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

                    System.out.println("\n✓ Account created successfully!");
                    account.displayAccountDetails();

                    System.out.print("\nPress Enter to continue... ");
                    scanner.nextLine();
                    break;
                }
                case 2:
                    accountManager.viewAllAccounts();
                    break;
                case 3:
                    System.out.println("Not yet implemented");
                    break;
                case 4:
                    System.out.println("Not yet implemented");
                    break;
                case 5:
                    programRunning = false;
                    System.out.println("\nThank you for using Bank Account Management System!\n");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
