import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private FromPanel fromPanel;
    private JPanel contentPanel;
    private ToolBar toolBar;

    public MainFrame() {
        super("FleetPilot");
        initMainFrame();
        initComps();
        layoutComps();
        activeFrame();
    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(630, 565);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initComps() {
        contentPanel = new JPanel();
        fromPanel = new FromPanel();
        toolBar = new ToolBar();
    }

    private void layoutComps() {
        setLayout(new BorderLayout());
        add(fromPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        contentPanel.add(createReservationsPanel());
        setJMenuBar(toolBar);
    }

    private void activeFrame() {
        fromPanel.setMainPanelListener(new MainPanelListener() {
            @Override
            public void mainPanelOccured(String event) {
                refreshFrame();
                switch (event) {
                    case "Reservations":
                        contentPanel.add(createReservationsPanel());
                        break;
                    case "Returns":
                        contentPanel.add(createReturnsPanel());
                        break;
                    case "Checkout":
                        contentPanel.add(new CheckoutPanel());
                        break;
                    case "New Activity":
                        contentPanel.add(createNewActivityPanel());
                        break;
                    case "Search":
                        contentPanel.add(new SearchPanel());
                        break;
                }
            }
        });

        toolBar.setToolBarListener(new ToolBarListener() {
            @Override
            public void toolBarEventOccured(String event) {
                if (event.equals("Reset All Data")) {
                    int response = JOptionPane.showConfirmDialog(
                            MainFrame.this,
                            "Are you sure you want to reset all data? This action cannot be undone.",
                            "Confirm Reset",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (response == JOptionPane.YES_OPTION) {
                        AUX_CLS.resetAllData();
                        refreshFrame();
                        contentPanel.add(createReservationsPanel());
                    }
                }
            }
        });
    }

    private void refreshFrame() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private ReservationsPanel createReservationsPanel() {
        ReservationsPanel panel = new ReservationsPanel();
        panel.setReservationClickListener(new ReservationClickListener() {
            @Override
            public void reservationClickEventOccurred(String reservation) {
                ReservationFrame frame = new ReservationFrame(reservation);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshFrame();
                        contentPanel.add(createReservationsPanel());
                    }
                });
            }
        });
        return panel;
    }

    private ReturnsPanel createReturnsPanel() {
        ReturnsPanel panel = new ReturnsPanel();
        panel.setReturnClickListener(new ReturnClickListener() {
            @Override
            public void returnClickEventOccurred(String returns) {
                ReturnsFrame frame = new ReturnsFrame(returns);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        refreshFrame();
                        contentPanel.add(createReturnsPanel());
                    }
                });
            }
        });
        return panel;
    }

    private NewActivityPanel createNewActivityPanel() {
        return new NewActivityPanel();
    }
}