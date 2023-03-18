import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame{

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 40;
    private static final int MARGINS = 10;
    private static String SOLO_GAME = "Play with Computer";
    private static String DUO_GAME = "Play with friend";
    private static String ONLINE_GAME = "Play online";
    public static void run()
    {

        JFrame frame = new JFrame("Checkers");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //ImageIcon backgroundImg = new ImageIcon("Background_Menu.jpg");
        //JLabel backgroundLabel = new JLabel(backgroundImg);
        //backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        //frame.getContentPane().add(backgroundLabel);

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
        gbc.insets = new Insets(MARGINS, MARGINS, MARGINS, MARGINS); // marginesy

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(Online, gbc);
        buttonPanel.add(Duo, gbc);
        buttonPanel.add(Solo, gbc);

        frame.setLayout(new GridBagLayout());
        frame.add(buttonPanel);
    }
}
