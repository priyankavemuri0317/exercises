package org.example;
import org.example.App.*;

import static org.example.App.isValidUserName;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String userName = "Priyankavemuri";
        String userName1 = "priyanka vemuri";
        String userName2 = "priyanka@123";
        String userName3 = "priyanka_123";
        String userName4 = "priyanka/vemuri";
        String userName5 = "priyanka#vemuri";
        assertTrue(isValidUserName(userName));
        assertFalse(isValidUserName(userName1));
        assertFalse(isValidUserName(userName2));
        assertTrue(isValidUserName(userName3));
        assertFalse(isValidUserName(userName4));
        assertFalse(isValidUserName(userName5));

    }





}

