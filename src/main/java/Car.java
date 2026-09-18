import java.io.Serializable;
/**
 * Domenska klasa (model) koja predstavlja jedno vozilo iz voznog parka.
 * <p>
 * Odgovara jednom retku u tablici {@code cars} u bazi podataka. Ne sadrži
 * nikakvu logiku pristupa bazi podataka - za to je zadužena klasa
 * {@link CarDAO}. Implementira {@link Serializable} radi kompatibilnosti sa
 * starijim dijelom projekta koji je koristio binarnu (datotečnu) pohranu
 * podataka prije migracije na MySQL bazu.
 */
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
