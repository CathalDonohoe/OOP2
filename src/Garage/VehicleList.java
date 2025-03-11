package Garage;

import java.util.ArrayList;

public final class VehicleList {

    final ArrayList<Vehicle> vehicles = new ArrayList<>();

    Vehicle v1 = new Vehicle ("Car", "Ford", "Focus", 8000);
    Vehicle v2 = new Vehicle ("Car", "Audi", "A4", 11000);
    Vehicle v3 = new Vehicle("Van", "Renault", "Traffic", 17000);
    Vehicle v4 = new Vehicle("Bike", "Yamaha", "MT 09", 9000);


    public VehicleList() {
        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);
        vehicles.add(v4);
    }
}
