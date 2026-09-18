/**
 * Konkretna naredba (Command pattern) koja stvara novu rezervaciju tipa
 * "Reservation" - vozilo koje čeka preuzimanje.
 * <p>
 * Pokreće ju {@code NewActivityPanel} kad korisnik odabere aktivnost
 * "Reservation". Kao receiver koristi {@link ReservationDAO} (spremanje
 * rezervacije) i {@link CarDAO} (označavanje vozila kao nedostupnog).
 */
public class CreateReservationCommand implements Command {
    private final ReservationDAO reservationDAO;
    private final CarDAO carDAO;
    private final String licensePlate;
    private final String client;
    private final String time;

    public CreateReservationCommand(ReservationDAO reservationDAO, CarDAO carDAO,
        String licensePlate, String client, String time) {
        this.reservationDAO = reservationDAO;
        this.carDAO = carDAO;
        this.licensePlate = licensePlate;
        this.client = client;
        this.time = time;
    }

    @Override
    public boolean execute() {
        Reservation reservation = new Reservation(licensePlate, client, time);
        boolean saved = reservationDAO.saveReservation(reservation);
        carDAO.updateCarAvailability(licensePlate, false);
        return saved;
    }
}
