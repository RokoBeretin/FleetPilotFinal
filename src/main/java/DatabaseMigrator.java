import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Jednokratna pomoćna klasa (skripta, ne dio žive aplikacije) koja
 * prebacuje podatke o vozilima iz stare binarne datoteke ({@code cars.bin},
 * nastale serijalizacijom {@link Car} objekata) u MySQL bazu podataka na
 * Aiven cloudu, preko {@link CarDAO}.
 * <p>
 * Korištena je jednom, u trenutku migracije projekta s datotečne pohrane
 * na relacijsku bazu podataka.
 */
public class DatabaseMigrator {

    @SuppressWarnings("unchecked")
    public static List<Car> readCarsFromBinFile(String filePath) {
        List<Car> cars = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                cars = (List<Car>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading binary file: " + e.getMessage());
        }

        return cars;
    }

    public static void main(String[] args) {
        String binFilePath = "C:\\Users\\nikob\\IdeaProjects\\finalni-projekt-oop-24-25-RokoBeretin\\cars.bin";

        List<Car> carsFromBin = readCarsFromBinFile(binFilePath);
        System.out.println("Found " + carsFromBin.size() + " cars in the binary file.");

        CarDAO carDAO = new CarDAO();
        int successfullySaved = 0;

        for (Car car : carsFromBin) {
            boolean success = carDAO.saveCar(car);
            if (success) {
                successfullySaved++;
            }
        }

        System.out.println("Migration finished! Successfully transferred " + successfullySaved + " out of " + carsFromBin.size() + " cars to the Aiven database.");
    }
}