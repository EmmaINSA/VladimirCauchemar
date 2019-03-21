import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PanelIntrument extends JPanel {

    private int instruSelec = 0, width, height;
    private Image bg = null;
    private Image[] bgs = {null, null, null};        // tableau de bgs à sélectionner

    public PanelIntrument() {
        this.init();
    }

    private void init() {
        this.setBackground(Color.BLUE);

        try {
            // a mettre dans le tableau bgs
            this.bgs[Constants.FLUTEABEC] = ImageIO.read(new File("Instrument Pics/flutevuedecoupe.jpg"));
            this.bgs[Constants.FLUTEDEPAN] = ImageIO.read(new File("Instrument Pics/flute de pan VueDeCoupe1.jpg"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setInstruSelec(int instrument) {
        this.instruSelec = instrument;
        try {
            this.bg = this.bgs[instrument];
        }catch (IndexOutOfBoundsException e){
            System.out.println("Instrument pas encore dispo en bg");
        }
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.bg != null){
            Image bgOnScreen = this.bg.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
            g.drawImage(bgOnScreen, 0,0,this);
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setDim(int width, int height){
        this.width = width;
        this.height = height;
        this.setBounds(0,0, this.width, this.height);
    }
}
