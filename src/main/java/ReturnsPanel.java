import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * Panel koji prikazuje popis svih vozila koja su trenutno preuzeta i
 * čekaju povrat (status {@code CHECKED_OUT}).
 * <p>
 * Podatke dohvaća izravno preko {@link ReservationDAO#getAllCheckedOutReservations()}.
 * Klik na pojedinu stavku prosljeđuje se registriranom
 * {@link ReturnClickListener}-u ({@code MainFrame}), koji otvara
 * {@code ReturnsFrame} za tu stavku.
 */
public class ReturnsPanel extends JPanel {
    private List<Reservation> checkedOutList;
    private JScrollPane scrollPane;
    private ReturnClickListener returnsClickListener;
    private JLabel appLabel;
    private ReservationDAO reservationDAO;

    public ReturnsPanel() {
        reservationDAO = new ReservationDAO();
        initComps();
        layoutComps();
    }

    private void initComps() {
        setLayout(new BorderLayout());
        appLabel = new JLabel("FleetPilot");
        appLabel.setHorizontalAlignment(SwingConstants.CENTER);
        appLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        appLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        checkedOutList = reservationDAO.getAllCheckedOutReservations();

        JPanel buttonListPanel = new JPanel();
        buttonListPanel.setLayout(new BoxLayout(buttonListPanel, BoxLayout.Y_AXIS));
        buttonListPanel.setBackground(Color.WHITE);

        for (Reservation reservation : checkedOutList) {
            String returnTime = reservation.getExpectedReturnTime() != null
                    ? reservation.getExpectedReturnTime()
                    : reservation.getTimeOfRes();

            String displayText = reservation.getLicensePlate() + " | " + reservation.getClient() +
                    " | " + reservation.getActivityType() + " | " + returnTime;

            JButton button = new JButton(displayText);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(750, 30));
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                if (returnsClickListener != null) {
                    returnsClickListener.returnClickEventOccurred(displayText);
                }
            });

            buttonListPanel.add(button);
            buttonListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        scrollPane = new JScrollPane(buttonListPanel);
        scrollPane.setPreferredSize(new Dimension(560, 400));
    }

    private void layoutComps() {
        add(scrollPane);
        add(appLabel, BorderLayout.SOUTH);
    }

    public void setReturnClickListener(ReturnClickListener listener) {
        this.returnsClickListener = listener;
    }
}