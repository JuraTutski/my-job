package org.example;


public class App {
    public static void main(String[] args){
        System.out.println("=== Демонстрация четырёх программ ===");


        // 1. Factorial

        int number = 5;
        long factorial = Factorial.calculate(number);
        System.out.println("Факториал числа " + number + ": " + factorial);

        // 2. Triangle

        double a = 3.0, b = 4.0, c = 5.0;
        double area = Triangle.area(a, b, c);
        System.out.println("Площадь треугольника со сторонами 3, 4, 5: " + area);

        // 3. Calculator

        int x = 10, y = 2;
        System.out.println("Сложение: " + x + " + " + y + " = " + Calculator.add(x, y));
        System.out.println("Вычитание: " + x + " - " + y + " = " + Calculator.subtract(x, y));
        System.out.println("Умножение: " + x + " * " + y + " = " + Calculator.multiply(x, y));
        System.out.println("Деление: " + x + " / " + y + " = " + Calculator.divide(x, y));

        // 4. Comparer

        int first = 7, second = 9;
        String result = Comparer.compare(first, second);
        System.out.println("Сравнение чисел " + first + " и " + second + ": " + result);
    }

}



