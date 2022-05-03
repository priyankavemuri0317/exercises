package org.bank;

import com.bank.bo.Account;
import com.bank.bo.Customer;
import com.bank.dao.BankDAO;
import org.junit.Test;

import java.util.Scanner;

import static com.bank.dao.DaoFactory.getBankDAO;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {

        assertTrue(true);
    }

    @Test
    public void accountCreatedByCustomer() {

        String customerId = "23";
        Customer customer = new Customer();
        customer.setCustomerId(Long.valueOf(customerId));
        BankDAO bankDAO = getBankDAO();

        Customer customer1 = bankDAO.viewCustomer(customer);
        assertNotNull(customer1);

        assertEquals("priya", customer1.getCustomerFirstName());

        Account account = new Account();
        account.setCustomerId(customer1.getCustomerId());
        account.setBalanceAmount(new Double(1000));
        account.setAccountType("1");

        bankDAO.addAccount(customer1, account, null);
        assertNotNull(account.getAccountId());
    }

}
