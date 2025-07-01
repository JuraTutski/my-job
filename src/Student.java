import java.util.List;
import java.util.Objects;

public class Student {
    private String name;
    private String group;
    private int course;
    private List<Integer> grades;

    public Student(String name, String group,int course, List<Integer> grades){
        this.name = name;
        this.group = group;
        this.course = course;
        this.grades = grades;
    }

     public String getName(){
        return name;
     }

     public int getCourse(){
        return course;
     }

     public void setCourse(int course){
        this.course = course;
     }

     public double getAverageGrade(){
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades){
            sum += grade;
        }
        return (double) sum / grades.size();

     }

     @Override
    public String toString(){
        return name + "| Группа: " + group + "| Курс: " + course +
                "| Средняя оценка: " + String.format("%.2f", getAverageGrade());


    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(group, student.group);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, group);
    }


}
