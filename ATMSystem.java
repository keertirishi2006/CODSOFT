import java.util.Scanner;

class BankAccount {
    private double balance;

    // Constructor to initialize the account balance
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("✅ Successfully deposited $" + amount);
        } else {
            System.out.println("❌ Deposit amount must be positive.");
        }
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("✅ Successfully withdrew $" + amount);
        } else if (amount > balance) {
            System.out.println("❌ Insufficient balance for this withdrawal.");
        } else {
            System.out.println("❌ Withdrawal amount must be positive.");
        }
    }

    // Method to check balance
    public double getBalance() {
        return balance;
    }
}

class ATM {
    private BankAccount account;

    // Constructor to connect the ATM to a BankAccount
    public ATM(BankAccount account) {
        this.account = account;
    }

    // Method to display the ATM menu and handle user input
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("🏦 Welcome to the ATM!");

        while (!exit) {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("💰 Current Balance: $" + String.format("%.2f", account.getBalance()));
                    break;
                case 2:
                    System.out.print("Enter the amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter the amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.println("👋 Thank you for using the ATM. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}

public class ATMSystem {
    public static void main(String[] args) {
        // Initialize the user's bank account with an initial balance
        BankAccount userAccount = new BankAccount(1000.00); // Initial balance is $1000

        // Create an ATM instance and link it to the user's bank account
        ATM atm = new ATM(userAccount);

        // Start the ATM interface
        atm.start();
    }
}
