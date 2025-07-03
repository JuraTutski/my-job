package org.example;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    void testFactorialPositive() {
        assertEquals(120, Factorial.calculate(5));
    }

    @Test
    void testFactorialZero() {
        assertEquals(1, Factorial.calculate(0));
    }

    @Test
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> Factorial.calculate(-3));
    }
}