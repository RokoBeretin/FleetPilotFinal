import java.util.EventListener;
/**
 * Listener sučelje koje omogućuje {@code ReservationsPanel} da obavijesti
 * {@code MainFrame} o kliku korisnika na pojedinu rezervaciju u popisu,
 * kako bi se otvorio {@code ReservationFrame} za tu stavku.
 */
public interface ReservationClickListener extends EventListener {
    void reservationClickEventOccurred(String event);
}
