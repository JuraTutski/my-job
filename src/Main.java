
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){

        // ==== Студенты ====

        Set<Student> students = new HashSet<>();

        students.add(new Student("Анастасия", "A", 1, Arrays.asList(4, 3, 5)));
        students.add(new Student("Андрей", "B", 1,Arrays.asList(3, 2, 2)));
        students.add(new Student("Антон", "C", 2, Arrays.asList(4, 5, 5)));
        students.add(new Student("Аня","D", 3, Arrays.asList(2, 2, 2)));
        students.add(new Student("Виктория", "D", 4, Arrays.asList(4, 4, 5)));

        System.out.println("До удаления: ");
        printAll(students);

        removeLowGradeStudents(students);
        System.out.println("\nПосле удаления: ");
        printAll(students);

        upgradeStudents(students);
        System.out.println("\nПосле перевода на следующий курс: ");
        printAll(students);

        System.out.println("\nСтуденты на курсе 2, 3, 5:");
        printStudents(students, 2);
        printStudents(students, 3);
        printStudents(students, 5);



        // ==== № 2 Телефонный справочник  ====


        PhoneBook book = new PhoneBook();

        book.add("Петров", "255-55-55");
        book.add("Сидоров", "355-66-60");
        book.add("Сидоров", "455-50-50"); // второй Сидоров
        book.add("Иванов", "123-23-12");

        System.out.println("\nВсе записи: ");
        book.printAll();

        System.out.println("\nНомера Петрова: " + book.get("Петров"));
        System.out.println("Номера Сидорова: " + book.get("Сидоров"));
        System.out.println("Номера Иванова: " + book.get("Иванов"));


    }
    // ==== Методы работы со студентами ====


    public static void removeLowGradeStudents(Set<Student> students){
        students.removeIf(student -> student.getAverageGrade() < 3);
    }
    public static void upgradeStudents(Set<Student> students){
        for (Student student : students){
            if (student.getAverageGrade() >= 3){
                student.setCourse(student.getCourse() + 1);
            }
        }
    }
    public static void printStudents(Set<Student> students, int course){
        for (Student student : students){
            if(student.getCourse() == course){
                System.out.println(student.getName());
            }
        }
    }
    public static void printAll(Set<Student> students){
        for (Student student : students){
            System.out.println(student);
        }
    }

}
