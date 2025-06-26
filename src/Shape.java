    public interface Shape{
        double getArea();

        default double getPerimeter(){
            return 0;
        }

        void setFillColor(String color);
        String getFillColor();

        void setBorderColor(String color);
        String getBorderColor();

    }

