import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NewActivityPanel extends JPanel {
    private JTextField timeOfRes;
    private JComboBox<String> activityCombo;
    private JTextField clientTextField;
    private JComboBox<String> vehicleCombo;
    private JButton submitButton;
    private NewActivityPanelListener newActivityListener;
    private CarDAO carDAO;
    private ReservationDAO reservationDAO;

    public NewActivityPanel() {
        carDAO = new CarDAO();
        reservationDAO = new ReservationDAO();

        initComps();
        layoutComps();
        acivateNAPanel();
    }

    public void setNewActivityListener(NewActivityPanelListener newActivityListener) {
        this.newActivityListener = newActivityListener;
    }

    private void initComps() {
        // Unosna polja
        timeOfRes = new JTextField(30);
        timeOfRes.setToolTipText("YYYY-MM-DD HH:MM");

        activityCombo = new JComboBox<>();
        DefaultComboBoxModel<String> activityModel = new DefaultComboBoxModel<>();
        activityModel.addElement("Reservation");
        activityModel.addElement("Car Wash");
        activityModel.addElement("Gas Refill");
        activityCombo.setModel(activityModel);
        activityCombo.setSelectedIndex(0);

        clientTextField = new JTextField(30);

        DefaultComboBoxModel<String> vehicleModel = new DefaultComboBoxModel<>();
        List<Car> carsList = carDAO.getAllCars();

        for (Car car : carsList) {
            if (car.isAvailable()) {
                vehicleModel.addElement(car.getLicensePlate() + " | " + car.getModel());
            }
        }

        vehicleCombo = new JComboBox<>(vehicleModel);

        submitButton = new JButton("Finish");
        submitButton.setPreferredSize(new Dimension(200, 50));
        submitButton.setMargin(new Insets(10, 20, 10, 20));
    }

    private void layoutComps() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(activityCombo, gbc);

        gbc.gridy += 1;
        add(vehicleCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Client:"), gbc);

        gbc.gridx += 1;
        add(clientTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy += 1;
        add(new JLabel("Time of Pick-up (YYYY-MM-DD HH:MM):"), gbc);

        gbc.gridx += 1;
        add(timeOfRes, gbc);

        gbc.gridy += 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(200, 10, 10, 10);
        add(submitButton, gbc);
    }

    private void acivateNAPanel() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activityCombo.getSelectedItem() != null &&
                        vehicleCombo.getSelectedItem() != null &&
                        !clientTextField.getText().trim().isEmpty()) {

                    String activity = (String) activityCombo.getSelectedItem();
                    String vehicle = (String) vehicleCombo.getSelectedItem();
                    String client = clientTextField.getText().trim();
                    String time = timeOfRes.getText().trim();

                    String licensePlate = vehicle.split("\\|")[0].trim();

                    if ("Reservation".equals(activity)) {
                        Reservation reservation = new Reservation(licensePlate, client, time);
                        reservationDAO.saveReservation(reservation);
                    }

                    carDAO.updateCarAvailability(licensePlate, false);

                    if (newActivityListener != null) {
                        String activityFinal = vehicle + " | " + client + " | " + activity + " | " + time;
                        newActivityListener.newActivityPanelOccured(activityFinal);
                    }

                    vehicleCombo.removeItem(vehicle);
                    clientTextField.setText("");
                    timeOfRes.setText("");

                } else {
                    JOptionPane.showMessageDialog(NewActivityPanel.this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public String getActivityCombo() {
        return (String) activityCombo.getSelectedItem();
    }
}