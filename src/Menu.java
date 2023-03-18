import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 40;
    private static final int MARGINS = 10;
    private static final String SOLO_GAME = "Play with Computer";
    private static final String DUO_GAME = "Play with friend";
    private static final String ONLINE_GAME = "Play online";
    private static final String BACKGROUND_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/Background_Menu.jpg";
    public static void Run() {

        JFrame frame = new JFrame("Checkers - Menu");
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton Online = new JButton(ONLINE_GAME);
        JButton Duo= new JButton(DUO_GAME);
        JButton Solo = new JButton(SOLO_GAME);

        Solo.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        Duo.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        Online.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(MARGINS, MARGINS, MARGINS, MARGINS);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(Online, gbc);
        buttonPanel.add(Duo, gbc);
        buttonPanel.add(Solo, gbc);

        ImageIcon imageIcon = new ImageIcon(BACKGROUND_IMAGE_PATH);
        JLabel background = new JLabel(imageIcon);

        frame.setContentPane(background);
        frame.setLayout(new GridBagLayout());
        frame.add(buttonPanel);
        frame.setVisible(true);

        Solo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.Run();
            }
        });


        Duo.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Tryb z ziomalem"));

        Online.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Ziomal na drugim kompie"));
    }
}
