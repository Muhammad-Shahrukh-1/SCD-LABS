class Parking {
    private int max = 4;
    private int count = 0;

    public synchronized void parkCar() {
        if (count < max) {
            System.out.println("Car is parked. Current count: " + (count + 1));
            count++;
        } else {
            System.out.println("Parking is full. Waiting for a spot...");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
    }

    public synchronized void carMovingOut() {
        if (count > 0) {
            System.out.println("Car is moving out. Parking spot is free now. Current count: " + (count - 1));
            count--;
            notifyAll();
        } else {
            System.out.println("No cars in the parking lot.");
        }
    }
}

class CarParkingThread extends Thread {
    private Parking parking;

    public CarParkingThread(Parking parking) {
        this.parking = parking;
    }

    public void run() {
        parking.parkCar();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
}

class CarMovingOutThread extends Thread {
    private Parking parking;

    public CarMovingOutThread(Parking parking) {
        this.parking = parking;
    }

    public void run() {
        parking.carMovingOut();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Parking parking = new Parking();

        for (int i = 0; i < 10; i++) {
            CarParkingThread carPark = new CarParkingThread(parking);
            CarMovingOutThread carOut = new CarMovingOutThread(parking);

            carPark.start();
            carOut.start();
        }
    }
}
