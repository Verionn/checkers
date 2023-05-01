import javax.swing.*;
import java.awt.*;

public class EndGamePanel extends JFrame {
    public EndGamePanel(JFrame parent) {
        Game.setMove("NOONE");
        setTitle("GAME OVER");
        setLocationRelativeTo(parent);
        setBounds(450, 450, 300, 300);
        setLayout(null);
        setResizable(false);

        JLabel EndGameInformation = new JLabel();
        if(Game.getWinner().equals("DRAW")){
            EndGameInformation.setText("DRAW");
        }
        else{
            EndGameInformation.setText(Game.getWinner() + " PAWNS WON");
        }


        EndGameInformation.setBounds(90,85, 200, 40);
        EndGameInformation.setBackground(Color.CYAN);
        add(EndGameInformation);

        JButton playButton = new JButton("Play Again");
        playButton.setBounds(12,180, 125, 40);

        playButton.addActionListener(e -> {
            dispose();
            parent.dispose();
            new Menu();
        });
        add(playButton);

        JButton quitButton = new JButton("Leave");
        quitButton.setBounds(162,180, 125, 40);

        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
