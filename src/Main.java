//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

        public static void main(String[] args) {
            printThreeWords();                      // 1
            checkSumSign();                         // 2
            printColor();                           // 3
            compareNumbers();                       // 4

            // 5 — Проверка суммы
            System.out.println("Сумма от 10 до 20? " + isSumBetween10And20(10, 10));

            // 6 — Вывод знака числа
            printNumberSign(5);
            printNumberSign(-3);

            // 7 — Проверка отрицательного числа
            System.out.println("Число -1 отрицательное? " + isNegative(-1));
            System.out.println("Число 3 отрицательное? " + isNegative(3));

            // 8 — Печать строки № раз
            printStringMultipleTimes("Привет", 3);

            // 9 — Проверка высокосного года
            System.out.println("Год 2024 високосный? " + isLeapYear(2024));
            System.out.println("Год 1900 високосный? " + isLeapYear(1900));
            System.out.println("Год 2000 високосный? " + isLeapYear(2000));

            // 10 — Инверсия массива
            invertArray();

            // 11 — Заполнение массива от 1 до 100
            fillArray();

            // 12 — Умножение чисел < 6 на 2
            multiplyLessThanSix();

            // 13 — Диагонали в двумерном массиве
            fillDiagonal();

            // 14 — Создание массива заданной длины и значений
            int[] customArray = createArray(5, 7);
            printArray(customArray);
        }

        // 1
        public static void printThreeWords() {
            System.out.println("Orange");
            System.out.println("Banana");
            System.out.println("Apple");
        }

        // 2
        public static void checkSumSign() {
            int a = -5;
            int b = 10;
            int sum = a + b;
            if (sum >= 0) {
                System.out.println("Сумма положительная");
            } else {
                System.out.println("Сумма отрицательная");
            }
        }

        // 3
        public static void printColor() {
            int value = 42;
            if (value <= 0) {
                System.out.println("Красный");
            } else if (value <= 100) {
                System.out.println("Жёлтый");
            } else {
                System.out.println("Зелёный");
            }
        }

        // 4
        public static void compareNumbers() {
            int a = 5;
            int b = 7;
            if (a >= b) {
                System.out.println("a >= b");
            } else {
                System.out.println("a < b");
            }
        }

        // 5
        public static boolean isSumBetween10And20(int a, int b) {
            int sum = a + b;
            return sum >= 10 && sum <= 20;
        }

        // 6
        public static void printNumberSign(int number) {
            if (number >= 0) {
                System.out.println("Число положительное");
            } else {
                System.out.println("Число отрицательное");
            }
        }

        // 7
        public static boolean isNegative(int number) {
            return number < 0;
        }

        // 8
        public static void printStringMultipleTimes(String str, int times) {
            for (int i = 0; i < times; i++) {
                System.out.println(str);
            }
        }

        // 9
        public static boolean isLeapYear(int year) {
            if (year % 400 == 0) {
                return true;
            } else if (year % 100 == 0) {
                return false;
            } else {
                return year % 4 == 0;
            }
        }

        // 10
        public static void invertArray() {
            int[] arr = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i] == 0 ? 1 : 0;
            }
            printArray(arr);
        }

        // 11
        public static void fillArray() {
            int[] arr = new int[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i + 1;
            }
            printArray(arr);
        }

        // 12
        public static void multiplyLessThanSix() {
            int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] < 6) {
                    arr[i] *= 2;
                }
            }
            printArray(arr);
        }

        // 13
        public static void fillDiagonal() {
            int n = 5;
            int[][] arr = new int[n][n];
            for (int i = 0; i < n; i++) {
                arr[i][i] = 1;
            }
            print2DArray(arr);
        }

        // 14
        public static int[] createArray(int len, int initialValue) {
            int[] arr = new int[len];
            for (int i = 0; i < len; i++) {
                arr[i] = initialValue;
            }
            return arr;
        }
        // Вспомогательный метод для вывода одномерного масива
        public static void printArray(int[] arr) {
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
    // Вспомагательный метод для вывода двумерного массива
    public static void print2DArray(int[][] arr) {
        for (int[] row : arr) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}

