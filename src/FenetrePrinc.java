import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


/* A faire :
* - ajouter JMenu
* - le reste
* */

public class FenetrePrinc extends JFrame implements ActionListener{

    protected int x=200, y=50, width = 800, height = 600;    // modifiables via options ?
    protected JMenuBar menuBar;
    protected JMenu menuIntruments, menuOptions, submenuResolution;
    protected JMenuItem itemFluteDePan, itemFluteABec, itemClarinette, reso1000_600, reso600_400;

    public FenetrePrinc() {
        super("Simulateur d'instruments à vent");
        init();
    }

    private void init(){

        this.setBounds(x,y,width,height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.menuBar();

        this.setVisible(true);
    }

    private void menuBar(){

        menuBar = new JMenuBar();

        //menuIntruments : comme "Fichier", avec des sous-composants
        menuIntruments = new JMenu("Intruments");
        // menuIntruments.setMnemonic(KeyEvent.VK_I);        // pour la recherche dans la barre de menu
        menuIntruments.getAccessibleContext().setAccessibleDescription("Choix des instruments");
        menuBar.add(menuIntruments);      // pas oublier

        // flute de pan
        itemFluteDePan = new JMenuItem("Flûte de pan"); // autre argument : KeyEvent.VK_P par ex
        //itemFluteDePan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));      // raccourci clavier ?
        itemFluteDePan.getAccessibleContext().setAccessibleDescription("Flûte de pan pas encore dispo");
        menuIntruments.add(itemFluteDePan);
        itemFluteDePan.addActionListener(this);

        menuIntruments.addSeparator();      // pour séparer vents & cordes ?

        // flute a bec
        itemFluteABec = new JMenuItem("Flûte à bec");
        itemFluteABec.getAccessibleContext().setAccessibleDescription("Flûte à bec pas encore dispo");
        menuIntruments.add(itemFluteABec);
        itemFluteABec.addActionListener(this);


        // options
        menuOptions = new JMenu("Options");
        menuBar.add(menuOptions);

        // résolution
        submenuResolution = new JMenu("Résolution");
        reso600_400 = new JMenuItem("600x400");
        submenuResolution.add(reso600_400);
        reso1000_600 = new JMenuItem("1000x600");
        submenuResolution.add(reso1000_600);
        reso1000_600.addActionListener(this);
        reso600_400.addActionListener(this);
        menuOptions.add(submenuResolution);

        this.setJMenuBar(menuBar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == this.itemFluteABec){
            System.out.println("Flûte à bec");
        }

        else if (source == this.itemFluteDePan){
            System.out.println("Flûte de pan");
        }

        else if (source ==this.reso600_400){
            this.setDim(600,400);
        }

        else if (source == this.reso1000_600){
            this.setDim(1000,600);
        }

        else{
            System.out.println("Pas d'action spécifiée");
        }
    }

    private void setDim(int width, int height){
        this.width = width;
        this.height = height;
        this.setBounds(x, y, width, height);
        System.out.println("SetDim done");
    }
}
