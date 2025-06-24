//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1.
        Product product1 = new Product(
                "Андроид",
                "2025-06-15",
                "Samsung",
                "Южная Корея",
                46999.99,
                true
        );
        System.out.println("Одиночный товар: ");
        product1.printInfo();

        // 2.
        Product[] productsArray = new Product[5];


        productsArray[0] = new Product("Samsung S25 Ultra", "01.02.2025",
                "Samsung Corp.", "Korea", 5599, true);
        productsArray[1] = new Product("iPhone 16 Pro Max", "10.03.2025",
                "Apple Inc.", "США", 7999.00, false);
        productsArray[2] = new Product("Xiaomi Note 14", "15.01.2025",
                "Xiaomi", "Китай", 25999.50, true);
        productsArray[3] = new Product("Samsung Galaxy", "10.03.2025",
                "Samsung Corp.", "Корея", 56999.99, true);
        productsArray[4] = new Product("Lenovo IdeaPad 5", "20.04.2025",
                "Lenovo Group Ltd.", "Китай", 42999.99, false);


        System.out.println(" Массив товаров: ");
        for (int i = 0; i < productsArray.length; i++) {
            productsArray[i].printInfo();
        }



        // 3. Аттракционы

        System.out.println(" Информация об аттракционах: ");

        Park myPark = new Park();
        Park.Attraction attraction1 = myPark.new Attraction("Карусель", "11:00 - 21:00",
                100);
        Park.Attraction attraction2 = myPark.new Attraction("Качели", "10:00 - 20:00",
                50);
        Park.Attraction attraction3 = myPark.new Attraction("Американские горки",
                "11:00 - 20:00", 200);

        attraction1.displayInfo();
        attraction2.displayInfo();
        attraction3.displayInfo();



    }

}
