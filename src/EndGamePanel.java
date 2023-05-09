import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EndGamePanel extends JFrame {

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean Online = false;

    public EndGamePanel(JFrame parent, String Winner, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        setTitle("GAME OVER");
        setLocationRelativeTo(parent);
        setBounds(450, 450, 300, 300);
        setLayout(null);
        setResizable(false);
        Online = true;
        createWindow(Winner, parent);
    }

    public EndGamePanel(JFrame parent, String Winner) {
        Game.setMove("NOONE");
        setTitle("GAME OVER");
        setLocationRelativeTo(parent);
        setBounds(450, 450, 300, 300);
        setLayout(null);
        setResizable(false);

        createWindow(Winner, parent);
    }

    private void createWindow(String Winner, JFrame parent){
        JLabel EndGameInformation = new JLabel();
        if(Winner.equals("DRAW"))
        {
            EndGameInformation.setText("          DRAW");
        }
        else{
            EndGameInformation.setText(Winner + " PAWNS WON");
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
            if(Online){
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(playButton);

        JButton quitButton = new JButton("Leave");
        quitButton.setBounds(162,180, 125, 40);

        quitButton.addActionListener(e -> {
            if(Online){
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.exit(0);
        });
        add(quitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
