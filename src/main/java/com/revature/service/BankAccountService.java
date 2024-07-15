package com.revature.service;

import com.revature.entity.BankAccount;
import com.revature.exception.LoginFail;
import com.revature.repository.BankAccountDao;

import java.util.List;

public class BankAccountService {

    private BankAccountDao bankAccountDao;

    public BankAccountService(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }

    public BankAccount checkAccount(BankAccount account) {
        for (BankAccount accounts : bankAccountDao.getAllBankAccount()) {
            boolean usernameMatch = accounts.getAccountOwner().equals(account.getAccountOwner());
            if (usernameMatch) {
                return account;
            }
        }
        throw new LoginFail("Bank Account is invalid");

    }

    public void makeBankAccount(String username, double balance) {
            BankAccount newBankAccount = new BankAccount(username, balance);
        bankAccountDao.createBankAccount(newBankAccount);

    }

    public void deleteBankAccount(int account_id, String username) {
        BankAccount deleteAccount = new BankAccount(account_id, username);
        bankAccountDao.deleteBankAccount(deleteAccount);
    }

    public void updateAccount(int account_id, double balance) {
        BankAccount account = new BankAccount(account_id, balance);
        bankAccountDao.updateAccount(account);
    }

    public BankAccount getBankAccountById(int account_id) {
        return bankAccountDao.getBankAccountByID(account_id);

    }

    public List<BankAccount> getAllUserBankAccount(String username) {
        return bankAccountDao.getAllUserBankAccount(username);
    }
}
