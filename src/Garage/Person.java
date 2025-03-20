package Garage;

sealed class Person implements Owner permits Employee {

    enum Occupation{
        Sales,
        Mechanic,
        Owner
    }

    String name;

    String occupation;

    public Person(String name, Occupation occupation) {
        this.name = name;
        this.occupation = String.valueOf(occupation);
    }

    public Person(){
        this(Owner.name, Occupation.valueOf(Owner.occupation));
    }

    protected void janitor(String name, Occupation occupation){
        this.name = name;
        this.occupation = String.valueOf(occupation);
    }
}
