package Garage;

public class Threading extends Thread{


    private int threadNumber;
    public Threading (int threadNumber){
        this.threadNumber = threadNumber +1;
    }

    @Override
    public void run(){
        System.out.println("Calling Garage number: " + threadNumber);

        if (threadNumber == 4){
            System.out.println("Welcome to Mike's Taxi, we will be there in 5 minutes");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
