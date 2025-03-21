package Garage;

public class Customer {

    private String name;
    private String address;
    private double funds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void buyVehicle(Vehicle vehicle, Employee employee, boolean monthlyPayment, String[] chosenEmp){
        employee.handleCustomer(this, monthlyPayment, vehicle, chosenEmp);
    }
}
