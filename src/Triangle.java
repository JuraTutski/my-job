public class Triangle implements Shape{
    private double sideA;
    private double sideB;
    private double sideC;
    private String fillColor;
    private String borderColor;

    public Triangle(double sideA, double sideB, double sideC){
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }
    @Override
    public double getArea(){
        double s = getPerimeter() / 2.0;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public void setFillColor(String color) {
        fillColor = color;
    }

    @Override
    public String getFillColor() {
        return fillColor;
    }

    @Override
    public void setBorderColor(String color) {
        borderColor = color;
    }

    @Override
    public String getBorderColor() {
        return borderColor;
    }
}
