/**
 * Konkretna naredba (Command pattern) koja stvara izravnu aktivnost tipa
 * "Car Wash" ili "Gas Refill" - za razliku od
 * {@link CreateReservationCommand}, ova naredba preskače korak čekanja
 * preuzimanja i odmah stvara stavku sa statusom {@code CHECKED_OUT}.
 * <p>
 * Pokreće ju {@code NewActivityPanel} kad korisnik odabere aktivnost
 * "Car Wash" ili "Gas Refill". Kao receiver koristi {@link ReservationDAO}
 * i {@link CarDAO}.
 */
public class CreateServiceActivityCommand implements Command {
    private final ReservationDAO reservationDAO;
    private final CarDAO carDAO;
    private final String licensePlate;
    private final String client;
    private final String time;
    private final String activityType;

    public CreateServiceActivityCommand(ReservationDAO reservationDAO, CarDAO carDAO,
        String licensePlate, String client, String time, String activityType) {
        this.reservationDAO = reservationDAO;
        this.carDAO = carDAO;
        this.licensePlate = licensePlate;
        this.client = client;
        this.time = time;
        this.activityType = activityType;
    }

    @Override
    public boolean execute() {
        Reservation directCheckout = new Reservation(licensePlate, client, time, activityType, "CHECKED_OUT");
        boolean saved = reservationDAO.saveReservation(directCheckout);
        carDAO.updateCarAvailability(licensePlate, false);
        return saved;
    }
}