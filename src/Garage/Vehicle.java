package Garage;

public class Vehicle {
    private String type;
    private String make;
    private String model;
    private double price;

    public void setType(String type) {
        this.type = type;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public Vehicle(String type, String make, String model, double price) {
        this.type = type;
        this.make = make;
        this.model = model;
        this.price = price;
    }

    public Vehicle(){
    }

    @Override
    public String toString() {
        return "Vehicle{" + "type='" + type + ", make='" + make + ", model='" + model + ", price=" + price + '}';
    }
}
