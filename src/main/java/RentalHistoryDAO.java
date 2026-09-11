import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalHistoryDAO {
    private static final String URL = "jdbc:mysql://mysql-22de4455-aerroko-baza.g.aivencloud.com:20716/defaultdb?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_5mUju3R5-wIbD2Vnlhq";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean saveEntry(String licensePlate, String model, String client, String activityType,
                             String checkoutTime, String returnTime, int kilometers, String fuelLevel) {
        String sql = "INSERT INTO rental_history (license_plate, model, client, activity_type, checkout_time, return_time, kilometers, fuel_level) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, licensePlate);
            stmt.setString(2, model);
            stmt.setString(3, client);
            stmt.setString(4, activityType);
            stmt.setString(5, checkoutTime);
            stmt.setString(6, returnTime);
            stmt.setInt(7, kilometers);
            stmt.setString(8, fuelLevel);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving rental history entry: " + e.getMessage());
            return false;
        }
    }

    public List<Activity> getAllHistory() {
        List<Activity> history = new ArrayList<>();
        String sql = "SELECT * FROM rental_history ORDER BY id DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                history.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error loading rental history: " + e.getMessage());
        }

        return history;
    }

    public List<Activity> searchByLicensePlate(String licensePlate) {
        List<Activity> results = new ArrayList<>();
        String sql = "SELECT * FROM rental_history WHERE license_plate LIKE ? ORDER BY id DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + licensePlate + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching rental history: " + e.getMessage());
        }

        return results;
    }

    private Activity mapRow(ResultSet rs) throws SQLException {
        Car car = new Car(rs.getString("license_plate"), rs.getString("model"));

        String details = rs.getString("client") + " | " + rs.getString("activity_type") +
                " | Checkout: " + rs.getString("checkout_time") +
                " | Return: " + rs.getString("return_time") +
                " | Km: " + rs.getInt("kilometers") +
                " | Fuel: " + rs.getString("fuel_level");

        Activity activity = new Activity();
        activity.setCar(car);
        activity.setActivity(details);
        return activity;
    }
}