import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Panel koji prikazuje pregled trenutnog stanja i povijesti
 * iznajmljivanja: vozila koja su trenutno preuzeta i čekaju povrat (iz
 * {@link ReservationDAO}), zajedno s cjelokupnom trajnom poviješću
 * završenih iznajmljivanja (iz {@link RentalHistoryDAO}).
 */
public class CheckoutPanel extends JPanel {
    private List<String> checkOutList;
    private JScrollPane scrollPane;
    private JLabel appLabel;
    private ReservationDAO reservationDAO;
    private RentalHistoryDAO rentalHistoryDAO;

    public CheckoutPanel() {
        reservationDAO = new ReservationDAO();
        rentalHistoryDAO = new RentalHistoryDAO();
        initComps();
        layoutComps();
    }

    private void initComps() {
        setLayout(new BorderLayout());
        appLabel = new JLabel("FleetPilot");
        appLabel.setHorizontalAlignment(SwingConstants.CENTER);
        appLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        appLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        checkOutList = new ArrayList<>();

        for (Reservation reservation : reservationDAO.getAllCheckedOutReservations()) {
            checkOutList.add(reservation.getLicensePlate() + " | " + reservation.getClient() +
                    " | " + reservation.getActivityType() + " | Picked up: " + reservation.getTimeOfRes() +
                    " (awaiting return)");
        }

        for (Activity activity : rentalHistoryDAO.getAllHistory()) {
            checkOutList.add(activity.getActivity());
        }

        JPanel buttonListPanel = new JPanel();
        buttonListPanel.setLayout(new BoxLayout(buttonListPanel, BoxLayout.Y_AXIS));
        buttonListPanel.setBackground(Color.WHITE);

        for (String checkOut : checkOutList) {
            JButton button = new JButton(checkOut);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(750, 30));
            button.setFocusPainted(false);

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
}