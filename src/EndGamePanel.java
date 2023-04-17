import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGamePanel extends JFrame {
    public EndGamePanel(JFrame parent) {
        setTitle("GAME OVER");
        setLocationRelativeTo(parent);
        setBounds(450, 450, 300, 300);
        setLayout(null);
        setResizable(false);

        JLabel EndGameInformation = new JLabel();
        EndGameInformation.setText(Game.WINNER + " PAWNS WON");
        EndGameInformation.setBounds(90,85, 200, 40);
        EndGameInformation.setBackground(Color.CYAN);
        add(EndGameInformation);

        JButton playButton = new JButton("Play Again");
        playButton.setBounds(12,180, 125, 40);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu.GameThread.interrupt();
                new Menu();
            }
        });

        add(playButton);

        JButton quitButton = new JButton("Leave");
        quitButton.setBounds(162,180, 125, 40);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(quitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
