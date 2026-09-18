import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
/**
 * Konkretna naredba (Command pattern) koja završava jedno iznajmljivanje
 * u trenutku fizičkog povrata vozila.
 * <p>
 * Objedinjuje tri koraka u jednu operaciju: upis trajnog zapisa u
 * {@code rental_history}, oslobađanje vozila (postavljanje na dostupno) i
 * zatvaranje (brisanje) retka u {@code reservations}. Pokreće ju
 * {@code ReturnsFrame} nakon unosa kilometraže i razine goriva. Kao
 * receivere koristi {@link CarDAO}, {@link ReservationDAO} i
 * {@link RentalHistoryDAO}.
 */
public class CompleteReturnCommand implements Command {
    private final CarDAO carDAO;
    private final ReservationDAO reservationDAO;
    private final RentalHistoryDAO rentalHistoryDAO;
    private final String licensePlate;
    private final int kilometers;
    private final String fuelLevel;

    public CompleteReturnCommand(CarDAO carDAO, ReservationDAO reservationDAO, RentalHistoryDAO rentalHistoryDAO,
                                 String licensePlate, int kilometers, String fuelLevel) {
        this.carDAO = carDAO;
        this.reservationDAO = reservationDAO;
        this.rentalHistoryDAO = rentalHistoryDAO;
        this.licensePlate = licensePlate;
        this.kilometers = kilometers;
        this.fuelLevel = fuelLevel;
    }

    @Override
    public boolean execute() {
        Optional<Reservation> reservationOpt = reservationDAO.getReservationByLicensePlateAndStatus(licensePlate, "CHECKED_OUT");
        Optional<Car> carOpt = carDAO.getCarByLicensePlate(licensePlate);

        String client = reservationOpt.map(Reservation::getClient).orElse("Unknown");
        String activityType = reservationOpt.map(Reservation::getActivityType).orElse("RESERVATION");
        String checkoutTime = reservationOpt.map(Reservation::getTimeOfRes).orElse("");
        String model = carOpt.map(Car::getModel).orElse("");

        String returnTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        boolean saved = rentalHistoryDAO.saveEntry(licensePlate, model, client, activityType, checkoutTime, returnTime, kilometers, fuelLevel);

        carDAO.updateCarAvailability(licensePlate, true);
        reservationDAO.completeReturn(licensePlate);

        return saved;
    }
}