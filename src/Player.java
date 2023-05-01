public class Player extends Thread{
    private final String Color;
    Player(String color)
    {
        this.Color = color;
    }

    public String getColor() {
        return Color;
    }
// przesylac dane za pomoca intow np.
    // 0 - puste pole 1 bialy pion 2 biala krolowa 3 czerwony pion 4 czerwona damka itd.
    // do tego watek na odczytywanie timera

}
