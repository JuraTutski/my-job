
public class Main {
    public static void main(String[] args){
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

        for(Cat cat : cats){
            cat.eat(bowl, 10);
        }
        bowl.info();

        System.out.println("\n ---- Сытость котов ----");
        for (Cat cat : cats){
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


    }
}






