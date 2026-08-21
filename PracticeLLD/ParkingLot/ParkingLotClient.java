import java.util.ArrayList;
import java.util.List;

enum VehicleType {
    BIKE,
    CAR,
    TRUCK
}

abstract class Vehicle {

    private final String number;
    private final VehicleType vehicleType;

    protected Vehicle(String number, VehicleType vehicleType) {
        this.number = number;
        this.vehicleType = vehicleType;
    }

    public String getNumber() {
        return number;
    }

    public VehicleType getType() {
        return vehicleType;
    }
}

class Bike extends Vehicle {

    public Bike(String number) {
        super(number, VehicleType.BIKE);
    }
}

class Car extends Vehicle {

    public Car(String number) {
        super(number, VehicleType.CAR);
    }
}

class Truck extends Vehicle {

    public Truck(String number) {
        super(number, VehicleType.TRUCK);
    }
}

enum SpotType {
    BIKE,
    CAR,
    TRUCK
}

enum Availability {
    OCCUPIED,
    VACANT
}

class ParkingSpot {

    private final String spotId;
    private final SpotType spotType;

    private Availability availability;
    private Vehicle vehicle;

    public ParkingSpot(String spotId, SpotType spotType) {
        this.spotId = spotId;
        this.spotType = spotType;
        this.availability = Availability.VACANT;
    }

    public String getSpotId() {
        return spotId;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public boolean isAvailable() {
        return availability == Availability.VACANT;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void park(Vehicle vehicle) {

        if (!isAvailable()) {
            System.out.println("Parking spot is already occupied");
            return;
        }

        this.vehicle = vehicle;
        this.availability = Availability.OCCUPIED;
    }

    public void free() {

        this.vehicle = null;
        this.availability = Availability.VACANT;
    }
}

class ParkingLot {

    private final List<ParkingSpot> spots;

    public ParkingLot() {
        spots = new ArrayList<>();
    }

    public void addParkingSpot(String spotId, SpotType spotType) {

        ParkingSpot spot = new ParkingSpot(spotId, spotType);
        spots.add(spot);

        System.out.println("Parking spot added: " + spotId);
    }

    public void removeParkingSpot(String spotId) {

        for (ParkingSpot spot : spots) {

            if (spot.getSpotId().equals(spotId)) {

                if (!spot.isAvailable()) {
                    System.out.println(
                            "Cannot remove occupied parking spot"
                    );
                    return;
                }

                spots.remove(spot);
                System.out.println(
                        "Parking spot removed: " + spotId
                );
                return;
            }
        }

        System.out.println("Parking spot not found");
    }

    public void parkVehicle(Vehicle vehicle) {

        for (ParkingSpot spot : spots) {

            if (spot.isAvailable()
                    && spot.getSpotType() == SpotType.valueOf(vehicle.getType().toString())) {

                spot.park(vehicle);

                System.out.println(
                        "Vehicle " + vehicle.getNumber()
                                + " parked at spot "
                                + spot.getSpotId()
                );

                return;
            }
        }

        System.out.println(
                "No available " + vehicle.getType() + " spot"
        );
    }

    public void removeVehicle(Vehicle vehicle) {

        for (ParkingSpot spot : spots) {

            Vehicle parkedVehicle = spot.getVehicle();

            if (parkedVehicle != null
                    && parkedVehicle.getNumber()
                    .equals(vehicle.getNumber())) {

                spot.free();

                System.out.println(
                        "Vehicle " + vehicle.getNumber()
                                + " removed from spot "
                                + spot.getSpotId()
                );

                return;
            }
        }

        System.out.println(
                "Vehicle " + vehicle.getNumber()
                        + " is not parked"
        );
    }

    public void displayAvailableSpots() {

        System.out.println("\nAvailable Parking Spots:");

        boolean found = false;

        for (ParkingSpot spot : spots) {

            if (spot.isAvailable()) {

                System.out.println(
                        "Spot: " + spot.getSpotId()
                                + " | Type: "
                                + spot.getSpotType()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No spots available");
        }
    }
}

public class ParkingLotClient {

    public static void main(String[] args) {

        ParkingLot lot = new ParkingLot();

        // Add parking spots
        lot.addParkingSpot("B11", SpotType.BIKE);
        lot.addParkingSpot("B12", SpotType.BIKE);

        lot.addParkingSpot("C11", SpotType.CAR);
        lot.addParkingSpot("C12", SpotType.CAR);

        lot.addParkingSpot("T11", SpotType.TRUCK);

        // Create vehicles
        Vehicle bike1 = new Bike("AP-B11");
        Vehicle bike2 = new Bike("TG-B12");

        Vehicle car1 = new Car("AP-C11");
        Vehicle car2 = new Car("TS-C12");

        Vehicle truck = new Truck("TG-T11");

        // Park vehicles
        System.out.println("\n--- Parking Vehicles ---");

        lot.parkVehicle(bike1);
        lot.parkVehicle(bike2);
        lot.parkVehicle(car1);
        lot.parkVehicle(truck);

        // Display available
        lot.displayAvailableSpots();

        // Park second car
        System.out.println("\n--- Parking Car 2 ---");

        lot.parkVehicle(car2);

        // Try another car - no spot available
        Vehicle car3 = new Car("KA-C13");

        System.out.println("\n--- Parking Car 3 ---");

        lot.parkVehicle(car3);

        // Remove bike1
        System.out.println("\n--- Removing Bike 1 ---");

        lot.removeVehicle(bike1);

        // Display again
        lot.displayAvailableSpots();

        // Try removing bike1 again
        System.out.println("\n--- Removing Bike 1 Again ---");

        lot.removeVehicle(bike1);
    }
}