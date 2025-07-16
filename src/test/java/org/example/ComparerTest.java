package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComparerTest {

    @Test
    void testFirstGreater(){
        assertEquals("Первое число больше", Comparer.compare(10, 5));
    }
    @Test
    void testSecondGreater(){
        assertEquals("Второе число больше", Comparer.compare(2, 9));
    }
    @Test
    void testEqual(){
        assertEquals("Числа равны", Comparer.compare(7, 7));
    }
}