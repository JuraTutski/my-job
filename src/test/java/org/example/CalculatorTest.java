package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CalculatorTest {

    @Test
    void testadd(){
        assertEquals(7,Calculator.add(3, 4));
    }
    @Test
    void testSubtract(){
        assertEquals(1, Calculator.subtract(5, 4));
    }
    @Test
    void testMultiply(){
        assertEquals(20, Calculator.multiply(4, 5));
    }
    @Test
    void testDivide(){
        assertEquals(2.5, Calculator.divide(5, 2), 0.001);
    }
    @Test
    void tesrDivideByZero(){
        assertThrows(ArithmeticException.class, () -> Calculator.divide(10, 0));

    }
}