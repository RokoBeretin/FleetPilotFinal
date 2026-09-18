import java.util.List;
/**
 * Konkretna naredba (Command pattern) koja resetira sve trenutno aktivne
 * podatke aplikacije - sva vozila se postavljaju na dostupna, a sve
 * rezervacije (aktivne i preuzete) se brišu.
 * <p>
 * Pokreće ju {@code ToolBar} na potvrdu gumba "Reset All Data". Tablica
 * {@code rental_history} namjerno ostaje netaknuta jer predstavlja trajni
 * revizijski zapis. Kao receivere koristi {@link CarDAO} i
 * {@link ReservationDAO}.
 */
public class ResetAllDataCommand implements Command {
    private final CarDAO carDAO;
    private final ReservationDAO reservationDAO;

    public ResetAllDataCommand(CarDAO carDAO, ReservationDAO reservationDAO) {
        this.carDAO = carDAO;
        this.reservationDAO = reservationDAO;
    }

    @Override
    public boolean execute() {
        List<Car> cars = carDAO.getAllCars();
        for (Car car : cars) {
            carDAO.updateCarAvailability(car.getLicensePlate(), true);
        }

        reservationDAO.deleteAllReservations();
        System.out.println("All data reset successfully in database.");
        return true;
    }
}