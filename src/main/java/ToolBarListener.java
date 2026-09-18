import java.util.EventListener;
/**
 * Listener sučelje koje omogućuje {@code ToolBar} (traka s gumbom
 * "Reset All Data") da obavijesti {@code MainFrame} o kliku na gumb, bez
 * da traka izravno poznaje glavni prozor ili DAO/Command slojeve.
 */
public interface ToolBarListener extends EventListener {
    void toolBarEventOccured(String event);
}
