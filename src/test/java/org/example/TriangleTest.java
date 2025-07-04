package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TriangleTest {

    @Test
    public void testAreaCorrect(){
        double result = Triangle.area(3, 4, 5);
        Assert.assertEquals( result, 6.0, 0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativeSides() {
        Triangle.area(-1, 4, 5);
    }

}
