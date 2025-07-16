package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TriangleTest {

    @Test
    void testAreaCorrect(){
        double result = Triangle.area(3, 4, 5);
        assertEquals(6.0, result, 0.001);
    }

    @Test
    void testNegativeSides(){
        assertThrows(IllegalArgumentException.class, () -> Triangle.area(-1, 4, 5));

    }

}