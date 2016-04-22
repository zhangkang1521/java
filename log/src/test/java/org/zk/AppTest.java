package org.zk;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        App.printLogA();
    }

    public void testBigDecimal(){
//        BigDecimal money = new BigDecimal("123.567");
//        BigDecimal result = money.multiply(BigDecimal.valueOf(100)).setScale(0) ;
//        System.out.println(result);
        String omoney = ("0");
        System.out.println(omoney);
    }
}
