import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReturnsPanel extends JPanel {
    private ArrayList<String> returnsList;
    private JScrollPane scrollPane;
    private ReturnClickListener returnsClickListener;
    private JLabel appLabel;

    public ReturnsPanel() {
        initComps();
        layoutComps();
    }

    private void initComps() {
        setLayout(new BorderLayout());
        appLabel = new JLabel("FleetPilot");
        appLabel.setHorizontalAlignment(SwingConstants.CENTER);
        appLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        appLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        returnsList = AUX_CLS.readLinesFromTxt("PROJECT/returns.txt");

        JPanel buttonListPanel = new JPanel();
        buttonListPanel.setLayout(new BoxLayout(buttonListPanel, BoxLayout.Y_AXIS));
        buttonListPanel.setBackground(Color.WHITE);

        for (String returns : returnsList) {
            JButton button = new JButton(returns);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(750, 30));
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                if (returnsClickListener != null) {
                    returnsClickListener.returnClickEventOccurred(returns);
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
