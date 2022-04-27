package org.example;

import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        System.out.println( "Hello World!" );
       Connection c = null;
       try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
                   .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "admin");
           System.out.println("Opened database successfully");


           Scanner my = new Scanner(System.in);
           System.out.println("enter your name");
           String name = my.nextLine();

           System.out.println("enter your age");
           String age = my.nextLine();


           System.out.println("enter your housetype");
           String housetype = my.nextLine();

           System.out.println("enter your city");
           String city = my.nextLine();

           String insertqueary = "insert into dbo.customer(customerid,\"name\",age,housetype,city) values (default, ?,?,?,?)";
           PreparedStatement ps = c.prepareStatement(insertqueary);
           ps.setString(1,name);
           ps.setInt(2,Integer.valueOf(age));
           ps.setString(3,housetype);
           ps.setString(4,city);
           int a = ps.executeUpdate();
          System.out.println( "insertedrows " +a);

           Statement s = c.createStatement();
           System.out.println("enter customerId");
           String  customerId = my.nextLine();
          String quary = "select * from dbo.customer where customerId = "+ customerId;
          ResultSet rs = s.executeQuery(quary );
          while (rs.next()) {
              System.out.println( "name "+ rs.getString("name" ));
              System.out.println( "customerid "+ rs.getLong("customerid" ));
              System.out.println( "age "+ rs.getInt("age" ));
              System.out.println( "housetype "+ rs.getString("housetype" ));
              System.out.println( "city "+ rs.getString("city" ));
          }


           System.out.println("enter customer id for delete");
           String deletequery = my.nextLine();
           String deletequary = "delete from dbo.customer where  customerId="+deletequery;
           s.executeUpdate(deletequary);

           c.close();
       } catch (Exception  e){
           e.printStackTrace();
        }

    }

}
