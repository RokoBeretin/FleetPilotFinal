import java.util.EventListener;
/**
 * Listener sučelje koje omogućuje {@code FromPanel} (traka s glavnim
 * gumbima: Reservations, Returns, Checkout, New Activity, Search) da
 * obavijesti {@code MainFrame} o odabiru korisnika, bez da panel izravno
 * poznaje glavni prozor.
 * <p>
 * Ovo je primjena Observer ideje unutar projekta: {@code FromPanel} je
 * "subjekt" koji samo emitira događaj, a registrirani slušatelj
 * ({@code MainFrame}) odlučuje kako će na njega reagirati (prikaz
 * odgovarajućeg panela).
 */
public interface MainPanelListener extends EventListener {
    void mainPanelOccured(String event);
}
