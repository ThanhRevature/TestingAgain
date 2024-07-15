package com.revature.entity;

import java.io.Serializable;
import java.util.Objects;

public class BankAccount implements Serializable {

    private String accountOwner;

    private double balance;

    private int accountId;

    public BankAccount() {
    }

    public BankAccount(String accountOwner) {
        this.accountOwner = accountOwner;
    }
    public BankAccount(int accountId) {
        this.accountId = accountId;
    }
    public BankAccount(String accountOwner, double balance, int accountId) {
        this.accountOwner = accountOwner;
        this.balance = balance;
        this.accountId = accountId;

    }

    public BankAccount(int accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public BankAccount(int accountId, String accountOwner) {
        this.accountId = accountId;
        this.accountOwner = accountOwner;
    }

    public BankAccount(String accountOwner, double balance) {
        this.accountOwner = accountOwner;
        this.balance = balance;
    }

    public String getAccountOwner() {
        return accountOwner;

    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountID() {
        return accountId;
    }

    public double deposit(double amount) {
        balance += amount;
        return balance;
    }

    public double withdraw(double amount) {
        if ((balance - amount) >= 0) {
            balance -= amount;
            return balance;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Double.compare(balance, that.balance) == 0 && Objects.equals(accountOwner, that.accountOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountOwner, balance);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountOwner='" + accountOwner + '\'' +
                ", balance=" + balance +
                '}';
    }
}