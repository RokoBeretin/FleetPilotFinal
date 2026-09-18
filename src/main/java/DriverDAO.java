import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * DAO (Data Access Object) klasa zadužena za sav pristup tablici
 * {@code drivers} u bazi podataka.
 * <p>
 * Koristi je prvenstveno {@code NewActivityPanel} kako bi popunila
 * padajući izbornik vozača, čime se sprječava ručni upis podataka o
 * klijentu i osigurava da svaka rezervacija referencira postojećeg vozača.
 */
public class DriverDAO {

    private static final String URL = "jdbc:mysql://mysql-22de4455-aerroko-baza.g.aivencloud.com:20716/defaultdb?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_5mUju3R5-wIbD2Vnlhq";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, oib FROM drivers";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("oib")
                );
                drivers.add(driver);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching drivers from database: " + e.getMessage());
        }

        return drivers;
    }

    public Optional<Driver> getDriverByOib(String oib) {
        String sql = "SELECT id, first_name, last_name, oib FROM drivers WHERE oib = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, oib);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Driver(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("oib")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching for driver: " + e.getMessage());
        }

        return Optional.empty();
    }

    public boolean saveDriver(Driver driver) {
        String sql = "INSERT INTO drivers (first_name, last_name, oib) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getOib());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error saving driver: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDriver(String oib) {
        String sql = "DELETE FROM drivers WHERE oib = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, oib);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting driver: " + e.getMessage());
            return false;
        }
    }
}