package web.model;

import java.awt.*;


public class Car {

    private String model;
    private int series;
    private Color color;

    public Car(String model, int series, Color color) {
        this.model = model;
        this.series = series;
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "model: " + model + " series: " + series + " color: " + color.getRGB();
    }


}
