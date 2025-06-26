public class Circle implements Shape{
    private double radius;
    private String fillColor;
    private String borderColor;

    public Circle(double radius){
        this.radius = radius;
    }
    @Override

    public double getArea(){
        return Math.PI * radius * radius;
    }
    @Override

    public double getPerimeter(){
        return 2 * Math.PI * radius;
    }
    @Override

    public void setFillColor(String color){
        fillColor = color;
    }
    @Override

    public String getFillColor(){
        return fillColor;
    }
    @Override

    public void setBorderColor(String color){
        borderColor = color;
    }
    @Override

    public String getBorderColor(){
        return borderColor;

    }
}
