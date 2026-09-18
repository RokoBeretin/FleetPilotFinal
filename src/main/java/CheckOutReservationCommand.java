/**
 * Konkretna naredba (Command pattern) koja prebacuje rezervaciju iz
 * statusa {@code ACTIVE} u {@code CHECKED_OUT} u trenutku kada klijent
 * stvarno preuzme vozilo.
 * <p>
 * Pokreće ju {@code ReservationFrame} nakon potvrde depozita, plaćanja i
 * uvjeta korištenja. Kao receiver koristi {@link ReservationDAO}.
 */
public class CheckOutReservationCommand implements Command {
    private final ReservationDAO reservationDAO;
    private final String licensePlate;
    private final String expectedReturnTime;

    public CheckOutReservationCommand(ReservationDAO reservationDAO, String licensePlate, String expectedReturnTime) {
        this.reservationDAO = reservationDAO;
        this.licensePlate = licensePlate;
        this.expectedReturnTime = expectedReturnTime;
    }

    @Override
    public boolean execute() {
        return reservationDAO.checkOutReservation(licensePlate, expectedReturnTime);
    }
}