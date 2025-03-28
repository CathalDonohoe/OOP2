package Garage;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class AutoDealership {

    static Scanner sc = new Scanner(System.in);

    private static final DecimalFormat df = new DecimalFormat("0.00");

    static LocalDate date = LocalDate.now();
    static LocalTime time = LocalTime.now();
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yy");
    static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args){

        int custOption = Integer.parseInt(hello());

        switch (custOption){
            case 1:
                buyCar();
                break;
            case 2:
                fixCar();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("You seem to have entered an incorrect option");
                hello();
        }
    }

    private static void fixCar() {
        Customer customer = getCustomerInfo();
        Vehicle vehicle = new Vehicle();
        String[] chosenEmp = getEmployee(2);

        System.out.println("Please enter your type of vehicle");
        vehicle.setType(sc.next());
        System.out.println("Please enter your make of vehicle");
        vehicle.setMake(sc.next());
        System.out.println("Please enter model of Vehicle");
        vehicle.setModel(sc.next());

        double min = 100;
        double max = 10000;

        Random rand = new Random();
        double price = min + (max - min) * rand.nextDouble();
        double realPrice = Double.parseDouble(df.format(price));
        System.out.println("Your estimated price is: " + realPrice);
        System.out.println("Your funds are: " + customer.getFunds() + " Euro");
        if(customer.getFunds()>realPrice){
            double change = customer.getFunds() - realPrice;
            System.out.println("You have sufficent funds");
            System.out.println();
            System.out.println("***********Receipt***********");
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Customer Address: " + customer.getAddress());
            System.out.println("Employee Name: " + chosenEmp[0]);
            System.out.println("Date: " + date.format(dateFormat));
            System.out.println("Time: " + time.format(timeFormat));
            System.out.println("Vehicle Type: " + vehicle.getType());
            System.out.println("Vehicle Make: " + vehicle.getMake());
            System.out.println("Vehicle Model: " + vehicle.getModel());
            System.out.println("Cost: " + realPrice);
            System.out.println("Payment: " + customer.getFunds() + " - " + realPrice);
            System.out.println("Change: " + df.format(change));
            System.out.println("***********Receipt***********");

            customer.setFunds(change);

        }else{
            System.out.println("You have insufficent funds for this repair");
        }

    }

    private static void buyCar() {

        Employee emp = new Employee();

        Customer customer = getCustomerInfo();

        String[] chosenEmp = getEmployee(1);
        VehicleList vl = new VehicleList();

        ArrayList<Vehicle> vehicleList = vl.vehicles;

        int choice = 12342;
        boolean valid = false;

        do {
            System.out.println("Select a car from our available options");
            for(var x =0; x<vehicleList.size(); x++){
                System.out.println(x+1 + ". Type: " + vehicleList.get(x).getType() + " Make: "+ vehicleList.get(x).getMake() + " Model: " + vehicleList.get(x).getModel() + " Price: " + vehicleList.get(x).getPrice());
            }

            var choiceX = sc.next();
            System.out.println(choiceX);
            try {
                Integer.parseInt(choiceX);
                if(Integer.parseInt(choiceX) <= 0 || Integer.parseInt(choiceX) > vehicleList.size()) throw new NumberFormatException();
                valid = true;
                choice = Integer.parseInt(choiceX);

            } catch (NumberFormatException e) {
                System.out.println("You have not entered a valid number. Please only use digits and decimal points");
            }

        }while (!valid);

        Vehicle vehicleChosen = new Vehicle(vehicleList.get(choice-1).getType(), vehicleList.get(choice-1).getMake(), vehicleList.get(choice-1).getModel(), vehicleList.get(choice-1).getPrice());

        System.out.println("You chose: " + vehicleChosen.getMake() + " " + vehicleChosen.getModel());

        boolean monthly =false;

        String option = String.valueOf(monthly());

        switch (option){
            case "y", "Y":
                monthly = true;
                break;
            case "n", "N":
                monthly = false;
                break;
            default:
                monthly();
        }

        Vehicle vehicle = new Vehicle();
        customer.buyVehicle(vehicleChosen, emp, monthly, chosenEmp);
    }

    private static String monthly() {
        boolean valid = false;
        String option="g";
        do {
            System.out.println("Do you wish to pay Monthly? (y/n)");
            option = sc.next();

            System.out.println(option);

            if(!Objects.equals(option, "y") && !Objects.equals(option, "Y") && !Objects.equals(option, "n") && !Objects.equals(option, "N")){
                valid = false;
            }else{
                valid = true;
            }
        }while (!valid);

        return option;
    }

    private static String[] getEmployee(int i) {

        Employee emp = new Employee();
        emp.setEmployees();
        ArrayList<Person> employees = new ArrayList<Person>();

        if(i==1){
            emp.setSales();
            employees = emp.sales;
        } else{
            emp.setMechanics();
            employees = emp.mechanics;
        }

        int choice = 34534;
        boolean valid;
        long numOfEmployees = Stream.of(employees).count();
        System.out.println("We have " + numOfEmployees + " employees you can choose from today");
        do {
            valid =false;

            System.out.println("Please choose an employee");

            for(var x =0; x<employees.size(); x++){
                System.out.println(x+1 + "." + employees.get(x).name);
            }
            var choiceX = sc.next();
            System.out.println(choiceX);
            try {
                Integer.parseInt(choiceX);
                if(Integer.parseInt(choiceX) <= 0 || Integer.parseInt(choiceX) > employees.size()) throw new NumberFormatException();
                valid = true;
                choice = Integer.parseInt(choiceX);

            } catch (NumberFormatException e) {
                System.out.println("You have not entered a valid number. Please only use digits and decimal points");
            }
        } while (!valid);

        String[] chosenEmp = {employees.get(choice-1).name, employees.get(choice-1).occupation};

        System.out.println("You chose: " + chosenEmp[0]);

        return chosenEmp;
    }

    private static Customer getCustomerInfo(){
        sc.nextLine();
        Customer customer = new Customer();
        System.out.println("Please enter your name:");
        customer.setName(sc.nextLine());
        System.out.println("Please enter your address: ");
        customer.setAddress(sc.nextLine());

        boolean valid;
        do {
            valid = false;
            System.out.println("Please enter your funds on hand (numbers only): ");
            var custInputFunds = sc.next();
            try {
                Double.parseDouble(custInputFunds);
                valid = true;
                customer.setFunds(Double.parseDouble(custInputFunds));

            } catch (NumberFormatException e) {
                System.out.println("You have not entered a valid number. Please only use digits and decimal points");
            }
        } while (!valid);

        return customer;
    }

    private static String hello() {
        String custOption;
        boolean valid = false;
        do {
            System.out.println("Welcome to Donohoe's Garage, please select how we can help you today!");
            System.out.println("*********************************************************************");
            System.out.println("1. Buy a vehicle");
            System.out.println("2. Fix a vehicle");
            System.out.println("3. exit");
            custOption = sc.next();

            if (Objects.equals(custOption, "1") || Objects.equals(custOption, "2") || Objects.equals(custOption, "3")) {
                valid=true;
            }else{
                System.out.println("You have not given a valid option, please select one of the following");
            }
        } while (!valid);
        return custOption;
    }
}
