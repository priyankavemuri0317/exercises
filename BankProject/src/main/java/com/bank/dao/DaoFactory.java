package com.bank.dao;

// this class doesn't seem super useful because we only have one Dao, but with multiple daos, its use becomes apparent
// multiple methods to return different daos:
// we only need 1 dao factory for the entire application
public class DaoFactory {
    private static BankDAO bankDAO;

    // private constructor, intentionally disallow instantiation of this class:
    private DaoFactory() {

    }

    // check if null, return the dao
    public static BankDAO getBankDAO() {
        if (bankDAO == null) {
            bankDAO = new BaseDAOImpl();
        }
        return bankDAO;
    }


}
