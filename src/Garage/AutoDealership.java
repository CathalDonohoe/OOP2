package Garage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                makeCar();
                break;
            case 4:
                additionalFeatures();
                break;
            case 5:
                System.out.println("Thank you, Good Bye");
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
            System.out.println("Cost: " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(realPrice));
            System.out.println("Payment: " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(customer.getFunds()) + " - " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(realPrice));
            System.out.println("Change: " + df.format(change));
            System.out.println("***********Receipt***********");

            customer.setFunds(change);

            Path p1 = Paths.get("C:\\Users\\cathal.donohoe\\IdeaProjects\\OOP2\\src\\Garage\\receipt.txt");
            try {
                String data = "***********Receipt***********\n"
                        + "Customer Name: " + customer.getName() +"\n"
                        + "Customer Address: " + customer.getAddress() + "\n"
                        +"Employee Name: " + chosenEmp[0]  +"\n"
                        +"Date: " + date.format(dateFormat) + "\n"
                        +"Time: " + time.format(timeFormat) + "\n"
                        +"Vehicle Type: " + vehicle.getType() + "\n"
                        +"Vehicle Make: " + vehicle.getMake() + "\n"
                        +"Vehicle Model: " + vehicle.getModel() + "\n"
                        +"Cost: " +  customer.getFunds() + " € - " + realPrice + " €\n"
                        +"***********Receipt***********";
                Files.write(p1, data.getBytes(), StandardOpenOption.CREATE);
            } catch (IOException e) {
                Logger.getLogger(AutoDealership.class.getName()).log(Level.SEVERE, null, e);
            }

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

    private static void makeCar(){
        Customer customer = getCustomerInfo();
        Employee employee = new Employee();

        System.out.println("The minumum price of a new car is:");
        Optional<Integer> min = Stream.of(26000, 140000, 36000, 56000, 27000, 60000).min((i1, i2)->(i1-i2));
        min.ifPresent(System.out::println);
        Optional<Integer> max = Stream.of(26000, 140000, 36000, 56000, 27000, 60000).max((i1, i2)->(i1-i2));
        System.out.println("The maximum price of a new car is: ");
        max.ifPresent(System.out::println);

        System.out.println("Who would you like to assist you today? Jane or John? Please enter the name or 'Any'");
        var custEmp = sc.next();
        String [] emp= new String[]{"temp"};
        List<String> names = Arrays.asList("Jane", "John");
        Predicate<String> pred = name -> name.startsWith("Jo");
        if (Objects.equals(custEmp, "Any")){
            Optional<String> any = Stream.of("Jane", "John").findAny();
            System.out.println("You have been given: " + any.get());
            emp= new String[]{any.get()};
        } else if (custEmp.equals("Jane")) {
            Optional<String> first = Stream.of("Jane", "John").findFirst();
            System.out.println("You have been given: " + first.get());
            emp = new String[]{first.get()};
        } else if (names.stream().allMatch(pred)) {
            System.out.println("You have been given: John");
            emp = new String[]{"John"};
        } else if(names.stream().anyMatch(pred) || names.stream().noneMatch(pred)) {
            System.out.println("You dont seem to have given a correct answer, John will assist you today");
            emp = new String[]{"john"};
        }

        System.out.println("What type of vehicle would you like to make?");
        var vehType = sc.next();
        System.out.println("What make of vehicle would you like to make?");
        var vehMake = sc.next();
        System.out.println("What model of vehicle would you like to make?");
        var vehModel = sc.next();

        Stream<String> stream = Stream.of(vehType, vehMake, vehModel);
        ArrayList<String> carDetails = new ArrayList<String>(stream.toList());

        System.out.println("You wish to make a vehicle type of: ");
        Stream.of(carDetails.get(0)).filter(type -> type.equals("Car")).forEach(System.out::print);
        Stream.of(carDetails.get(0)).filter(type -> type.equals("Van")).forEach(System.out::print);
        Stream.of(carDetails.get(0)).filter(type -> type.equals("Bike")).forEach(System.out::print);
        System.out.println();

        Optional<Integer> vehPrice = Stream.of(26000, 140000, 36000, 56000, 27000, 60000).findAny();

        Vehicle vehicleChosen = new Vehicle(vehType, vehMake, vehModel, Double.parseDouble(String.valueOf(vehPrice.get())));

        System.out.println("You chose: " + vehicleChosen.getMake() + " " + vehicleChosen.getModel());

        System.out.println("Price: "+vehPrice.get());

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
        customer.buyVehicle(vehicleChosen, employee, monthly, emp);

    }

    private static void additionalFeatures(){

        Path p1 = Paths.get("C:\\Users\\cathal.donohoe\\IdeaProjects\\OOP2\\src\\Garage\\test.txt");
        try {
            Files.createFile(p1);
        } catch (IOException e) {
            Logger.getLogger(AutoDealership.class.getName()).log(Level.SEVERE, null, e);
        }

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
        var custName = sc.nextLine();
        customer.setName(custName);
        System.out.println("Please enter your address: ");
        var custAddress = sc.nextLine();
        customer.setAddress(custAddress);
        double custFunds = 0;
        boolean valid;
        do {
            valid = false;
            System.out.println("Please enter your funds on hand (numbers only): ");
            var custInputFunds = sc.next();
            try {
                Double.parseDouble(custInputFunds);
                valid = true;
                customer.setFunds(Double.parseDouble(custInputFunds));
                custFunds = Double.parseDouble(custInputFunds);

            } catch (NumberFormatException e) {
                System.out.println("You have not entered a valid number. Please only use digits and decimal points");
            }
        } while (!valid);

        System.out.println("The details you entered were: ");
        Stream.of(custName, custAddress, custFunds).map(s->s.toString()).forEach(System.out::println);

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
            System.out.println("3. Buy a brand new vehicle");
            System.out.println("4. Execute additional features");
            System.out.println("5. exit");
            custOption = sc.next();

            if (Objects.equals(custOption, "1") || Objects.equals(custOption, "2") || Objects.equals(custOption, "3") || Objects.equals(custOption, "4") || Objects.equals(custOption, "5")) {
                valid=true;
            }else{
                System.out.println("You have not given a valid option, please select one of the following");
            }
        } while (!valid);
        return custOption;
    }
}
