package com.revature.controller;

import com.revature.entity.BankAccount;
import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.exception.ValidateFail;
import com.revature.service.BankAccountService;
import com.revature.service.UserService;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserController {

    private Scanner scanner;
    private UserService userService;
    private BankAccountService bankAccountService;
    private Map<String, String> controlMap;

    public UserController(Scanner scanner, UserService userService, BankAccountService bankAccountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    public void promptBankAccountCreation(Map<String, String> controlMap) {
        printOutAllUserAccount(controlMap);
        System.out.println("y. Yes");
        System.out.println("n. No");
        System.out.print("Would you like to make a bank account? ");
        String userMakeBankAccount = scanner.nextLine();
        switch (userMakeBankAccount) {
            case "y" :
                createBankAccount(controlMap);
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("\nBank Account created\n");
                break;
            case "n" :
                System.out.println("You did not make Bank Account");
                break;
            default:
                System.out.println("GoodBye!");
                controlMap.put("Continue Loop", "false");
                break;
        }
    }

    public void promptBankAccountDeletion(Map<String, String> controlMap) {
        printOutAllUserAccount(controlMap);
        System.out.println("Are you sure you want to delete your account?");
        System.out.println("c. Confirm Deletion");
        System.out.println("w. No, I want to keep my account");
        String user = scanner.nextLine();
        switch (user) {
            case "c":
                deleteBankAccount(controlMap);
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("\n Bank Account was deleted \n");
                break;
            case "w" :
                System.out.println("You did not delete your bank account");
                break;

            default:
                System.out.println("GoodBye!");
                controlMap.put("Continue Loop", "false");
                break;
        }
    }

    public void promptBankingMenu(Map<String, String> controlMap) {
        System.out.printf("\nBanking stuff for %s can happen here!\n\n", controlMap.get("User"));
        printOutAllUserAccount(controlMap);
        System.out.println("c. Create a Bank Account");
        System.out.println("d. Delete a Bank Account");
        System.out.println("m. Deposit");
        System.out.println("w. Withdraw");
        System.out.println("o. Logout");
        System.out.println("Would would you like to do?");
        String userAction2 = scanner.nextLine();
        switch (userAction2) {
            case "c":
//                            printOutAllUserAccount(controlMap);
                promptBankAccountCreation(controlMap);
                break;


            case "d":
//                            printOutAllUserAccount(controlMap);
                promptBankAccountDeletion(controlMap);
                break;

            case "m":
                printOutAllUserAccount(controlMap);
                System.out.print("Deposit amount: ");
                double deposit = scanner.nextDouble();
                System.out.print("Account ID: ");
                int accountId = scanner.nextInt();
                scanner.nextLine();
                deposit(accountId, deposit);
                break;

            case "w":
                printOutAllUserAccount(controlMap);
                System.out.print("Withdraw amount: ");
                double withdraw = scanner.nextDouble();
                System.out.print("Account ID: ");
                int accountId2 = scanner.nextInt();
                scanner.nextLine();
                withdraw(accountId2, withdraw);
                break;
            case "o":
                logout(controlMap);
        }
    }

    public void promptUserForService(Map<String,String> controlMap) {

        System.out.println("1. Make a new account");
        System.out.println("2. Login");
        System.out.println(("q. quit"));
        System.out.print("What would you like to do? ");

        try {
            String userAction = scanner.nextLine();
            switch (userAction) {
                case "1":
                    registerUser();
                    break;

                case "2":
                    controlMap.put("User", login().getUsername());
                    System.out.printf("\nBanking stuff for %s can happen here!\n\n", controlMap.get("User"));
                    printOutAllUserAccount(controlMap);
                    System.out.println("c. Create a Bank Account");
                    System.out.println("d. Delete a Bank Account");
                    System.out.println("m. Deposit money into Account");
                    System.out.println("w. Withdraw money from Account");
                    System.out.println("o. Logout");
                    System.out.println();
                    System.out.print("What would you like to do: ");
                    String userAction2 = scanner.nextLine();
                    switch (userAction2) {
                        case "c":
//                            printOutAllUserAccount(controlMap);
                            promptBankAccountCreation(controlMap);
                            break;


                        case "d":
//                            printOutAllUserAccount(controlMap);
                            promptBankAccountDeletion(controlMap);
                            break;

                        case "m":
                            printOutAllUserAccount(controlMap);
                            System.out.print("Deposit amount: ");
                            double deposit = scanner.nextDouble();
                            System.out.print("Account ID: ");
                            int accountId = scanner.nextInt();
                            scanner.nextLine();
                            deposit(accountId, deposit);
                            break;

                        case "w":
                            printOutAllUserAccount(controlMap);
                            System.out.print("Withdraw amount: ");
                            double withdraw = scanner.nextDouble();
                            System.out.print("Account ID: ");
                            int accountId2 = scanner.nextInt();
                            scanner.nextLine();
                            withdraw(accountId2, withdraw);
                            break;

                        case "o":
                            logout(controlMap);
                    }

//                    promptBankingMenu(controlMap);
                    break;

                case "q":
                    System.out.println("GoodBye!");
                    controlMap.put("Continue Loop", "false");
            }
        } catch (LoginFail e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerUser() {
        try {
            User newCredentials = getUserCredentials();
            User newUser = userService.validate(newCredentials);
            System.out.printf("New account created:  %s  \n", newUser);
        } catch (ValidateFail e) {
            System.out.println("Sorry Username has already taken. Please try again \n");
        }
    }




    public void createBankAccount(Map<String, String> controlMap) {
        String username = controlMap.get("User");
        try {
            bankAccountService.makeBankAccount(username, 0);
        } catch (LoginFail e) {
            System.out.println("Failed to make a bank account");
        }
    }

    public void deposit(int account_id, double amount) {
        BankAccount account = bankAccountService.getBankAccountById(account_id);
        double balance = account.getBalance();
        double total = balance + amount;
        try {
            bankAccountService.updateAccount(account_id, total);
            System.out.printf("Deposited: %.2f into Account ID: %d Total Balance: %.2f \n", amount, account_id, total);

        } catch (LoginFail e) {
            System.out.println("Failed to updateAccount");
        }
    }

    public void withdraw(int account_id, double amount) {
        BankAccount account = bankAccountService.getBankAccountById(account_id);
        double balance = account.getBalance();
        if ((balance - amount) >= 0) {
            double total = balance - amount;
            try {
                bankAccountService.updateAccount(account_id, total);
                System.out.printf("Withdraw: %.2f into Account ID: %d Total Balance: %.2f \n", amount, account_id, total);
            } catch (LoginFail e) {
                System.out.println("Failed to withdraw");
            }
        } else {
            System.out.println("You can not withdraw more than what you have poor boy");
        }
    }


    public void printOutAllUserAccount(Map<String, String> controlMap) {
        String username = controlMap.get("User");
        List<BankAccount> allAccounts = bankAccountService.getAllUserBankAccount(username);
        for (BankAccount account : allAccounts) {
            System.out.println("\nAll of your bank accounts located here: ");
            System.out.printf("Account ID: %d has Balance of: %.2f \n", account.getAccountID(), account.getBalance());
            System.out.println();
        }

    }

    public void deleteBankAccount(Map<String, String> controlMap) {
        String username = controlMap.get("User");
        System.out.println("What is the Account ID you are trying to delete");
        int account_id = scanner.nextInt();
        try {
            bankAccountService.deleteBankAccount(account_id, username);

        } catch (LoginFail e) {
            System.out.println("Failed to delete bank account");
        }
    }


    public User login() {
        return userService.checkLogin(getUserCredentials());
    }


    public void logout(Map<String, String> controlMap) {
        controlMap.remove("User");
        System.out.println("\nYou have logged out\n");
        System.out.println("--------------------------------------------------------------------------");
    }


    public User getUserCredentials() {
        String newUsername;
        String newPassword;
        System.out.print("Please enter a username: ");
        newUsername = scanner.nextLine();
        System.out.print("Please enter a password: ");
        newPassword = scanner.nextLine();
        return new User(newUsername, newPassword);
    }
}