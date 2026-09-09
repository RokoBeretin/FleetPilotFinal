import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

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
            System.err.println("Greška pri čitanju bin datoteke: " + e.getMessage());
        }

        return cars;
    }

    public static void main(String[] args) {
        String binFilePath = "C:\\Users\\nikob\\IdeaProjects\\finalni-projekt-oop-24-25-RokoBeretin\\cars.bin";

        List<Car> carsFromBin = readCarsFromBinFile(binFilePath);
        System.out.println("Pronađeno " + carsFromBin.size() + " auta u binarnoj datoteci.");

        CarDAO carDAO = new CarDAO();
        int uspjesnoSpremljeno = 0;

        for (Car car : carsFromBin) {
            boolean success = carDAO.saveCar(car);
            if (success) {
                uspjesnoSpremljeno++;
            }
        }

        System.out.println("Migracija završena! Uspješno prebačeno " + uspjesnoSpremljeno + " od " + carsFromBin.size() + " auta u Aiven bazu.");
    }
}