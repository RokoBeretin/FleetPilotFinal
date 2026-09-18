import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Alatna traka (JMenuBar) na vrhu glavnog prozora, trenutno s jednim
 * gumbom - "Reset All Data".
 * <p>
 * Ne sadrži poslovnu logiku - klik na gumb samo se prosljeđuje
 * registriranom {@link ToolBarListener}-u ({@code MainFrame}), koji
 * pokreće {@code ResetAllDataCommand}.
 */
public class ToolBar extends JMenuBar implements ActionListener {
    private JButton clearButton;
    private ToolBarListener toolBarListener;

    public ToolBar() {
        initComps();
        layoutComps();
    }

    private void initComps() {
        clearButton = new JButton("Reset All Data");
        clearButton.addActionListener(this);
        clearButton.setActionCommand("Reset All Data");
    }

    private void layoutComps() {
        add(clearButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(toolBarListener != null) {
            toolBarListener.toolBarEventOccured(e.getActionCommand());
        }
    }

    public void setToolBarListener(ToolBarListener toolBarListener) {
        this.toolBarListener = toolBarListener;
    }
}
