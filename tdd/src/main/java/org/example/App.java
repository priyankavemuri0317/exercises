package org.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        String UserName = "priyankavemuri";
        String UserName1 = "priyanka vemuri";
        boolean oo =  isValidUserName(UserName1);
        System.out.println(oo);

    }
    public static  boolean isValidUserName(String name) {
        if (name.contains("/") || name.contains("#") || name.contains(" ") || name.contains("@")) {
            return false;
        }
        return true;
    }
}

