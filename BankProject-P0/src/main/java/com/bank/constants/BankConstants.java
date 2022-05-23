package com.bank.constants;

import java.util.HashMap;
import java.util.Map;

public class BankConstants {
    public static Map status = new HashMap();
    public static Map accountType = new HashMap();
    public static Map transactionType = new HashMap();


    static {
        status.put("E", "Enabled");
        status.put("P", "Pending");
        status.put("R", "Rejected");

        accountType.put("1", "Current");
        accountType.put("2", "Saving");

        transactionType.put("D", "Deposited");
        transactionType.put("W", "Withdrawn");
    }
}
