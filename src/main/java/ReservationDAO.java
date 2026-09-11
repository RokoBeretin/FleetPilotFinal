import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private static final String URL = "jdbc:mysql://mysql-22de4455-aerroko-baza.g.aivencloud.com:20716/defaultdb?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_5mUju3R5-wIbD2Vnlhq";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (license_plate, client, time_of_res, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservation.getLicensePlate());
            stmt.setString(2, reservation.getClient());
            stmt.setString(3, reservation.getTimeOfRes());
            stmt.setString(4, reservation.getStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error saving reservation: " + e.getMessage());
            return false;
        }
    }

    public List<Reservation> getAllActiveReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE status = 'ACTIVE'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservation res = new Reservation(
                        rs.getInt("id"),
                        rs.getString("license_plate"),
                        rs.getString("client"),
                        rs.getString("time_of_res"),
                        rs.getString("status")
                );
                reservations.add(res);
            }

        } catch (SQLException e) {
            System.err.println("Error loading reservations: " + e.getMessage());
        }

        return reservations;
    }
    public boolean updateReservationStatus(String licensePlate, String status) {
        String sql = "UPDATE reservations SET status = ? WHERE license_plate = ? AND status = 'ACTIVE'";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, licensePlate);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating reservation status: " + e.getMessage());
            return false;
        }
    }
}