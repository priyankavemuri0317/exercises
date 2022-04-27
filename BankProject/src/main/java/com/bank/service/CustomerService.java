package com.bank.service;

import com.bank.bo.Account;
import com.bank.bo.Customer;
import com.bank.dao.BankDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.bank.dao.DaoFactory.getBankDAO;

public class CustomerService {
    public static void customerOperations() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Customer Id");
        String customerId = in.nextLine();

        Customer customer = new Customer();
        customer.setCustomerId(Long.valueOf(customerId));
        BankDAO bankDAO = getBankDAO();

        if (bankDAO.viewCustomer(customer) == null) {
            System.out.println("Invalid Customer Id");
        } else {
            while (true) {
                System.out.println("Please select the following option");
                System.out.println("1) Add Account");
                System.out.println("2) View Account");
                System.out.println("3) Withdraw funds from Account");
                System.out.println("4) Deposit funds to Account");
                System.out.println("5) Transfer to another account");
                System.out.println("6) Receive to another account");
                System.out.println("Press any key to Quit");
                String index = in.nextLine();
                if (index.equals("1")) {
                    addAccount(customer);
                } else if (index.equals("2")) {
                    viewAccount(customer);
                } else if (index.equals("3")) {
                    withdrawMoneyFromAccount(customer);
                } else if (index.equals("4")) {
                    depositMoneyToAccount(customer);
                } else if (index.equals("5")) {
                    transferMoneyToAccount(customer);
                } else if (index.equals("6")) {
                    receiveMoneyToAccount(customer);
                } else {
                    System.out.println("Quit");
                    break;
                }
            }
        }
    }

    public static void addAccount(Customer customer) {

        Scanner in = new Scanner(System.in);
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());

        while (true) {
            try {
                System.out.println("Enter Balance Amount");
                String amount = in.nextLine();
                Double amDouble = Double.parseDouble(amount);
                if (amDouble > 0.0) {
                    account.setBalanceAmount(amDouble);
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Enter Account Type");
        System.out.println("1) Current");
        System.out.println("2) Saving");
        String accountType = in.nextLine();
        account.setAccountType(accountType);

        BankDAO bankDAO = getBankDAO();
        bankDAO.addAccount(customer, account, null);
    }

    public static void viewAccount(Customer customer) {
        BankDAO bankDAO = getBankDAO();
        List accounts = bankDAO.viewAllAccount(customer);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            Map accountsMap = new HashMap();
            for (int i = 0; i < accounts.size(); i++) {
                Account account1 = (Account) accounts.get(i);
                accountsMap.put(account1.getAccountId(), account1);
                System.out.println("Account Id: " + account1.getAccountId());
            }

            String accountId = null;
            while (true) {
                System.out.println("Enter Account Id");
                Scanner in = new Scanner(System.in);
                accountId = in.nextLine();

                Account account1 = (Account) accountsMap.get(Long.valueOf(accountId));
                if (account1 == null || !"E".equals(account1.getAccountStatus())) {
                    System.out.println("Entered Account Id is not exist or enabled, Do you want select another account? (Yes or No).");
                    String confirmStr = in.nextLine();
                    if (!"Yes".equals(confirmStr)) {
                        return;
                    }
                } else {
                    break;
                }
            }

            Account account = new Account();
            account.setCustomerId(customer.getCustomerId());
            account.setAccountId(Long.valueOf(accountId));
            accounts = bankDAO.viewAccount(account);
            if (accounts == null || accounts.isEmpty()) {
                System.out.println("Account Id: " + accountId + " is not valid");
            } else {
                for (int i = 0; i < accounts.size(); i++) {
                    Account account2 = (Account) accounts.get(i);
                    System.out.println(account2.toString());
                }
            }
        }
    }

    public static void depositMoneyToAccount(Customer customer) {
        BankDAO bankDAO = getBankDAO();
        List accounts = bankDAO.viewAllAccount(customer);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                Account account1 = (Account) accounts.get(i);
                System.out.println(account1.toString());
            }

            Scanner in = new Scanner(System.in);
            System.out.println("Enter Account Id for deposit money.");
            String accountId = in.nextLine();

            Account account = new Account();
            account.setCustomerId(customer.getCustomerId());
            account.setAccountId(Long.valueOf(accountId));
            while (true) {
                try {
                    System.out.println("Enter Amount > $0.0 for deposit");
                    String amount = in.nextLine();
                    Double amDouble = Double.parseDouble(amount);
                    if (amDouble > 0.0) {
                        account.setBalanceAmount(amDouble);
                        break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            bankDAO.depositToAccount(account);
        }
    }

    public static void withdrawMoneyFromAccount(Customer customer) {
        BankDAO bankDAO = getBankDAO();
        List accounts = bankDAO.viewAllAccount(customer);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                Account account1 = (Account) accounts.get(i);
                System.out.println(account1.toString());
            }
            Scanner in = new Scanner(System.in);
            System.out.println("Enter Account Id for withdraw money.");
            String accountId = in.nextLine();

            Account account = new Account();
            account.setCustomerId(customer.getCustomerId());
            account.setAccountId(Long.valueOf(accountId));
            while (true) {
                try {
                    System.out.println("Enter Amount > $0.0 for withdraw");
                    String amount = in.nextLine();
                    Double amDouble = Double.parseDouble(amount);
                    if (amDouble > 0.0) {
                        account.setBalanceAmount(amDouble);
                        break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            bankDAO.withDrawFromAccount(account);
        }
    }

    public static void transferMoneyToAccount(Customer customer) {
        BankDAO bankDAO = getBankDAO();
        List accounts = bankDAO.viewAllAccount(customer);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                Account account1 = (Account) accounts.get(i);
                System.out.println(account1.toString());
            }

            Scanner in = new Scanner(System.in);
            System.out.println("Enter your Account Id to transfer money.");
            String fromAccountId = in.nextLine();

            System.out.println("Enter Account Id to receive money.");
            String toAccountId = in.nextLine();

            Account toAccount = new Account();
            toAccount.setAccountId(Long.valueOf(toAccountId));

            Account fromAccount = new Account();
            fromAccount.setCustomerId(customer.getCustomerId());
            fromAccount.setAccountId(Long.valueOf(fromAccountId));

            while (true) {
                try {
                    System.out.println("Enter Amount > $0.0 to transfer");
                    String amount = in.nextLine();
                    Double amDouble = Double.parseDouble(amount);
                    if (amDouble > 0.0) {
                        fromAccount.setBalanceAmount(amDouble);
                        break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            bankDAO.transferToAccount(fromAccount, toAccount);

        }
    }

    public static void receiveMoneyToAccount(Customer customer) {
        BankDAO bankDAO = getBankDAO();
        List accounts = bankDAO.viewAllAccount(customer);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No Accounts exists");
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                Account account1 = (Account) accounts.get(i);
                System.out.println(account1.toString());
            }

            Scanner in = new Scanner(System.in);
            System.out.println("Enter your Account Id to receive money.");
            String toAccountId = in.nextLine();

            System.out.println("Enter Account Id to get money.");
            String fromAccountId = in.nextLine();

            Account fromAccount = new Account();
            fromAccount.setAccountId(Long.valueOf(fromAccountId));

            Account toAccount = new Account();
            toAccount.setCustomerId(customer.getCustomerId());
            toAccount.setAccountId(Long.valueOf(toAccountId));

            while (true) {
                try {
                    System.out.println("Enter Amount > $0.0 to receive");
                    String amount = in.nextLine();
                    Double amDouble = Double.parseDouble(amount);
                    if (amDouble > 0.0) {
                        fromAccount.setBalanceAmount(amDouble);
                        break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            bankDAO.receiveFromAccount(fromAccount, toAccount);
        }
    }

}
