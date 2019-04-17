//===========================================================================================================================
//
// Comme son nom l'indique, c'est la fenetre principale, qui traite toute l'IHM (listeners en entres, affichage graphique en sortie...)
//
//===========================================================================================================================


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import org.jfugue.player.Player;


public class FenetrePrinc extends JFrame implements ActionListener, ChangeListener, KeyListener{

	Instrument i=new Instrument("Flute");
    protected int instruSelec=0, x=200, y=50, width = 960, height = 720, instruWidth, instruHeight;    // modifiables via options
    protected int dureeMin = 1, dureeMax = 10, dureeAct= 2;
    protected int octaveMin=2, octaveMax=7;
    protected String octaveAct="5";
    protected JMenuBar menuBar;
    protected JCheckBox[] GroupeHarmonique; 
    protected JMenu menuInstruments, menuOptions, submenuResolution, menuAbout, menuHarmoniques;
    protected JMenuItem itemFluteDePan, itemFluteABec, itemClarinette, itemHautbois, itemOrgue,
            reso1280_960, reso960_720, reso640_480,  itemInspi;
    protected PanelInstrument panelInstru;
    protected JPanel mainPanel, panelOptions;
    protected JSlider sliderDuree, sliderOctave;
    protected JLabel labelDuree, labelOctave;
    private String duree=String.valueOf(dureeAct);
    protected JCheckBox afficherGraphe;
    protected LinkedList<Integer> harmoniquesChoisies;    
    protected Synthesis s;
    protected Analysis a;
    protected Unique u;
    Player player = new Player();
    String instrument = Constants.STRINGS[instruSelec];
    double frequence=0;

    public FenetrePrinc() {
        super("Simulateur d'instruments a vent");
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

        // panel affichage Instrument
        this.panelInstru = new PanelInstrument();
        panelInstru.setDim(instruWidth, instruHeight);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);
        this.mainPanel.setBounds(0,0,width,height);
        this.mainPanel.add(this.panelInstru);
        this.setContentPane(this.mainPanel);

        // panel options
        this.panelOptions = new JPanel();
        panelOptions.setLayout(null);
        panelOptions.setBounds(instruWidth,0,width-instruWidth, instruHeight);
        panelOptions.setBackground(Color.CYAN);
        this.mainPanel.add(panelOptions);

        // widgets
        labelDuree = new JLabel("Duree d'une note (a def)");
        labelDuree.setBounds((width-instruWidth)/2- 60, 10, width-instruWidth,50);
        panelOptions.add(labelDuree);

        sliderDuree = new JSlider(dureeMin,dureeMax,dureeAct);
        sliderDuree.setMajorTickSpacing(1);
        sliderDuree.setMinorTickSpacing(1);
        sliderDuree.setPaintTicks(true);
        sliderDuree.setPaintLabels(true);
        sliderDuree.setValueIsAdjusting(true);
        sliderDuree.addChangeListener(this);
        sliderDuree.setBounds(30,60,width-instruWidth-60, height/10);
        this.panelOptions.add(sliderDuree);

        labelOctave = new JLabel("N° de l'octave jouee");
        labelOctave.setBounds((width-instruWidth)/2 - 60, 60 + height/10 + 20, width-instruWidth, 50);
        panelOptions.add(labelOctave);

        sliderOctave = new JSlider(octaveMin, octaveMax, Integer.valueOf(octaveAct));
        sliderOctave.setMajorTickSpacing(1);
        sliderOctave.setMinorTickSpacing(1);
        sliderOctave.setPaintLabels(true);
        sliderOctave.setPaintTicks(true);
        sliderOctave.setValueIsAdjusting(true);
        sliderOctave.addChangeListener(this);
        sliderOctave.setBounds(30,60 + height/10 + 20*2 + 50,width-instruWidth-60, height/10);
        panelOptions.add(sliderOctave);

        this.setInstruSelec(0);

        // barre de menu
        this.menuBar();

        this.setVisible(true);
        addKeyListener(this);

        //graphes
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
        itemFluteDePan = new JMenuItem("Flute de pan");
        menuInstruments.add(itemFluteDePan);
        itemFluteDePan.addActionListener(this);

        // flute a bec
        itemFluteABec = new JMenuItem("Flute a bec");
        menuInstruments.add(itemFluteABec);
        itemFluteABec.addActionListener(this);

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

        afficherGraphe = new JCheckBox("Afficher les Graphes");
        afficherGraphe.setSelected(true);
        menuOptions.add(afficherGraphe);
        afficherGraphe.addActionListener(this);

        // resolution
        submenuResolution = new JMenu("Resolution");
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
        menuAbout = new JMenu("A propos");
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
            this.setInstruSelec(Constants.FLUTEABEC);
        }

        else if (source == this.itemFluteDePan){
            this.setInstruSelec(Constants.FLUTEDEPAN);
        }

        else if (source == this.itemClarinette){
            this.setInstruSelec(Constants.CLARINETTE);
        }

        else if (source == this.itemHautbois){
            this.setInstruSelec(Constants.HAUTBOIS);
        }

        else if (source == this.itemOrgue){
            this.setInstruSelec(Constants.ORGUE);
        }
        
        else if (source == this.afficherGraphe){
            System.out.println("Affichage graphique active");
        }

        else if ((source == this.GroupeHarmonique[0])||(source == this.GroupeHarmonique[1])||(source == this.GroupeHarmonique[2])||
                (source == this.GroupeHarmonique[3])||(source == this.GroupeHarmonique[4])||(source == this.GroupeHarmonique[5])||
                (source == this.GroupeHarmonique[6])||(source == this.GroupeHarmonique[7])||(source == this.GroupeHarmonique[8])||
                (source == this.GroupeHarmonique[9])||(source == this.GroupeHarmonique[10])||(source == this.GroupeHarmonique[11])||
                (source == this.GroupeHarmonique[12])||(source == this.GroupeHarmonique[13])||(source == this.GroupeHarmonique[14])){
            harmoniquesChoisies = getSelectedNames(GroupeHarmonique);
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
                Desktop.getDesktop().browse(new URI("https://youtu.be/hpjV962DLWs"));
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


    public void keyTyped(KeyEvent e) {}


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_S) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] C"+octaveAct+duree);
                frequence =i.f[0+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_D) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] D"+octaveAct+duree);
                frequence =i.f[2+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_F) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] E"+octaveAct+duree);
                frequence =i.f[4+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_J) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] F"+octaveAct+duree);
                frequence =i.f[5+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_K) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] G"+octaveAct+duree);
                frequence =i.f[7+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_L) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] A"+octaveAct+duree);
                frequence =i.f[9+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_M) {     // VK_SEMICOLON pour qwerty

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] B"+octaveAct+duree);
                frequence =i.f[11+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_E) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] C#"+octaveAct+duree);
                frequence =i.f[1+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_R) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] D#"+octaveAct+duree);
                frequence =i.f[3+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();

            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_I) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] F#"+octaveAct+duree);
                frequence =i.f[6+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_O) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] G#"+octaveAct+duree);
                frequence =i.f[8+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (keyCode == KeyEvent.VK_P) {

            try {
                player.play("I["+Constants.STRINGS[instruSelec]+"] A#"+octaveAct+duree);
                frequence =i.f[10+(Integer.parseInt(octaveAct)-2)*12];
                if(afficherGraphe.isSelected()){
                    rafraichir(instrument, frequence, harmoniquesChoisies);
                    rendreVisible();
                }else rendreInvisible();
            } catch (Exception ex) {
                Logger.getLogger(FenetrePrinc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public void keyReleased(KeyEvent e) {}

    private void setDim(int width, int height){
        if (this.width != width && this.height != height) {
            // fenetre et panels
            this.width = width;
            this.height = height;
            this.instruWidth = 2 * width / 3;
            this.instruHeight = 2 * height / 3;
            this.panelInstru.setDim(instruWidth, instruHeight);
            this.panelOptions.setBounds(instruWidth, 0, width-instruWidth,instruHeight);
            this.mainPanel.setBounds(0, 0, width, height);

            // widgets
            labelDuree.setBounds((width-instruWidth)/2- 60, 10, width-instruWidth,50);
            sliderDuree.setBounds(30,60,width-instruWidth-60, height/10);
            labelOctave.setBounds((width-instruWidth)/2 - 60, 60 + height/10 + 20, width-instruWidth, 50);
            sliderOctave.setBounds(30,60 + height/10 + 20*2 + 50,width-instruWidth-60, height/10);
            this.setBounds(x, y, width, height);
        }
    }

    private void setOctave(int numOctave){
        octaveAct = String.valueOf(numOctave);
    }

    private void setDuree(int dureeint){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< dureeint; i++){
            sb.append("i");     // i = 1/8s
        }
        duree = sb.toString();
    }

    private void setInstruSelec(int instrument){
        this.instruSelec = instrument;
        this.panelInstru.setInstruSelec(instrument);
        this.repaint();
    }

    private LinkedList<Integer> getSelectedNames(JCheckBox[] boxes) {

        LinkedList<Integer> SelectedElements = new LinkedList<Integer>();

        for (int i = 0; i < boxes.length; i++)
            if (boxes[i].isSelected())
                SelectedElements.add(i);

        return SelectedElements;
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

    private void rendreVisible(){
        s.setVisible(true);
        a.setVisible(true);
        u.setVisible(true);
        this.setVisible(true);
    }

    private void rendreInvisible(){
        s.setVisible(false);
        a.setVisible(false);
        u.setVisible(false);
        this.setVisible(true);
    }

    private void rafraichir(String instrument, double frequence, LinkedList<Integer> harmoniques){
        a.refresh(instrument, frequence);
        s.refresh(instrument, frequence);
            if(harmoniques == null){
                LinkedList<Integer> harmoniquesParDefaut = new LinkedList<Integer>();
                harmoniquesParDefaut.add(0);
                u.refresh(instrument, frequence, harmoniquesParDefaut);
                System.out.println("Vous pouvez choisir les harmoniques que vous souhaitez, la fondamentale etant affichee par defaut");
            }
            else 
                u.refresh(instrument, frequence, harmoniques);
    }

}
