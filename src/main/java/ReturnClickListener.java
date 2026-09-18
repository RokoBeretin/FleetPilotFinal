import java.util.EventListener;
/**
 * Listener sučelje koje omogućuje {@code ReturnsPanel} da obavijesti
 * {@code MainFrame} o kliku korisnika na pojedino vozilo koje čeka
 * povrat, kako bi se otvorio {@code ReturnsFrame} za tu stavku.
 */
public interface ReturnClickListener extends EventListener {
    void returnClickEventOccurred(String returnInfo);
}
