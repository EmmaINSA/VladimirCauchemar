import javax.swing.*;
import java.awt.*;


/* A faire :
* - ajouter JMenu
* - le reste
* */

public class FenetrePrinc extends JFrame {

    protected int x=50, y=50, width = 800, height = 600;    // modifiables via options ?

    public FenetrePrinc() {
        super("Simulateur d'instruments à vent");
        init();
    }

    private void init(){

        this.setBounds(x,y,width,height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
