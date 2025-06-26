public class Dog extends Animal {
    private static int dogCount = 0;
    private static final int MAX_RUN = 500;
    private static final int MAX_SWIM = 10;

    public Dog(String name){
        super(name);
        dogCount++;


    }
    @Override
    public void run(int distance){
        if(distance <= MAX_RUN){
            System.out.println(name + " пробежала " + distance + " м.");
        }

    }
    @Override
    public void swim(int distance){
        if(distance <= MAX_SWIM){
            System.out.println(name + " проплыла " + distance + " м.");
        } else {
            System.out.println(name + " не может проплыть " + distance + " м.");

        }
    }

    public static int getDogCount(){
        return dogCount;
    }
}

