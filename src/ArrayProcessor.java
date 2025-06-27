public class ArrayProcessor {
    public static int processArray(String[][] array) throws MyArraySizeException, MyArrayDataException{
        if (array.length != 4){
          throw new MyArraySizeException("Неверное количество строк: " + array.length);
        }
        for (int i = 0; i < array.length; i++){
            if (array[i].length != 4 ){
                throw new MyArraySizeException("Неверное количество столбцов в строке " + i + ": "
                      + array[i].length);


            }

        }
        int sum = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e){
                    throw new MyArrayDataException("Неверные данные в ячейке [" + i + "][" + j + "]: "
                           + array[i][j]);
                }
            }
        }
        return sum;


    }
}
