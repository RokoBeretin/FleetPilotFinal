import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    // Marks every car available again and clears pending/active reservations.
    // rental_history is intentionally left untouched - it's the permanent audit trail.
    public static void resetAllData() {
        List<Car> cars = carDAO.getAllCars();
        for (Car car : cars) {
            carDAO.updateCarAvailability(car.getLicensePlate(), true);
        }

        reservationDAO.deleteAllReservations();
        System.out.println("All data reset successfully in database.");
    }
}