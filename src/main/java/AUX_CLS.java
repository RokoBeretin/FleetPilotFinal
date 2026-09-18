import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Pomoćna (utility) klasa sa statičkim metodama koje ne pripadaju
 * nijednoj konkretnoj domenskoj ili DAO klasi - čitanje JSON konfiguracije
 * te posredno učitavanje vozila iz baze podataka.
 */
public class AUX_CLS {
    private static Gson gson = new Gson();
    private static CarDAO carDAO = new CarDAO();
    private static ReservationDAO reservationDAO = new ReservationDAO();

    public static HashMap<String, ArrayList<String>> readFromJson(String filePath) {
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            hashMap = gson.fromJson(reader, HashMap.class);
            System.out.println("Loaded from JSON file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return hashMap;
    }

    public static ArrayList<Car> loadCarsFromDb() {
        List<Car> carsFromDb = carDAO.getAllCars();
        System.out.println("Cars loaded from Aiven database.");
        return new ArrayList<>(carsFromDb);
    }

}