
public class Main {
    public static void main(String[] args) {
        String[][] array = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "X", "12"},
                {"13", "14", "15", "16"}
        };
        try {
            int result = ArrayProcessor.processArray(array);
            System.out.println("Сумма элементов массива: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Дополнительно: пример ошибки выхода за границы массива
        try {
            int[] nums = {1, 2, 3};
            System.out.println(nums[5]); // ошибка
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка индекса: " + e.getMessage());
        }
    }
}