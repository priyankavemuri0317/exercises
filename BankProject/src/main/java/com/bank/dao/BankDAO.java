package com.bank.dao;

import com.bank.bo.Account;
import com.bank.bo.Customer;
import com.bank.bo.User;

import java.util.List;

public interface BankDAO {
    User viewUser(User user);

    void addUser(User us);

    void updateUser(User us);

    void deleteUser(User us);

    void addAccount(Customer customer, Account account, User user);

    Customer viewCustomer(Customer customer);

    List viewAllAccount(Customer customer);

    List viewAccount(Account account);

    List viewAccountByAccountId(Account account);

    void depositToAccount(Account account);

    void withDrawFromAccount(Account account);

    void updateAccount(Account account);

    void transferToAccount(Account fromAccount, Account toAccount);

    void receiveFromAccount(Account fromAccount, Account toAccount);

    List viewAccountTransactions(Account account);
}
