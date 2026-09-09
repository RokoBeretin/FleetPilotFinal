import java.io.Serializable;

public class Car implements Serializable {
    private String licensePlate;
    private String model;
    private boolean isAvailable;


    public Car(String licensePlate, String model) {
        this.licensePlate = licensePlate;
        this.model = model;
        isAvailable = true;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "Car{" +
                "licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public boolean setAvailable(boolean available) {
        isAvailable = available;
        return isAvailable;
    }


}
