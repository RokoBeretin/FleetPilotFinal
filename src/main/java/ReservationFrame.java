import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationFrame extends JFrame {
    private JCheckBox depositCheckBox;
    private JCheckBox paidCheckBox;
    private JCheckBox TermsCheckBox;
    private JTextField timeOfReturn;
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
        timeOfReturn = new JTextField(20);
        timeOfReturn.setToolTipText("YYYY-MM-DD HH:MM");
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
        add(new JLabel("Time of Return (YYYY-MM-DD HH:MM):"), gbc);

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
                if (depositCheckBox.isSelected() && paidCheckBox.isSelected() && TermsCheckBox.isSelected() && !timeOfReturn.getText().trim().isEmpty()) {
                    String title = ReservationFrame.this.getTitle();
                    String activityFinal = title + " | " + timeOfReturn.getText().trim();

                    AUX_CLS.writeToTxt(activityFinal, "PROJECT/returns.txt");
                    AUX_CLS.writeToTxt(activityFinal, "PROJECT/checkout.txt");

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