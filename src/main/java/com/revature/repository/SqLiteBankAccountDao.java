package com.revature.repository;

import com.revature.entity.BankAccount;
import com.revature.exception.UserSqlException;
import com.revature.utility.DatabaseConnector;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqLiteBankAccountDao implements BankAccountDao {

    @Override
    public void createBankAccount(BankAccount account){
        String sql = "insert into bank_account (account_owner, balance) values (?, ?)";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getAccountOwner());
            preparedStatement.setDouble(2, account.getBalance());
//            preparedStatement.setInt(3, account.getAccountID());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return;
            }

            throw  new UserSqlException("Bank Account could not be created: Please try again");

        } catch (SQLException e) {
            throw new UserSqlException(e.getMessage());
        }

    }


    @Override
    public void updateAccount(BankAccount account) {
        String sql = "update bank_account set balance = ? where account_id = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, account.getAccountOwner());
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountID());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return;
            }

            throw  new UserSqlException("You did not updateAccount: Please try again");

        } catch (SQLException e) {
            throw new UserSqlException(e.getMessage());
        }
    }

    @Override
    public void deleteBankAccount(BankAccount account) {
        String sql = "delete from bank_account where account_id = ? and account_owner = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountID());
            preparedStatement.setString(2, account.getAccountOwner());


            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return;
            }

            throw  new UserSqlException("Bank Account could not be created: Please try again");

        } catch (SQLException e) {
            throw new UserSqlException(e.getMessage());
        }

    }

    public BankAccount getBankAccountByID(int account_id) {
        String sql = "select * from bank_account where account_id = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, account.getAccountOwner());
//            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setInt(1, account_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BankAccount account = new BankAccount();
                account.setAccountId(resultSet.getInt("account_id"));
                account.setAccountOwner(resultSet.getString("account_owner"));
                account.setBalance(resultSet.getDouble("balance"));
                return account;
            }

            throw  new UserSqlException("Could not retrieve account by ID");

        } catch (SQLException e) {
            throw new UserSqlException(e.getMessage());
        }
    }


    @Override
    public List<BankAccount> getAllUserBankAccount(String username) {
        String sql = "select * from bank_account where account_owner = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            List<BankAccount> bankAccounts = new ArrayList<>();
            while (resultSet.next()) {
                BankAccount accounts = new BankAccount();
                accounts.setAccountId(resultSet.getInt("account_id"));
                accounts.setAccountOwner(resultSet.getString("account_owner"));
                accounts.setBalance(resultSet.getDouble("balance"));

                bankAccounts.add(accounts);
            }
            return bankAccounts;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    @Override
    public List<BankAccount> getAllBankAccount() {
       String sql = "select * from bank_account";
        try (Connection connection = DatabaseConnector.createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<BankAccount> bankAccounts = new ArrayList<>();
            while (resultSet.next()) {
                BankAccount account = new BankAccount();
                account.setAccountId(resultSet.getInt("account_id"));
                account.setAccountOwner(resultSet.getString("account_owner"));
                account.setBalance(resultSet.getDouble("balance"));

                bankAccounts.add(account);
            }

            return bankAccounts;
        } catch (SQLException e) {
            throw new UserSqlException(e.getMessage());
        }
    }


}
