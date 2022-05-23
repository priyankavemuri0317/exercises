package com.erms.dao;

// this class doesn't seem super useful because we only have one Dao, but with multiple daos, its use becomes apparent
// multiple methods to return different daos:
// we only need 1 dao factory for the entire application
public class DaoFactory {
    private static ErmsDao ermsDAO;

    // private constructor, intentionally disallow instantiation of this class:
    private DaoFactory() {

    }

    // check if null, return the dao
    public static ErmsDao getBankDAO() {
        if (ermsDAO == null) {
            ermsDAO = new ErmsDaoImpl();
        }
        return ermsDAO;
    }


}
