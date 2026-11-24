import java.util.*;

class Account {
    String accountNumber;
    String name;
    String pin;
    int balance;

    Stack<String> transactions;

    Account(String accountNumber, String name,
            String pin, int balance) {

        this.accountNumber = accountNumber;
        this.name = name;
        this.pin = pin;
        this.balance = balance;

        transactions = new Stack<>();

        transactions.push("Account opened with balance: Rs. "
                + balance);
    }
}

public class ATM_Simulator {

    // ARRAY: Store bank accounts
    static Account[] accounts = {
        new Account("1001", "Sachin", "1234", 10000),
        new Account("1002", "Rahul", "2345", 15000),
        new Account("1003", "Amit", "3456", 20000),
        new Account("1004", "Rohit", "4567", 12000)
    };

    // QUEUE: Manage waiting customers
    static Queue<String> customerQueue =
            new LinkedList<>();

    static Scanner sc = new Scanner(System.in);

    // SEARCHING: Find account using account number
    static Account searchAccount(String accountNumber) {

        for (int i = 0; i < accounts.length; i++) {

            if (accounts[i].accountNumber.equals(accountNumber)) {
                return accounts[i];
            }
        }

        return null;
    }

    // PIN verification
    static Account login() {

        System.out.println("\n===== ATM LOGIN =====");

        System.out.print("Enter Account Number: ");
        String accountNumber = sc.nextLine();

        Account account = searchAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return null;
        }

        System.out.print("Enter ATM PIN: ");
        String enteredPin = sc.nextLine();

        if (account.pin.equals(enteredPin)) {
            System.out.println("\nLogin Successful!");
            System.out.println("Welcome, " + account.name);
            return account;
        } else {
            System.out.println("Incorrect PIN!");
            return null;
        }
    }

    // Check balance
    static void checkBalance(Account account) {

        System.out.println("\nAvailable Balance: Rs. "
                + account.balance);
    }

    // GREEDY ALGORITHM: Cash withdrawal
    static void withdrawMoney(Account account) {

        System.out.print("\nEnter withdrawal amount: Rs. ");
        int amount = sc.nextInt();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount > account.balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        int remaining = amount;

        // Currency denominations in descending order
        int[] notes = {500, 200, 100, 50, 20, 10, 5, 1};

        System.out.println("\nCash Dispensed:");

        for (int note : notes) {

            int count = remaining / note;

            if (count > 0) {

                System.out.println("Rs. " + note
                        + " x " + count);

                remaining = remaining % note;
            }
        }

        account.balance -= amount;

        account.transactions.push(
                "Withdrawn: Rs. " + amount);

        System.out.println("\nWithdrawal Successful!");
        System.out.println("Remaining Balance: Rs. "
                + account.balance);
    }

    // Deposit money
    static void depositMoney(Account account) {

        System.out.print("\nEnter deposit amount: Rs. ");
        int amount = sc.nextInt();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        account.balance += amount;

        account.transactions.push(
                "Deposited: Rs. " + amount);

        System.out.println("Money Deposited Successfully!");
        System.out.println("New Balance: Rs. "
                + account.balance);
    }

    // STACK: Display transaction history
    static void miniStatement(Account account) {

        System.out.println("\n===== MINI STATEMENT =====");

        if (account.transactions.empty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Account Holder: " + account.name);

        System.out.println("Recent Transactions:");

        for (int i = account.transactions.size() - 1;
             i >= 0; i--) {

            System.out.println(account.transactions.get(i));
        }
    }

    // QUEUE: Add customer
    static void addCustomer() {

        System.out.print("\nEnter account number: ");
        String accountNumber = sc.nextLine();

        if (searchAccount(accountNumber) != null) {

            customerQueue.offer(accountNumber);

            System.out.println(
                    "Customer added to waiting queue!");

            System.out.println("Queue Position: "
                    + customerQueue.size());

        } else {
            System.out.println("Account not found!");
        }
    }

    // QUEUE: Serve next customer
    static void serveCustomer() {

        if (customerQueue.isEmpty()) {

            System.out.println("\nNo customers waiting!");

        } else {

            String nextCustomer = customerQueue.poll();

            Account account = searchAccount(nextCustomer);

            System.out.println("\nNow Serving Customer:");
            System.out.println("Account Number: "
                    + nextCustomer);
            System.out.println("Customer Name: "
                    + account.name);
        }
    }

    // Display ATM menu
    static void atmMenu(Account account) {

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("          ATM SIMULATOR");
            System.out.println("================================");

            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Mini Statement");
            System.out.println("5. Add Customer to Queue");
            System.out.println("6. Serve Next Customer");
            System.out.println("7. Logout");

            System.out.print("\nEnter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    checkBalance(account);
                    break;

                case 2:
                    withdrawMoney(account);
                    break;

                case 3:
                    depositMoney(account);
                    break;

                case 4:
                    miniStatement(account);
                    break;

                case 5:
                    addCustomer();
                    break;

                case 6:
                    serveCustomer();
                    break;

                case 7:
                    System.out.println(
                            "Thank you for using our ATM!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice! Try again.");
            }

        } while (choice != 7);
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("********************************");
        System.out.println("       WELCOME TO ATM");
        System.out.println("********************************");

        while (true) {

            Account account = login();

            if (account != null) {
                atmMenu(account);
            }

            System.out.print(
                    "\nDo you want another login? (yes/no): ");

            String answer = sc.nextLine();

            if (!answer.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("\nATM Session Ended.");
        sc.close();
    }
}