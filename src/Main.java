
public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog("Жужа");
        Cat cat1 = new Cat("Белка");
        Cat cat2 = new Cat("Стрелка");

        System.out.println("---- Бег и плавание ----");
        dog.run(400);
        dog.swim(5);

        cat1.run(150);
        cat1.swim(3);


        System.out.println("\n ----- Кормление котов -----");
        Bowl bowl = new Bowl(15);
        Cat[] cats = {cat1, cat2};

        for (Cat cat : cats) {
            cat.eat(bowl, 10);
        }
        bowl.info();

        System.out.println("\n ---- Сытость котов ----");
        for (Cat cat : cats) {
            System.out.println(cat.name + " сыта? " + cat.isFull());
        }

        System.out.println("\n ---- Добавим еды и покормим ещё ----");
        bowl.addFood(10);
        cat2.eat(bowl, 10);
        bowl.info();

        System.out.println("\n ---- Статистика ----");
        System.out.println("Всего животных: " + Animal.getAnimalCount());
        System.out.println("Котов: " + Cat.getCatCount());
        System.out.println("Собак: " + Dog.getDogCount());




        // 2

        System.out.println();
        System.out.println("------ Расчёт геометрических фигур ------");

        Shape circle = new Circle(5);
        circle.setFillColor("Красный");
        circle.setBorderColor("Чёрный");

        Shape rectangle = new Rectangle(4, 6);
        rectangle.setFillColor("Синий");
        rectangle.setBorderColor("Зелёный");

        Shape triangle = new Triangle(3, 4, 5);
        triangle.setFillColor("Жёлтый");
        triangle.setBorderColor("Оранжевый");

        Shape[] shapes = {circle, rectangle, triangle};

        for (Shape shape : shapes) {
            System.out.println("Фигура: " + shape.getClass().getSimpleName());
            System.out.printf("Периметр: %.2f\n", shape.getPerimeter());
            System.out.printf("Площадь: %.2f\n", shape.getArea());
            System.out.println("Цвет заливки: " + shape.getFillColor());
            System.out.println("Цвет границы: " + shape.getBorderColor());
            System.out.println("----------------------------");
        }


    }
}






