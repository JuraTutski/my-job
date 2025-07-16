package org.example;


public class Triangle {
    public static double area(double a, double b, double c){
        if (a <= 0 || b <= 0 || c <= 0)
            throw new IllegalArgumentException("Стороны должны быть положительными");
        double s = (a + b + c) /2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));

    }
}