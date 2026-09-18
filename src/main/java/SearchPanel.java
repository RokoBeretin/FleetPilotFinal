import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/**
 * Panel za pretragu trajne povijesti iznajmljivanja po (dijelu)
 * registracijske oznake vozila.
 * <p>
 * Pretragu delegira klasi {@link RentalHistoryDAO}, koja izvršava
 * {@code LIKE} upit nad tablicom {@code rental_history}, te ispisuje
 * rezultate u tekstualno polje.
 */
public class SearchPanel extends JPanel {
    private JTextField searchField;
    private JTextArea resultsArea;
    private JButton searchButton;
    private JLabel appLabel;
    private RentalHistoryDAO rentalHistoryDAO;

    public SearchPanel() {
        rentalHistoryDAO = new RentalHistoryDAO();
        initComps();
        layoutComps();
        activatePanel();
    }

    private void initComps() {
        searchField = new JTextField(20);
        resultsArea = new JTextArea(21 , 50);
        resultsArea.setEditable(false);
        resultsArea.setFocusable(false);
        searchButton = new JButton("Search");
        appLabel = new JLabel("FleetPilot");
        appLabel.setHorizontalAlignment(SwingConstants.CENTER);
        appLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        appLabel.setBorder(BorderFactory.createEmptyBorder(1, 0, 10, 0));

    }

    private void layoutComps() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Search:"), gbc);

        gbc.gridx += 1;
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(searchField, gbc);

        gbc.gridx += 1;
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy += 1;

        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(new JScrollPane(resultsArea), gbc);
        gbc.gridy += 1;
        add(appLabel, gbc);
    }

    private void activatePanel() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!searchField.getText().isEmpty()) {
                    String licensePlate = searchField.getText().trim();
                    StringBuilder results = new StringBuilder();

                    List<Activity> activities = rentalHistoryDAO.searchByLicensePlate(licensePlate);
                    for (Activity activity : activities) {
                        results.append(activity.getActivity()).append("\n");
                    }

                    if (activities.isEmpty()) {
                        results.append("No history found for \"").append(licensePlate).append("\".");
                    }

                    resultsArea.setText(results.toString());

                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a license plate to search.", "Input Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }
}