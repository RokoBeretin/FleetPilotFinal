import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDAO {
    private static final String URL = "jdbc:mysql://mysql-22de4455-aerroko-baza.g.aivencloud.com:20716/defaultdb?ssl-mode=REQUIRED";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_5mUju3R5-wIbD2Vnlhq";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Save a new reservation row (either a pending pickup or a direct checkout for Car Wash / Gas Refill)
    public boolean saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (license_plate, client, time_of_res, status, activity_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservation.getLicensePlate());
            stmt.setString(2, reservation.getClient());
            stmt.setString(3, reservation.getTimeOfRes());
            stmt.setString(4, reservation.getStatus());
            stmt.setString(5, reservation.getActivityType());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error saving reservation: " + e.getMessage());
            return false;
        }
    }

    // Reservations awaiting pickup (shown in ReservationsPanel)
    public List<Reservation> getAllActiveReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE status = 'ACTIVE' AND activity_type = 'RESERVATION'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reservations.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error loading active reservations: " + e.getMessage());
        }

        return reservations;
    }

    // Cars currently checked out, waiting to be returned (shown in ReturnsPanel)
    public List<Reservation> getAllCheckedOutReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE status = 'CHECKED_OUT'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reservations.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error loading checked-out reservations: " + e.getMessage());
        }

        return reservations;
    }

    public Optional<Reservation> getReservationByLicensePlateAndStatus(String licensePlate, String status) {
        String sql = "SELECT * FROM reservations WHERE license_plate = ? AND status = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, licensePlate);
            stmt.setString(2, status);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading reservation: " + e.getMessage());
        }

        return Optional.empty();
    }

    // Transition ACTIVE -> CHECKED_OUT once the customer actually picks up the car
    public boolean checkOutReservation(String licensePlate, String expectedReturnTime) {
        String sql = "UPDATE reservations SET status = 'CHECKED_OUT', expected_return_time = ? WHERE license_plate = ? AND status = 'ACTIVE'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, expectedReturnTime);
            stmt.setString(2, licensePlate);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error checking out reservation: " + e.getMessage());
            return false;
        }
    }

    // Closes out the checked-out row once the car has actually been returned
    // (the permanent record lives in rental_history from this point on)
    public boolean completeReturn(String licensePlate) {
        String sql = "DELETE FROM reservations WHERE license_plate = ? AND status = 'CHECKED_OUT'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, licensePlate);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error completing return: " + e.getMessage());
            return false;
        }
    }

    // Used by "Reset All Data" - clears pending/active reservations, keeps rental_history intact
    public void deleteAllReservations() {
        String sql = "DELETE FROM reservations";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error clearing reservations: " + e.getMessage());
        }
    }

    private Reservation mapRow(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("id"),
                rs.getString("license_plate"),
                rs.getString("client"),
                rs.getString("time_of_res"),
                rs.getString("status"),
                rs.getString("activity_type"),
                rs.getString("expected_return_time")
        );
    }
}