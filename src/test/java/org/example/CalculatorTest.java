package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTest {

    @Test
    public void testAdd() {
        Assert.assertEquals(Calculator.add(3, 4), 7);
    }

    @Test
    public void testSubtract() {
        Assert.assertEquals(Calculator.subtract(5, 4), 1);
    }

    @Test
    public void testMultiply() {
        Assert.assertEquals(Calculator.multiply(4, 5), 20);
    }

    @Test
    public void testDivide() {
        Assert.assertEquals(Calculator.divide(5, 2), 2.5, 0.001);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        Calculator.divide(10, 0);
    }
}