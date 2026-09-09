import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReservationsPanel extends JPanel {
    private List<Reservation> reservationsList;
    private JScrollPane scrollPane;
    private ReservationClickListener reservationClickListener;
    private JLabel appLabel;
    private ReservationDAO reservationDAO;

    public ReservationsPanel() {
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

        reservationsList = reservationDAO.getAllActiveReservations();

        JPanel buttonListPanel = new JPanel();
        buttonListPanel.setLayout(new BoxLayout(buttonListPanel, BoxLayout.Y_AXIS));
        buttonListPanel.setBackground(Color.WHITE);

        for (Reservation reservation : reservationsList) {
            String displayText = reservation.getLicensePlate() + " | " + reservation.getClient() + " | " + reservation.getTimeOfRes();

            JButton button = new JButton(displayText);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(750, 30));
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                if (reservationClickListener != null) {
                    reservationClickListener.reservationClickEventOccurred(displayText);
                }
            });

            buttonListPanel.add(button);
            buttonListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        scrollPane = new JScrollPane(buttonListPanel);
        scrollPane.setPreferredSize(new Dimension(560, 400));
    }

    private void layoutComps() {
        add(scrollPane, BorderLayout.CENTER);
        add(appLabel, BorderLayout.SOUTH);
    }

    public void setReservationClickListener(ReservationClickListener listener) {
        this.reservationClickListener = listener;
    }
}