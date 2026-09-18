import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * DAO (Data Access Object) klasa zadužena za sav pristup tablici
 * {@code cars} u MySQL bazi podataka (Aiven cloud).
 * <p>
 * Ovo je jedino mjesto u projektu koje piše SQL upite vezane uz vozila -
 * ostatak aplikacije (GUI paneli, Command klase) komunicira isključivo
 * kroz metode ove klase i domenske objekte tipa {@link Car}, nikad
 * izravno s bazom podataka.
 */
public class CarDAO {

    private static final String URL = "jdbc:mysql://mysql-22de4455-aerroko-baza.g.aivencloud.com:20716/defaultdb?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_5mUju3R5-wIbD2Vnlhq";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT license_plate, model, is_available FROM cars";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String licensePlate = rs.getString("license_plate");
                String model = rs.getString("model");
                boolean isAvailable = rs.getBoolean("is_available");

                Car car = new Car(licensePlate, model);
                car.setAvailable(isAvailable);
                cars.add(car);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving car from database: " + e.getMessage());
        }

        return cars;
    }

    public Optional<Car> getCarByLicensePlate(String licensePlate) {
        String sql = "SELECT license_plate, model, is_available FROM cars WHERE license_plate = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, licensePlate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String model = rs.getString("model");
                    boolean isAvailable = rs.getBoolean("is_available");

                    Car car = new Car(licensePlate, model);
                    car.setAvailable(isAvailable);
                    return Optional.of(car);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching for car: " + e.getMessage());
        }

        return Optional.empty();
    }

    public boolean saveCar(Car car) {
        String sql = "INSERT INTO cars (license_plate, model, is_available) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, car.getLicensePlate());
            stmt.setString(2, car.getModel());
            stmt.setBoolean(3, car.isAvailable());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error saving car: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCarAvailability(String licensePlate, boolean isAvailable) {
        String sql = "UPDATE cars SET is_available = ? WHERE license_plate = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isAvailable);
            stmt.setString(2, licensePlate);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error changing car availability: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCar(String licensePlate) {
        String sql = "DELETE FROM cars WHERE license_plate = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, licensePlate);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting car: " + e.getMessage());
            return false;
        }
    }
}