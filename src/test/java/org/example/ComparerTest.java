package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ComparerTest {

    @Test
    public void testFirstGreater() {
        Assert.assertEquals(Comparer.compare(10, 5), "Первое число больше");
    }

    @Test
    public void testSecondGreater() {
        Assert.assertEquals(Comparer.compare(2, 9), "Второе число больше");
    }

    @Test
    public void testEqual() {
        Assert.assertEquals(Comparer.compare(7, 7), "Числа равны");
    }
}