package com.bank.dao;

import com.bank.bo.Account;
import com.bank.bo.Customer;
import com.bank.bo.Transaction;
import com.bank.bo.User;
import com.bank.constants.BankConstants;
import org.postgresql.util.PGmoney;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAOImpl implements BankDAO {

    private final String tranInsertQuery = "insert into dbo.transactiondata (TransactionId, AccountID, TransactionType, TransactionAmount, BalanceAmount, CustomerId, UserId, ModifiedDate) values (default, ?, ?, ?, ?, ?, ?, current_timestamp)";
    private Connection connection = null;


    public BaseDAOImpl() {
        connection = ConnectionFactory.getConnection();
    }

    public User viewUser(User user) {

        User user1 = null;
        String viewCustomer = "select userid, firstname, lastname from dbo.userdata where userid = ? order by userid";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(viewCustomer);
            preparedStatement.setLong(1, user.getUserId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                preparedStatement = connection.prepareStatement(viewCustomer);
                user1 = new User();
                user1.setUserId(rs.getLong(1));
                user1.setFirstName(rs.getString(2));
                user1.setLastName(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user1;
    }

    public void addUser(User us) {
        String query = "insert into dbo.userdata (userid, firstname, lastname) values (default, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, us.getFirstName());
            preparedStatement.setString(2, us.getLastName());
            int inserted = preparedStatement.executeUpdate();
            System.out.println("inserted !" + inserted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User us) {
        String query = "update dbo.userdata set firstname = ? , lastname = ? where userid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, us.getFirstName());
            preparedStatement.setString(2, us.getLastName());
            preparedStatement.setLong(3, us.getUserId());
            int inserted = preparedStatement.executeUpdate();
            System.out.println("updated !" + inserted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(User us) {
        String deletequery = "delete from dbo.userdata where  customerId=" + us.getUserId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deletequery);
            int deleted = preparedStatement.executeUpdate();
            System.out.println("deleted !" + deleted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAccount(Customer customer, Account account, User user) {
        String addCustomer = "insert into dbo.customerdata (customerid, firstname, lastname) values (default, ?, ?)";
        String addAccount = "insert into dbo.account (AccountId, BalanceAmount, CustomerId, AccountType, AccountStatus) values (default, ?, ?, ?, 'P')";
        try {

            if (customer.getCustomerId() == null) {
                PreparedStatement preparedStatement = connection.prepareStatement(addCustomer, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, customer.getCustomerFirstName());
                preparedStatement.setString(2, customer.getCustomerLastName());
                int added = preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    Long customerId = rs.getLong(1);
                    System.out.println("added new Customer, Customer Id : " + customerId);

                    customer.setCustomerId(customerId);
                    preparedStatement = connection.prepareStatement(addAccount, Statement.RETURN_GENERATED_KEYS);

                    PGmoney pGmoney = new PGmoney(account.getBalanceAmount());

                    preparedStatement.setObject(1, pGmoney);
                    preparedStatement.setLong(2, customer.getCustomerId());
                    preparedStatement.setString(3, account.getAccountType());
                    int addedAccount = preparedStatement.executeUpdate();
                    rs = preparedStatement.getGeneratedKeys();
                    if (rs.next()) {
                        Long accountId = rs.getLong(1);
                        System.out.println("added new account, Account Id : " + accountId);
                        preparedStatement = connection.prepareStatement(tranInsertQuery);
                        preparedStatement.setLong(1, accountId);
                        preparedStatement.setString(2, "D");
                        preparedStatement.setObject(3, pGmoney);
                        preparedStatement.setObject(4, pGmoney);
                        preparedStatement.setNull(5, Types.INTEGER);
                        preparedStatement.setLong(6, user.getUserId());
                        addedAccount = preparedStatement.executeUpdate();
                    }
                }
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(addAccount, Statement.RETURN_GENERATED_KEYS);
                PGmoney pGmoney = new PGmoney(account.getBalanceAmount());
                preparedStatement.setObject(1, pGmoney);

                preparedStatement.setLong(2, customer.getCustomerId());
                preparedStatement.setString(3, account.getAccountType());
                int addedAccount = preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    Long accountId = rs.getLong(1);
                    System.out.println("added new account, Account Id : " + accountId);
                    preparedStatement = connection.prepareStatement(tranInsertQuery);
                    preparedStatement.setLong(1, accountId);
                    preparedStatement.setString(2, "D");
                    preparedStatement.setObject(3, pGmoney);
                    preparedStatement.setObject(4, pGmoney);
                    preparedStatement.setLong(5, customer.getCustomerId());
                    preparedStatement.setNull(6, Types.INTEGER);

                    addedAccount = preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer viewCustomer(Customer customer) {

        Customer customer1 = null;
        String viewCustomer = "select customerid, firstname, lastname from dbo.customerdata where customerid = ? order by customerid";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(viewCustomer);
            preparedStatement.setLong(1, customer.getCustomerId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                preparedStatement = connection.prepareStatement(viewCustomer);
                customer1 = new Customer();
                customer1.setCustomerId(rs.getLong(1));
                customer1.setCustomerFirstName(rs.getString(2));
                customer1.setCustomerLastName(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer1;
    }

    public List viewAllAccount(Customer customer) {
        List accounts = new ArrayList();
        String viewAccount = "select AccountId, BalanceAmount::numeric, AccountType, AccountStatus, CustomerId from dbo.account where customerid = ? order by AccountId";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(viewAccount);
            preparedStatement.setLong(1, customer.getCustomerId());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account1 = new Account();
                account1.setAccountId(rs.getLong(1));
                account1.setBalanceAmount(rs.getDouble(2));
                account1.setAccountType(rs.getString(3));
                account1.setAccountStatus(rs.getString(4));
                account1.setCustomerId(rs.getLong(5));
                accounts.add(account1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public List viewAccount(Account account) {
        List accounts = new ArrayList();
        String viewAccount = "select AccountId, BalanceAmount::numeric, AccountType, AccountStatus, CustomerId from dbo.account where customerid = ? and accountID = ? order by AccountId";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(viewAccount);
            preparedStatement.setLong(1, account.getCustomerId());
            preparedStatement.setLong(2, account.getAccountId());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account1 = new Account();
                account1.setAccountId(rs.getLong(1));
                account1.setBalanceAmount(rs.getDouble(2));
                account1.setAccountType(rs.getString(3));
                account1.setAccountStatus(rs.getString(4));
                account1.setCustomerId(rs.getLong(5));
                accounts.add(account1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public List viewAccountByAccountId(Account account) {
        List accounts = new ArrayList();
        String viewAccount = "select AccountId, BalanceAmount::numeric, AccountType, AccountStatus, CustomerId from dbo.account where accountID = ? order by AccountId";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(viewAccount);
            preparedStatement.setLong(1, account.getAccountId());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account1 = new Account();
                account1.setAccountId(rs.getLong(1));
                account1.setBalanceAmount(rs.getDouble(2));
                account1.setAccountType(rs.getString(3));
                account1.setAccountStatus(rs.getString(4));
                account1.setCustomerId(rs.getLong(5));
                accounts.add(account1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public void depositToAccount(Account account) {
        List accounts = viewAccount(account);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            Account account1 = (Account) accounts.get(0);
            if (!"E".equals(account1.getAccountStatus())) {
                System.out.println("Account Id: " + account1.getAccountId() + " is not enabled, you can not deposit money to this account.");
                return;
            }
            String updateAccount = "update dbo.account set BalanceAmount = ? where accountID = ?";
            try {
                Double amount = account1.getBalanceAmount();
                amount = amount + account.getBalanceAmount();
                PreparedStatement preparedStatement = connection.prepareStatement(updateAccount);
                PGmoney pGmoney = new PGmoney(amount);
                preparedStatement.setObject(1, pGmoney);
                preparedStatement.setLong(2, account.getAccountId());
                int updated = preparedStatement.executeUpdate();
                System.out.println(account.getBalanceAmount() + " deposited to account id " + account.getAccountId());

                preparedStatement = connection.prepareStatement(tranInsertQuery);
                preparedStatement.setLong(1, account.getAccountId());
                preparedStatement.setString(2, "D");
                PGmoney pGmoney1 = new PGmoney(account.getBalanceAmount());
                preparedStatement.setObject(3, pGmoney1);
                preparedStatement.setObject(4, pGmoney);
                preparedStatement.setLong(5, account.getCustomerId());
                preparedStatement.setNull(6, Types.INTEGER);
                updated = preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void withDrawFromAccount(Account account) {
        List accounts = viewAccount(account);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            Account account1 = (Account) accounts.get(0);
            if (!"E".equals(account1.getAccountStatus())) {
                System.out.println("Account Id: " + account1.getAccountId() + " is not enabled, you can not withdraw money from this account.");
                return;
            }

            String updateAccount = "update dbo.account set BalanceAmount = ? where accountID = ?";
            try {
                Double amount = account1.getBalanceAmount();

                if (amount >= account.getBalanceAmount()) {
                    amount = amount - account.getBalanceAmount();
                    PreparedStatement preparedStatement = connection.prepareStatement(updateAccount);
                    PGmoney pGmoney = new PGmoney(amount);
                    preparedStatement.setObject(1, pGmoney);
                    preparedStatement.setLong(2, account.getAccountId());
                    int updated = preparedStatement.executeUpdate();
                    System.out.println(account.getBalanceAmount() + " withdrawn from account id " + account.getAccountId());

                    preparedStatement = connection.prepareStatement(tranInsertQuery);
                    preparedStatement.setLong(1, account.getAccountId());
                    preparedStatement.setString(2, "W");
                    PGmoney pGmoney1 = new PGmoney(account.getBalanceAmount());
                    preparedStatement.setObject(3, pGmoney1);
                    preparedStatement.setObject(4, pGmoney);
                    preparedStatement.setLong(5, account.getCustomerId());
                    preparedStatement.setNull(6, Types.INTEGER);
                    updated = preparedStatement.executeUpdate();

                } else {
                    System.out.println(" We can not withdrawn amount, low balance in account " + account.getAccountId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAccount(Account account) {
        String updateAccount = "update dbo.account set AccountStatus = ? where accountID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateAccount);
            preparedStatement.setString(1, account.getAccountStatus());
            preparedStatement.setLong(2, account.getAccountId());
            int updated = preparedStatement.executeUpdate();
            System.out.println("Account status has been updated to " + BankConstants.status.get(account.getAccountStatus()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transferToAccount(Account fromAccount, Account toAccount) {
        List accounts = viewAccount(fromAccount);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            Account fromAccount1 = (Account) accounts.get(0);
            if (!"E".equals(fromAccount1.getAccountStatus())) {
                System.out.println("Account Id: " + fromAccount1.getAccountId() + " is not enabled, you can not transfer money from this account.");
                return;
            } else if (fromAccount1.getBalanceAmount() < fromAccount.getBalanceAmount()) {
                System.out.println("Low balance in account in account Id :" + fromAccount1.getAccountId());
            }

            List toAccounts = viewAccountByAccountId(toAccount);
            Account toAccount1 = null;
            if (toAccounts == null || toAccounts.isEmpty()) {
                System.out.println("No Accounts exists");
            } else {
                toAccount1 = (Account) toAccounts.get(0);
                if (!"E".equals(toAccount1.getAccountStatus())) {
                    System.out.println("Account Id: " + toAccount1.getAccountId() + " is not enabled, you can not deposit money to this account.");
                    return;
                }
            }

            String updateAccount = "update dbo.account set BalanceAmount = ? where accountID = ?";
            try {
                Double amount = fromAccount1.getBalanceAmount();
                amount = amount - fromAccount.getBalanceAmount();

                PreparedStatement preparedStatement = connection.prepareStatement(updateAccount);
                PGmoney pGmoney = new PGmoney(amount);
                preparedStatement.setObject(1, pGmoney);
                preparedStatement.setLong(2, fromAccount1.getAccountId());
                int updated = preparedStatement.executeUpdate();

                // Logger Transaction
                preparedStatement = connection.prepareStatement(tranInsertQuery);
                preparedStatement.setLong(1, fromAccount1.getAccountId());
                preparedStatement.setString(2, "W");
                PGmoney pGmoney1 = new PGmoney(fromAccount.getBalanceAmount());
                preparedStatement.setObject(3, pGmoney1);
                preparedStatement.setObject(4, pGmoney);
                preparedStatement.setLong(5, fromAccount.getCustomerId());
                preparedStatement.setNull(6, Types.INTEGER);
                updated = preparedStatement.executeUpdate();

                amount = toAccount1.getBalanceAmount();
                amount = amount + fromAccount.getBalanceAmount();

                preparedStatement = connection.prepareStatement(updateAccount);
                pGmoney = new PGmoney(amount);
                preparedStatement.setObject(1, pGmoney);
                preparedStatement.setLong(2, toAccount1.getAccountId());
                updated = preparedStatement.executeUpdate();

                System.out.println(fromAccount.getBalanceAmount() + " transfer from account id " + fromAccount1.getAccountId() + "  to account id " + toAccount1.getAccountId());

                // Logger Transaction
                preparedStatement = connection.prepareStatement(tranInsertQuery);
                preparedStatement.setLong(1, toAccount1.getAccountId());
                preparedStatement.setString(2, "D");
                preparedStatement.setObject(3, pGmoney1);
                preparedStatement.setObject(4, pGmoney);
                preparedStatement.setLong(5, fromAccount.getCustomerId());
                preparedStatement.setNull(6, Types.INTEGER);
                updated = preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void receiveFromAccount(Account fromAccount, Account toAccount) {
        List accounts = viewAccountByAccountId(fromAccount);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            Account fromAccount1 = (Account) accounts.get(0);
            if (!"E".equals(fromAccount1.getAccountStatus())) {
                System.out.println("Account Id: " + fromAccount1.getAccountId() + " is not enabled, you can not get money from this account.");
                return;
            } else if (fromAccount1.getBalanceAmount() < fromAccount.getBalanceAmount()) {
                System.out.println("Low balance in account in account Id :" + fromAccount1.getAccountId());
            }

            List toAccounts = viewAccount(toAccount);
            Account toAccount1 = null;
            if (toAccounts == null || toAccounts.isEmpty()) {
                System.out.println("No Accounts exists");
            } else {
                toAccount1 = (Account) toAccounts.get(0);
                if (!"E".equals(toAccount1.getAccountStatus())) {
                    System.out.println("Account Id: " + toAccount1.getAccountId() + " is not enabled, you can not deposit money to this account.");
                    return;
                }
            }

            String updateAccount = "update dbo.account set BalanceAmount = ? where accountID = ?";
            try {
                Double amount = fromAccount1.getBalanceAmount();
                amount = amount - fromAccount.getBalanceAmount();

                PreparedStatement preparedStatement = connection.prepareStatement(updateAccount);
                PGmoney pGmoney = new PGmoney(amount);
                preparedStatement.setObject(1, pGmoney);
                preparedStatement.setLong(2, fromAccount1.getAccountId());
                int updated = preparedStatement.executeUpdate();

                // Logger Transaction
                preparedStatement = connection.prepareStatement(tranInsertQuery);
                preparedStatement.setLong(1, fromAccount1.getAccountId());
                preparedStatement.setString(2, "W");
                PGmoney pGmoney1 = new PGmoney(fromAccount.getBalanceAmount());
                preparedStatement.setObject(3, pGmoney1);
                preparedStatement.setObject(4, pGmoney);
                preparedStatement.setLong(5, toAccount.getCustomerId());
                preparedStatement.setNull(6, Types.INTEGER);
                updated = preparedStatement.executeUpdate();

                amount = toAccount1.getBalanceAmount();
                amount = amount + fromAccount.getBalanceAmount();

                preparedStatement = connection.prepareStatement(updateAccount);
                pGmoney = new PGmoney(amount);
                preparedStatement.setObject(1, pGmoney);
                preparedStatement.setLong(2, toAccount1.getAccountId());
                updated = preparedStatement.executeUpdate();

                System.out.println(fromAccount.getBalanceAmount() + " received from account id " + fromAccount1.getAccountId() + "  to account id " + toAccount1.getAccountId());

                // Logger Transaction
                preparedStatement = connection.prepareStatement(tranInsertQuery);
                preparedStatement.setLong(1, toAccount1.getAccountId());
                preparedStatement.setString(2, "D");
                pGmoney1 = new PGmoney(fromAccount.getBalanceAmount());
                preparedStatement.setObject(3, pGmoney1);
                preparedStatement.setObject(4, pGmoney);
                preparedStatement.setLong(5, toAccount.getCustomerId());
                preparedStatement.setNull(6, Types.INTEGER);
                updated = preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List viewAccountTransactions(Account account) {
        List transactions = new ArrayList();
        String viewAccount = "select TransactionId, AccountID, TransactionType, TransactionAmount::numeric, BalanceAmount::numeric, CustomerId, " +
                "UserId, ModifiedDate from dbo.transactiondata where accountID = ? order by TransactionId DESC";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(viewAccount);
            preparedStatement.setLong(1, account.getAccountId());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getLong(1));
                transaction.setAccountId(rs.getLong(2));
                transaction.setTransactionType(rs.getString(3));

                transaction.setTransactionAmount(rs.getDouble(4));
                transaction.setBalanceAmount(rs.getDouble(5));

                transaction.setCustomerId(rs.getLong(6));
                transaction.setUserId(rs.getLong(7));
                transaction.setModifiedDate(rs.getTimestamp(8));

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
