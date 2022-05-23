package com.bank.service;


import com.bank.bo.Account;
import com.bank.bo.Customer;
import com.bank.bo.Transaction;
import com.bank.bo.User;
import com.bank.dao.BankDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.bank.dao.DaoFactory.getBankDAO;

public class UserService {
    public static void userOperations() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter User ID");
        String userid = in.nextLine();

        User user = new User();
        user.setUserId(Long.valueOf(userid));
        BankDAO bankDAO = getBankDAO();

        if (bankDAO.viewUser(user) == null) {
            System.out.println("Invalid User Id");
        } else {
            while (true) {
                System.out.println("Please select the following option");

                System.out.println("1) Add Customer Account");
                System.out.println("2) View Customer Account(s)");
                //System.out.println("3) Add New User");
                //System.out.println("4) Update Existing User");
                //System.out.println("5) Delete Existing User");
                System.out.println("Press any key to Quit");
                String index = in.nextLine();


                if (index.equals("1")) {
                    addAccount(user);
                } else if (index.equals("2")) {
                    viewAccount(user);
                } else if (index.equals("3")) {
                    addUser();
                } else if (index.equals("4")) {
                    updateUser();
                } else if (index.equals("5")) {
                    deleteUser();
                } else {
                    System.out.println("Invalid option");
                    break;
                }
            }
        }
    }

    public static void addUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter First Name:");
        String fName = in.nextLine();
        System.out.println("Enter Last Name:");
        String lName = in.nextLine();

        User us = new User();
        us.setFirstName(fName);
        us.setLastName(lName);

        BankDAO bankDAO = getBankDAO();
        bankDAO.addUser(us);

    }

    public static void updateUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter User Id:");
        String userId = in.nextLine();
        System.out.println("Enter First Name:");
        String fName = in.nextLine();
        System.out.println("Enter Last Name:");
        String lName = in.nextLine();

        User us = new User();
        us.setUserId(Long.valueOf(userId));
        us.setFirstName(fName);
        us.setLastName(lName);

        BankDAO bankDAO = getBankDAO();
        bankDAO.updateUser(us);

    }

    public static void deleteUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("User Id:");
        String userId = in.nextLine();

        User us = new User();
        us.setUserId(Long.valueOf(userId));

        BankDAO bankDAO = getBankDAO();
        bankDAO.deleteUser(us);
    }

    public static void addAccount(User user) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter Customer First Name");
        String fName = in.nextLine();
        System.out.println("Enter Customer Last Name");
        String lName = in.nextLine();
        System.out.println("Enter Balance Amount");
        String amount = in.nextLine();
        System.out.println("Enter Account Type");
        System.out.println("1) Current");
        System.out.println("2) Saving");
        String accountType = in.nextLine();

        Customer customer = new Customer();
        customer.setCustomerFirstName(fName);
        customer.setCustomerLastName(lName);

        Account account = new Account();
        account.setBalanceAmount(Double.valueOf(amount));
        account.setAccountType(accountType);
        BankDAO bankDAO = getBankDAO();
        bankDAO.addAccount(customer, account, user);
    }

    public static void viewAccount(User user) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Customer Id");
        String customerId = in.nextLine();

        Customer customer = new Customer();
        customer.setCustomerId(Long.valueOf(customerId));
        BankDAO bankDAO = getBankDAO();

        if (bankDAO.viewCustomer(customer) == null) {
            System.out.println("Invalid Customer Id");
        } else {
            List accounts = bankDAO.viewAllAccount(customer);
            if (accounts == null || accounts.isEmpty()) {
                System.out.println("No Accounts exists");
            } else {
                Map accountsMap = new HashMap();
                for (int i = 0; i < accounts.size(); i++) {
                    Account account1 = (Account) accounts.get(i);
                    accountsMap.put(account1.getAccountId(), account1);
                    System.out.println(account1);
                }

                System.out.println("Please select following options");
                System.out.println("1) Validate account(s)");
                System.out.println("2) View account transactions");
                System.out.println("Press any key to Quit");

                String confirmStr = in.nextLine();
                if ("1".equals(confirmStr)) {

                    String accountId = null;
                    String status = null;
                    while (true) {
                        System.out.println("Enter Account Id");
                        accountId = in.nextLine();

                        Account account1 = (Account) accountsMap.get(Long.valueOf(accountId));
                        if (account1 == null) {
                            System.out.println("Entered Account Id is not exist, Do you want select another account? (Yes or No).");
                            confirmStr = in.nextLine();
                            if (!"Yes".equals(confirmStr)) {
                                return;
                            }
                        } else {
                            System.out.println("Do you want to enable or reject ? (E or R)");
                            status = in.nextLine();
                            if ("E".equals(status) && status.equals(account1.getAccountStatus())) {
                                System.out.println("Entered Account Id is already enabled, Do you want select another account? (Yes or No).");
                                confirmStr = in.nextLine();
                                if (!"Yes".equals(confirmStr)) {
                                    return;
                                }
                            } else if ("R".equals(status) && status.equals(account1.getAccountStatus())) {
                                System.out.println("Entered Account Id is already rejected, Do you want select another account? (Yes or No).");
                                confirmStr = in.nextLine();
                                if (!"Yes".equals(confirmStr)) {
                                    return;
                                }
                            } else if ("R".equals(status) && "E".equals(status)) {
                                System.out.println("Invalid option");
                                return;
                            } else {
                                break;
                            }
                        }
                    }

                    Account account = new Account();
                    account.setCustomerId(customer.getCustomerId());
                    account.setAccountId(Long.valueOf(accountId));
                    account.setAccountStatus(status);
                    bankDAO.updateAccount(account);
                } else if ("2".equals(confirmStr)) {
                    while (true) {
                        System.out.println("Enter Account Id");
                        String accountId = in.nextLine();

                        Account account1 = (Account) accountsMap.get(Long.valueOf(accountId));
                        if (account1 == null) {
                            System.out.println("Entered Account Id is not exist, Do you want select another account? (Yes or No).");
                            confirmStr = in.nextLine();
                            if (!"Yes".equals(confirmStr)) {
                                return;
                            }
                        }

                        Account account = new Account();
                        account.setCustomerId(customer.getCustomerId());
                        account.setAccountId(Long.valueOf(accountId));
                        List transactions = bankDAO.viewAccountTransactions(account);

                        if (transactions == null || transactions.isEmpty()) {
                            System.out.println("No transactions exists");
                        } else {
                            for (int i = 0; i < transactions.size(); i++) {
                                Transaction transaction = (Transaction) transactions.get(i);
                                System.out.println(transaction.toString());
                            }
                        }
                        break;
                    }
                } else {
                    System.out.println("Invalid option");
                    return;
                }
            }
        }
    }
}
