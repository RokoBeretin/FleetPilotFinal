import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FromPanel extends JPanel implements ActionListener {
    JButton reservations;
    JButton returns;
    JButton checkout;
    JButton newActivity;
    JButton search;

    private MainPanelListener mainPanelListener;

    public FromPanel() {
        initComps();
        layoutComps();
    }

    private void initComps() {
        Dimension buttonSize = new Dimension(150, 50);
        reservations = new JButton("Reservations");
        returns = new JButton("Returns");
        checkout = new JButton("Checkout");
        newActivity = new JButton("New Activity");
        search = new JButton("Search");
        reservations.setPreferredSize(buttonSize);
        returns.setPreferredSize(buttonSize);
        checkout.setPreferredSize(buttonSize);
        newActivity.setPreferredSize(buttonSize);
        search.setPreferredSize(buttonSize);

        reservations.addActionListener(this);
        returns.addActionListener(this);
        checkout.addActionListener(this);
        newActivity.addActionListener(this);
        search.addActionListener(this);

        reservations.setActionCommand("Reservations");
        returns.setActionCommand("Returns");
        checkout.setActionCommand("Checkout");
        newActivity.setActionCommand("New Activity");
        search.setActionCommand("Search");
    }

    private void layoutComps() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(reservations, gbc);

        gbc.gridx += 1;

        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(returns, gbc);

        gbc.gridx += 1;

        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(checkout, gbc);

        gbc.gridx += 1;

        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(newActivity, gbc);

        gbc.gridx += 1;

        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(search, gbc);

        gbc.gridx += 1;
    }

    public void setMainPanelListener(MainPanelListener mainPanelListener) {
        this.mainPanelListener = mainPanelListener;
    }


    public void actionPerformed(ActionEvent e) {
        if (mainPanelListener != null) {
            mainPanelListener.mainPanelOccured(e.getActionCommand());
        } else {
            System.err.println("No listener registered");
        }
    }
}


