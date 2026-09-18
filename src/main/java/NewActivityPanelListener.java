import java.util.EventListener;
/**
 * Listener sučelje koje omogućuje {@code NewActivityPanel} da obavijesti
 * {@code MainFrame} nakon što je nova aktivnost uspješno stvorena (npr.
 * radi osvježavanja drugih panela).
 */
public interface NewActivityPanelListener extends EventListener {
    void newActivityPanelOccured(String event);
}
