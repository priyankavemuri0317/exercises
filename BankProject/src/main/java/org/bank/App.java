package org.bank;

import com.bank.service.CustomerService;
import com.bank.service.UserService;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Scanner in = new Scanner(System.in);
        System.out.println("Are you User or Customer?");
        String index = in.nextLine();
        if (index.equals("User")) {
            System.out.println("Valid User option");
            UserService.userOperations();
        } else if (index.equals("Customer")) {
            System.out.println("Valid Customer option");
            CustomerService.customerOperations();
        } else {
            System.out.println("inValid option");
        }
    }
}
