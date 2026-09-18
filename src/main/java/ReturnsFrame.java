import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Zaseban prozor (JFrame) za unos podataka pri fizičkom povratu vozila -
 * prijeđena kilometraža i razina goriva.
 * <p>
 * Ne izvršava poslovnu logiku izravno, već po potvrdi pokreće
 * {@link CompleteReturnCommand} (invoker u Command dizajnerskom obrascu)
 * koji upisuje trajni zapis u {@code rental_history}, oslobađa vozilo i
 * zatvara rezervaciju.
 */
public class ReturnsFrame extends JFrame {
    private JTextField kilometersField;
    private JTextField fuelField;
    private JButton submitButton;
    private CarDAO carDAO;
    private ReservationDAO reservationDAO;
    private RentalHistoryDAO rentalHistoryDAO;

    public ReturnsFrame(String name) {
        super(name);
        carDAO = new CarDAO();
        reservationDAO = new ReservationDAO();
        rentalHistoryDAO = new RentalHistoryDAO();
        initMainFrame();
        initComps();
        layoutComps();
        activateFrame();
    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(250, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initComps() {
        kilometersField = new JTextField(10);
        fuelField = new JTextField(10);
        submitButton = new JButton("Finish");
    }

    private void layoutComps() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(new JLabel("Kilometers:"), gbc);

        gbc.gridx = 1;
        add(kilometersField, gbc);

        gbc.gridx = 0;
        gbc.gridy += 1;

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(new JLabel("Fuel Level:"), gbc);

        gbc.gridx = 1;
        add(fuelField, gbc);

        gbc.gridx = 0;
        gbc.gridy += 1;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(40, 10, 10, 10);
        add(submitButton, gbc);
    }

    private void activateFrame() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kmText = kilometersField.getText().trim();
                String fuelText = fuelField.getText().trim();

                if (kmText.isEmpty() || fuelText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            ReturnsFrame.this,
                            "Please fill in all fields.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    String rawTitle = ReturnsFrame.this.getTitle();
                    String licensePlate = rawTitle.split("\\|")[0].trim();

                    int kilometers;
                    try {
                        kilometers = Integer.parseInt(kmText);
                    } catch (NumberFormatException ex) {
                        System.err.println("Invalid kilometers format.");
                        kilometers = 0;
                    }

                    new CompleteReturnCommand(carDAO, reservationDAO, rentalHistoryDAO, licensePlate, kilometers, fuelText).execute();

                    dispose();
                }
            }
        });
    }
}