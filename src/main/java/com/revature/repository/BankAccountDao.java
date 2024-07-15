package com.revature.repository;

import com.revature.entity.BankAccount;

import java.util.List;

public interface BankAccountDao {

    void createBankAccount(BankAccount account);
    void deleteBankAccount(BankAccount account);
    List<BankAccount> getAllBankAccount();
    List<BankAccount> getAllUserBankAccount(String username);
    void updateAccount(BankAccount account);
    BankAccount getBankAccountByID(int account_id);
}
