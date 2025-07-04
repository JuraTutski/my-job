package org.example;


import org.testng.Assert;
import org.testng.annotations.Test;

public class FactorialTest {

    @Test
    public void testFactorialPositive() {
        Assert.assertEquals( Factorial.calculate(5),120L);
    }

    @Test
    public void testFactorialZero() {
        Assert.assertEquals( Factorial.calculate(0), 1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFactorialNegative() {
        Factorial.calculate(-3);
    }
}