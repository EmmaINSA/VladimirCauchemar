import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import org.jfugue.player.Player;

/* A faire :
* - les panels principaux avec tout le reste
*
* La morale de cette histoire :
* "Quand tu as un probleme que tu ne sais pas resoudre, contourne-le !"
* Grand maitre anonyme 2019
* */

public class FenetrePrinc extends JFrame implements ActionListener,KeyListener{

	instrument i=new instrument("Flute");
	double fi=0;
    protected int instruSelec=0, x=200, y=50, width = 800, height = 600, instruWidth, instruHeight;    // modifiables via options
    protected JMenuBar menuBar;
    protected JCheckBox[] GroupeHarmonique; 
    protected JMenu menuInstruments, menuOptions, submenuResolution, menuAbout, menuHarmoniques;
    protected JMenuItem itemFluteDePan, itemFluteABec, itemClarinette, itemHautbois, itemOrgue,
            reso1000_600, reso600_400, itemInspi;
    protected PanelInstrument panelInstru;
    protected JPanel mainPanel;
    protected JCheckBox AfficherGraphe;
    LinkedList<Integer> harmoniquesChoisies;
    protected Synthesis s;
    protected Analysis a;
    protected Unique u;
    
    public FenetrePrinc() {
        super("Simulateur d'instruments ¨a vent");
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
        addKeyListener(this);

        //graph
        s = new Synthesis();
        a = new Analysis();
        u = new Unique();
    }

    private void menuBar(){

        menuBar = new JMenuBar();


        //menuInstruments : comme "Fichier", avec des sous-composants
        menuInstruments = new JMenu("Instruments");
        // menuInstruments.setMnemonic(KeyEvent.VK_I);        // pour la recherche dans la barre de menu
        menuInstruments.getAccessibleContext().setAccessibleDescription("Choix des instruments");
        menuBar.add(menuInstruments);      // pas oublier

        // flute de pan
        itemFluteDePan = new JMenuItem("Flute de pan"); // autre argument : KeyEvent.VK_P par ex
        //itemFluteDePan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));      // raccourci clavier ?
//        itemFluteDePan.getAccessibleContext().setAccessibleDescription("Flute de pan pas encore dispo");
        menuInstruments.add(itemFluteDePan);
        itemFluteDePan.addActionListener(this);

        // flute a bec
        itemFluteABec = new JMenuItem("Flute ¨a bec");
//        itemFluteABec.getAccessibleContext().setAccessibleDescription("Flute ¨a bec pas encore dispo");
        menuInstruments.add(itemFluteABec);
        itemFluteABec.addActionListener(this);

        menuInstruments.addSeparator();      // pour separer vents & cordes ?

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

        //menuHarmoniques : selection d'harmoniques par checkbox
        menuHarmoniques = new JMenu("Harmoniques");

        GroupeHarmonique = new JCheckBox[15];

        for(int i = 0; i<GroupeHarmonique.length; i++){
            GroupeHarmonique[i] = new JCheckBox(""+i);
            menuHarmoniques.add(GroupeHarmonique[i]);
            GroupeHarmonique[i].addActionListener(this);
        }

        menuBar.add(menuHarmoniques);

        // options
        menuOptions = new JMenu("Options");
        menuBar.add(menuOptions);

        AfficherGraphe = new JCheckBox("Afficher les Graphes");
        menuOptions.add(AfficherGraphe);
        AfficherGraphe.addActionListener(this);
        
        // resolution
        submenuResolution = new JMenu("Resolution");
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


    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == this.itemFluteABec){
            System.out.println("Flute a bec");
            this.setInstruSelec(Constants.FLUTEABEC);

        }

        else if (source == this.itemFluteDePan){
            System.out.println("Flute de pan");
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

        else if ((source == this.GroupeHarmonique[0])||(source == this.GroupeHarmonique[1])||(source == this.GroupeHarmonique[2])||
                (source == this.GroupeHarmonique[3])||(source == this.GroupeHarmonique[4])||(source == this.GroupeHarmonique[5])||
                (source == this.GroupeHarmonique[6])||(source == this.GroupeHarmonique[7])||(source == this.GroupeHarmonique[8])||
                (source == this.GroupeHarmonique[9])||(source == this.GroupeHarmonique[10])||(source == this.GroupeHarmonique[11])||
                (source == this.GroupeHarmonique[12])||(source == this.GroupeHarmonique[13])||(source == this.GroupeHarmonique[14])){
            harmoniquesChoisies = getSelectedNames(GroupeHarmonique);
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
            System.out.println("Pas d'action specifiee");
        }
    }
    
    public void keyTyped(KeyEvent e) {
            
    }

    

public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
		Player player = new Player();

        if (keyCode == KeyEvent.VK_Q) {
               
                try {
                	   player.play("I["+Constants.STRINGS[instruSelec]+"] C5q");
                       String instrument = Constants.STRINGS[instruSelec];
               		   double frequence =i.f[0];
               		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_W) {
                
                try {
                	 player.play("I["+Constants.STRINGS[instruSelec]+"] D5q");
                     String instrument = Constants.STRINGS[instruSelec];
             		 double frequence =i.f[1];
             		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_E) {
                
                try {
                	// player.play("I["+Constants.STRINGS[instruSelec]+"] E5q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[2];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_R) {
               
                try {
                	// player.play("I["+Constants.STRINGS[instruSelec]+"] F5q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[3];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_T) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] G5q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[4];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_Y) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] A5q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[5];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_U) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] B5q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[6];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_A) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] C6q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[7];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_S) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] D6q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[8];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
            		}
                    
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_D) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] E6q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[9];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_F) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] F6q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[10];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_G) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] G6q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[11];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_H) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] A6q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[12];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_J) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] B6q");
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[13];
            		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
       
        if (keyCode == KeyEvent.VK_K) {
            
            try {
            	   player.play("I["+Constants.STRINGS[instruSelec]+"] C7q");
                   String instrument = Constants.STRINGS[instruSelec];
           		   double frequence =i.f[14];
           		if(AfficherGraphe.isSelected()){
                       rafraichir(instrument, frequence, harmoniquesChoisies);
                       rendreVisible();
            		}
            } catch (Exception ex) {
                    Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }


    public void keyReleased(KeyEvent e) {
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

    // pour que chaque composant ait le m¨ºme instrument s¨¦lectionn¨¦, utiliser cette m¨¦thode
    // --- A REMPLIR POUR LES AUTRES PANELS ---
    private void setInstruSelec(int instrument){
        this.instruSelec = instrument;
        this.panelInstru.setInstruSelec(instrument);
        this.repaint();
    }

    private LinkedList<Integer> getSelectedNames(JCheckBox[] boxes) {

        LinkedList<Integer> SelectedElements =new LinkedList<Integer>();

        for(int i = 0; i< boxes.length; i++)
            if(boxes[i].isSelected())
                SelectedElements.add(i);

        return SelectedElements;
    }

        private void rendreVisible(){

        s.setVisible(true);
        a.setVisible(true);
        u.setVisible(true);

    }

    private void rafraichir(String instrument, double frequence, LinkedList<Integer> harmoniques){
        a.refresh(instrument, frequence);
        u.refresh(instrument, frequence, harmoniquesChoisies);
        s.refresh(instrument, frequence);
    }

}