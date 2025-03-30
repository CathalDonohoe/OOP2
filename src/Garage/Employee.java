package Garage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public non-sealed class Employee extends Person{

    Person p1 = new Person ("Jane Doe", Occupation.Sales);
    Person p2 = new Person ("John Doe", Occupation.Sales);
    Person p3 = new Person("Alice Smith", Occupation.Mechanic);
    Person p4 = new Person("Alex Smith", Occupation.Mechanic);

    ArrayList<Person> employees = new ArrayList<Person>();
    ArrayList<Person> sales = new ArrayList<Person>();
    ArrayList<Person> mechanics = new ArrayList<Person>();

    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yy");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public Employee() {
        super();
        super.name.equals("Rebecca");
        super.occupation.equals("Manager");
    }


    @Override
    protected void janitor(String name, Occupation occupation) {
        super.janitor(name, occupation);
    }

    public void setEmployees(){
        employees.add(p1);
        employees.add(p2);
        employees.add(p3);
        employees.add(p4);
    }

    public ArrayList<Person> getEmployees() {
        return employees;
    }


    public ArrayList<Person> setSales() {

        for(int i=0; i < employees.size(); i++){
            if(Objects.equals(employees.get(i).occupation, "Sales")){
                sales.add(employees.get(i));
            }
        }
        return sales;
    }

    public ArrayList<Person> setMechanics() {

        AtomicInteger i = new AtomicInteger();

        employees.forEach( (person) -> {
            if(Objects.equals(employees.get(i.get()).occupation, "Mechanic")){
                mechanics.add(employees.get(i.get()));
            }
            i.getAndIncrement();
        });

        return mechanics;
    }

    public void handleCustomer(Customer cust, boolean monthlyPayments, Vehicle vehicle, String[] chosenEmp) throws IOException {

        if (monthlyPayments){
            double balanceLoan = vehicle.getPrice() - cust.getFunds();
            processTransaction(vehicle.getPrice(), cust.getFunds(), cust, vehicle, chosenEmp, balanceLoan);
        } else if (vehicle.getPrice() <= cust.getFunds()) {
            processTransaction(cust, vehicle, chosenEmp, vehicle.getPrice(), cust.getFunds());
        }else if (vehicle.getPrice() > cust.getFunds()){
            System.out.println("Customer has insufficient funds");
        }
    }

    private void processTransaction(Customer cust, Vehicle vehicle, String[] chosenEmp, double... d) throws IOException {
        var change = d[1] - d[0];
        System.out.println("Transaction complete for " + vehicle.getMake() + " " + vehicle.getModel());
        System.out.println();
        System.out.println("***********Receipt***********");
        System.out.println("Customer Name: " + cust.getName());
        System.out.println("Customer Address: " + cust.getAddress());
        System.out.println("Employee Name: " + chosenEmp[0]);
        System.out.println("Date: " + date.format(dateFormat));
        System.out.println("Time: " + time.format(timeFormat));
        System.out.println("Vehicle Type: " + vehicle.getType());
        System.out.println("Vehicle Make: " + vehicle.getMake());
        System.out.println("Vehicle Model: " + vehicle.getModel());
        System.out.println("Cost: "+ NumberFormat.getCurrencyInstance(Locale.GERMANY).format(d[0]));
        System.out.println("Payment: " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(cust.getFunds()) + " - " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(d[0]));
        System.out.println("Change: " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(change));
        System.out.println("***********Receipt***********");

        cust.setFunds(change);

        Path p1 = Paths.get("C:\\Users\\cathal.donohoe\\IdeaProjects\\OOP2\\src\\Garage\\receipt.txt");
        Files.delete(p1);
        try {
            String data = "***********Receipt***********\n"
                    + "Customer Name: " + cust.getName() +"\n"
                    + "Customer Address: " + cust.getAddress() + "\n"
                    +"Employee Name: " + chosenEmp[0]  +"\n"
                    +"Date: " + date.format(dateFormat) + "\n"
                    +"Time: " + time.format(timeFormat) + "\n"
                    +"Vehicle Type: " + vehicle.getType() + "\n"
                    +"Vehicle Make: " + vehicle.getMake() + "\n"
                    +"Vehicle Model: " + vehicle.getModel() + "\n"
                    +"Cost: " +  cust.getFunds() + " € - " + d[0] + " €\n"
                    +"***********Receipt***********";
            Files.write(p1, data.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            Logger.getLogger(AutoDealership.class.getName()).log(Level.SEVERE, null, e);
        }
    }



    public void processTransaction(double price, double funds, Customer cust, Vehicle vehicle, String[] chosenEmp, double balanceLoan) throws IOException {
        System.out.println("Checking if loan is valid...");
        System.out.println("Loan has been approved");

        System.out.println("Transaction complete for " + vehicle.getMake() + " " + vehicle.getModel());
        System.out.println();
        System.out.println("***********Receipt***********");
        System.out.println("Customer Name: " + cust.getName());
        System.out.println("Customer Address: " + cust.getAddress());
        System.out.println("Employee Name: " + chosenEmp[0]);
        System.out.println("Date: " + date.format(dateFormat));
        System.out.println("Time: " + time.format(timeFormat));
        System.out.println("Vehicle Type: " + vehicle.getType());
        System.out.println("Vehicle Make: " + vehicle.getMake());
        System.out.println("Vehicle Model: " + vehicle.getModel());
        System.out.println("Cost: " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(price));
        System.out.println("Customer to pay monthly");
        System.out.println("***********Receipt***********");


        Path p1 = Paths.get("C:\\Users\\cathal.donohoe\\IdeaProjects\\OOP2\\src\\Garage\\receipt.txt");
        Files.delete(p1);
        try {
            String data = "***********Receipt***********\n"
                    + "Customer Name: " + cust.getName() +"\n"
                    + "Customer Address: " + cust.getAddress() + "\n"
                    +"Employee Name: " + chosenEmp[0]  +"\n"
                    +"Date: " + date.format(dateFormat) + "\n"
                    +"Time: " + time.format(timeFormat) + "\n"
                    +"Vehicle Type: " + vehicle.getType() + "\n"
                    +"Vehicle Make: " + vehicle.getMake() + "\n"
                    +"Vehicle Model: " + vehicle.getModel() + "\n"
                    +"Cost: " + price + " €\n"
                    +"Customer to pay monthly \n"
                    +"***********Receipt***********";
            Files.write(p1, data.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            Logger.getLogger(AutoDealership.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}