import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfugue.player.Player;


/* A faire :
* - les panels principaux avec tout le reste
*
* La morale de cette histoire :
* "Quand tu as un probème que tu ne sais pas résoudre, contourne-le !"
* Grand maître anonyme 2019
* */

public class FenetrePrinc extends JFrame implements ActionListener, ChangeListener, KeyListener{

	instrument i=new instrument("Flute");
	double fi=0;
    protected int instruSelec=0, x=200, y=50, width = 960, height = 720, instruWidth, instruHeight;    // modifiables via options
    protected int dureeMin = 1, dureeMax = 10, dureeAct= 5;
    protected int octaveMin=4, octaveMax=7;
    protected String octaveAct="5";
    protected JMenuBar menuBar;
    protected JMenu menuInstruments, menuOptions, submenuResolution, menuAbout;
    protected JMenuItem itemFluteDePan, itemFluteABec, itemClarinette, itemHautbois, itemOrgue,
            reso1280_960, reso960_720, reso640_480,  itemInspi;
    protected PanelInstrument panelInstru;
    protected JPanel mainPanel, panelOptions;
    protected JCheckBox AfficherGraphe;
    protected JSlider sliderDuree, sliderOctave;
    protected JLabel labelDuree, labelOctave;
    private String duree=String.valueOf(dureeAct);

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
        this.setFocusable(true);

        // panel affichage instrument
        this.panelInstru = new PanelInstrument();
        panelInstru.setDim(instruWidth, instruHeight);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);
        this.mainPanel.setBounds(0,0,width,height);
        this.mainPanel.add(this.panelInstru);
        this.setContentPane(this.mainPanel);

        // panel options
        this.panelOptions = new JPanel();
//        panelOptions.setLayout(null);
        panelOptions.setBounds(instruWidth,0,width-instruWidth, instruHeight);
        panelOptions.setBackground(Color.CYAN);
        this.mainPanel.add(panelOptions);

        // widgets
        labelDuree = new JLabel("Durée ");
//        labelDuree.setBounds((width-instruWidth)/2, 30, 100,50);
        panelOptions.add(labelDuree);

        sliderDuree = new JSlider(dureeMin,dureeMax,dureeAct);
        sliderDuree.setMajorTickSpacing(1);
        sliderDuree.setMinorTickSpacing(1);
        sliderDuree.setPaintTicks(true);
        sliderDuree.setPaintLabels(true);
        sliderDuree.setValueIsAdjusting(true);
        sliderDuree.addChangeListener(this);
        this.panelOptions.add(sliderDuree);

        labelOctave = new JLabel("Octave ");
        panelOptions.add(labelOctave);

        sliderOctave = new JSlider(octaveMin, octaveMax, Integer.valueOf(octaveAct));
        sliderOctave.setMajorTickSpacing(1);
        sliderOctave.setMinorTickSpacing(1);
        sliderOctave.setPaintLabels(true);
        sliderOctave.setPaintTicks(true);
        sliderOctave.setValueIsAdjusting(true);
        sliderOctave.addChangeListener(this);
        panelOptions.add(sliderOctave);

        this.setInstruSelec(0);

        // barre de menu
        this.menuBar();

        this.setVisible(true);
        addKeyListener(this);
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

        AfficherGraphe = new JCheckBox("Afficher les Graphes");
        menuOptions.add(AfficherGraphe);
        AfficherGraphe.addActionListener(this);
        
        // résolution
        submenuResolution = new JMenu("Résolution");
        reso640_480 = new JMenuItem("640x480");
        submenuResolution.add(reso640_480);
        reso960_720 = new JMenuItem("960x720");
        submenuResolution.add(reso960_720);
        reso1280_960 = new JMenuItem("1280x960");
        submenuResolution.add(reso1280_960);
        reso1280_960.addActionListener(this);
        reso960_720.addActionListener(this);
        reso640_480.addActionListener(this);
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

        // Resolutions : 1280*960 / 640*480 / 960*720
        else if (source ==this.reso960_720){
            this.setDim(960,720);
        }

        else if (source == this.reso1280_960){
            this.setDim(1280,960);
        }

        else if (source == this.reso640_480){
            this.setDim(640,480);
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
    
    public void keyTyped(KeyEvent e) {
        System.out.println("Key typed");
    }

   
    public void keyPressed(KeyEvent e) {

        System.out.println("Keypressed");
        int keyCode = e.getKeyCode();
		Player player = new Player();

        if (keyCode == KeyEvent.VK_Q) {
               
                try {
                	   player.play("I["+Constants.STRINGS[instruSelec]+"] C"+octaveAct+duree);//do5
                       String instrument = Constants.STRINGS[instruSelec];
               		   double frequence =i.f[0];
               		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_W) {
                
                try {
                	 player.play("I["+Constants.STRINGS[instruSelec]+"] D"+octaveAct+duree);//ri5
                     String instrument = Constants.STRINGS[instruSelec];
             		 double frequence =i.f[1];
             		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_E) {
                
                try {
                    System.out.println("Playing");
                    player.play("I["+Constants.STRINGS[instruSelec]+"] E"+octaveAct+duree);//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[2];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_R) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] F"+octaveAct+duree);//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[3];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_T) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] G"+octaveAct+duree);//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[4];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_Y) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] A"+octaveAct+duree);//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[5];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_U) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] B"+octaveAct+duree);//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[6];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_A) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] C6h");//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[7];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_S) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] D6h");//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[8];
            		if(AfficherGraphe.isSelected()){
            		Synthesis s = new Synthesis (instrument, frequence);
            		Unique u = new Unique (instrument, frequence);
            		Analysis a = new Analysis (instrument, frequence);
            		}
                    
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_D) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] E6h");//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[9];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_F) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] F6h");//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[10];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_G) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] G6h");//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[11];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_H) {
                
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] A6h");//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[12];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
                		}
                } catch (Exception ex) {
                        Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (keyCode == KeyEvent.VK_J) {
               
                try {
                	player.play("I["+Constants.STRINGS[instruSelec]+"] B6h");//ri5
                    String instrument = Constants.STRINGS[instruSelec];
            		double frequence =i.f[13];
            		if(AfficherGraphe.isSelected()){
                		Synthesis s = new Synthesis (instrument, frequence);
                		Unique u = new Unique (instrument, frequence);
                		Analysis a = new Analysis (instrument, frequence);
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
            this.panelOptions.setBounds(instruWidth, 0, width-instruWidth,instruHeight);
            this.mainPanel.setBounds(0, 0, width, height);
            this.setBounds(x, y, width, height);
//        System.out.println("SetDim done");
        }
    }

    private void setOctave(int numOctave){
        octaveAct = String.valueOf(numOctave);
    }

    private void setDuree(int dureeint){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< dureeint; i++){
            sb.append("q");
        }
        duree = sb.toString();
    }

    // pour que chaque composant ait le même instrument sélectionné, utiliser cette méthode
    // --- A REMPLIR POUR LES AUTRES PANELS ---
    private void setInstruSelec(int instrument){
        this.instruSelec = instrument;
        this.panelInstru.setInstruSelec(instrument);
        this.repaint();
    }


    @Override
    public void stateChanged(ChangeEvent e) {       // appelee quand on modif la valeur d'un slider a la souris
        if (e.getSource() == sliderOctave){
            setOctave(sliderOctave.getValue());

        }else if (e.getSource() == sliderDuree){
            setDuree(sliderDuree.getValue());
        }
        this.requestFocus();        // on perd le focus pour la fenetre après stateChanged -> le remet
    }


}
