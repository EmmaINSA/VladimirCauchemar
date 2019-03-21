import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


/* A faire :
* - les panels principaux avec tout le reste
*
* La morale de cette histoire :
* "Quand tu as un probème que tu ne sais pas résoudre, contourne-le !"
* Grand maître anonyme 2019
* */

public class FenetrePrinc extends JFrame implements ActionListener{

    protected int instruSelec=0, x=200, y=50, width = 800, height = 600, instruWidth, instruHeight;    // modifiables via options
    protected JMenuBar menuBar;
    protected JMenu menuInstruments, menuOptions, submenuResolution, menuAbout;
    protected JMenuItem itemFluteDePan, itemFluteABec, itemClarinette, itemHautbois, itemOrgue,
            reso1000_600, reso600_400, itemInspi;
    protected PanelInstrument panelInstru;
    protected JPanel mainPanel;

    public FenetrePrinc() {
        super("Simulateur d'instruments à vent");
        init();
    }

    private void init(){

        // fenetre
        this.setBounds(x,y,width,height);
        this.instruWidth = 2*width/3;
        this.instruHeight = 2*height/3;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // panels
        this.panelInstru = new PanelInstrument();
        panelInstru.setDim(instruWidth, instruHeight);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);
        this.mainPanel.setBounds(0,0,width,height);
        this.mainPanel.add(this.panelInstru);
        this.setContentPane(this.mainPanel);

        this.setInstruSelec(0);

        // barre de menu
        this.menuBar();

        this.setVisible(true);
    }

    private void menuBar(){

        menuBar = new JMenuBar();

        //menuInstruments : comme "Fichier", avec des sous-composants
        menuInstruments = new JMenu("Instruments");
        // menuInstruments.setMnemonic(KeyEvent.VK_I);        // pour la recherche dans la barre de menu
        menuInstruments.getAccessibleContext().setAccessibleDescription("Choix des instruments");
        menuBar.add(menuInstruments);      // pas oublier

        // flute de pan
        itemFluteDePan = new JMenuItem("Flûte de pan"); // autre argument : KeyEvent.VK_P par ex
        //itemFluteDePan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));      // raccourci clavier ?
//        itemFluteDePan.getAccessibleContext().setAccessibleDescription("Flûte de pan pas encore dispo");
        menuInstruments.add(itemFluteDePan);
        itemFluteDePan.addActionListener(this);

        // flute a bec
        itemFluteABec = new JMenuItem("Flûte à bec");
//        itemFluteABec.getAccessibleContext().setAccessibleDescription("Flûte à bec pas encore dispo");
        menuInstruments.add(itemFluteABec);
        itemFluteABec.addActionListener(this);

        menuInstruments.addSeparator();      // pour séparer vents & cordes ?

        // clarinette
        itemClarinette = new JMenuItem("Clarinette");
        menuInstruments.add(itemClarinette);
        itemClarinette.addActionListener(this);

        // hautbois
        itemHautbois = new JMenuItem("Hautbois");
        menuInstruments.add(itemHautbois);
        itemHautbois.addActionListener(this);

        // orgue
        itemOrgue = new JMenuItem("Orgue");
        menuInstruments.add(itemOrgue);
        itemOrgue.addActionListener(this);

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

        // autres
        menuAbout = new JMenu("About");
        itemInspi = new JMenuItem("Inspiration");
        itemInspi.addActionListener(this);
        menuAbout.add(itemInspi);
        menuBar.add(menuAbout);

        this.setJMenuBar(menuBar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == this.itemFluteABec){
            System.out.println("Flûte à bec");
            this.setInstruSelec(Constants.FLUTEABEC);

        }

        else if (source == this.itemFluteDePan){
            System.out.println("Flûte de pan");
            this.setInstruSelec(Constants.FLUTEDEPAN);

        }

        else if (source == this.itemClarinette){
            System.out.println("Clarinette");
            this.setInstruSelec(Constants.CLARINETTE);
        }

        else if (source == this.itemHautbois){
            System.out.println("Hautbois");
            this.setInstruSelec(Constants.HAUTBOIS);
        }

        else if (source == this.itemOrgue){
            System.out.println("Orgue");
            this.setInstruSelec(Constants.ORGUE);
        }

        else if (source ==this.reso600_400){
            this.setDim(600,400);
        }

        else if (source == this.reso1000_600){
            this.setDim(1000,600);
        }

        else if (source == this.itemInspi){
            try {
                java.awt.Desktop.getDesktop().browse(new URI("https://youtu.be/hpjV962DLWs"));
            }catch (URISyntaxException e2){
                e2.printStackTrace();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }

        else{
            System.out.println("Pas d'action spécifiée");
        }
    }

    private void setDim(int width, int height){
        if (this.width != width && this.height != height) {
            this.width = width;
            this.height = height;
            this.instruWidth = 2 * width / 3;
            this.instruHeight = 2 * height / 3;
            this.panelInstru.setDim(instruWidth, instruHeight);
            this.mainPanel.setBounds(0, 0, width, height);
            this.setBounds(x, y, width, height);
//        System.out.println("SetDim done");
        }
    }

    // pour que chaque composant ait le même instrument sélectionné, utiliser cette méthode
    // --- A REMPLIR POUR LES AUTRES PANELS ---
    private void setInstruSelec(int instrument){
        this.instruSelec = instrument;
        this.panelInstru.setInstruSelec(instrument);
        this.repaint();
    }
}
