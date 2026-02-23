package BankManagementSystem;

import java.io.*;
import java.util.*;

public class BankSystem {

    static final String FILE_NAME = "accounts.txt";
    static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {

        loadAccounts();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Bank Management System =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Account");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount(sc);
                    break;

                case 2:
                    deposit(sc);
                    break;

                case 3:
                    withdraw(sc);
                    break;

                case 4:
                    viewAccount(sc);
                    break;

                case 5:
                    saveAccounts();
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }

    static void createAccount(Scanner sc) {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        accounts.add(new Account(accNo, name, balance));
        System.out.println("Account Created Successfully!");
    }

    static void deposit(Scanner sc) {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        for (Account acc : accounts) {
            if (acc.accountNumber == accNo) {
                acc.deposit(amount);
                System.out.println("Deposit Successful!");
                return;
            }
        }
        System.out.println("Account Not Found!");
    }

    static void withdraw(Scanner sc) {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        for (Account acc : accounts) {
            if (acc.accountNumber == accNo) {
                if (acc.withdraw(amount)) {
                    System.out.println("Withdrawal Successful!");
                } else {
                    System.out.println("Insufficient Balance!");
                }
                return;
            }
        }
        System.out.println("Account Not Found!");
    }

    static void viewAccount(Scanner sc) {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        for (Account acc : accounts) {
            if (acc.accountNumber == accNo) {
                System.out.println("Name: " + acc.name);
                System.out.println("Balance: " + acc.balance);
                return;
            }
        }
        System.out.println("Account Not Found!");
    }

    static void saveAccounts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Account acc : accounts) {
                pw.println(acc);
            }
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    static void loadAccounts() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                accounts.add(new Account(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Double.parseDouble(parts[2])
                ));
            }
        } catch (Exception e) {
            // File may not exist initially
        }
    }
}
