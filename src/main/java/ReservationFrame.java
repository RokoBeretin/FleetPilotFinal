import com.github.lgooddatepicker.components.DateTimePicker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationFrame extends JFrame {
    private JCheckBox depositCheckBox;
    private JCheckBox paidCheckBox;
    private JCheckBox TermsCheckBox;
    private DateTimePicker timeOfReturn;
    private JButton submitButton;

    public ReservationFrame(String name) {
        super(name);
        initMainFrame();
        initComps();
        layoutComps();
        activateFrame();
    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initComps() {
        depositCheckBox = new JCheckBox("Deposit");
        paidCheckBox = new JCheckBox("Paid");
        TermsCheckBox = new JCheckBox("Terms & Conditions");
        timeOfReturn = new DateTimePicker();
        submitButton = new JButton("Finish");
    }

    private void layoutComps() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(depositCheckBox, gbc);

        gbc.gridy += 1;

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(paidCheckBox, gbc);

        gbc.gridy += 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(TermsCheckBox, gbc);

        gbc.gridy += 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(new JLabel("Time of Return:"), gbc);

        gbc.gridy += 1;

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(timeOfReturn, gbc);

        gbc.gridy += 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(submitButton, gbc);
    }

    private void activateFrame() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (depositCheckBox.isSelected() && paidCheckBox.isSelected() && TermsCheckBox.isSelected() && timeOfReturn.getDateTimePermissive() != null) {
                    String title = ReservationFrame.this.getTitle();
                    LocalDateTime dateTime = timeOfReturn.getDateTimePermissive();

                    String[] titleParts = title.split("\\|");
                    if (titleParts.length >= 3) {
                        String pickupTimeText = titleParts[2].trim();
                        try {
                            LocalDateTime pickupTime = LocalDateTime.parse(pickupTimeText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                            if (dateTime.isBefore(pickupTime)) {
                                JOptionPane.showMessageDialog(ReservationFrame.this, "Return time cannot be earlier than the car's pickup time (" + pickupTimeText + ").", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } catch (DateTimeParseException ex) {
                            System.err.println("Error parsing pickup time: " + ex.getMessage());
                        }
                    }

                    String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    String activityFinal = title + " | " + formattedTime;

                    AUX_CLS.writeToTxt(activityFinal, "src/main/returns.txt");
                    AUX_CLS.writeToTxt(activityFinal, "src/main/checkout.txt");

                    String licensePlate = title.split("\\|")[0].trim();

                    ReservationDAO reservationDAO = new ReservationDAO();
                    reservationDAO.updateReservationStatus(licensePlate, "COMPLETED");

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ReservationFrame.this, "Please complete all fields and check all boxes.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}