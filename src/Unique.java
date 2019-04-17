//===========================================================================================================================
//
// Objet extension de JFrame qui programme un des 3 graphiques : les signaux decomposes
//
//===========================================================================================================================



import javax.swing.JFrame;
import java.util.LinkedList;

public class Unique extends JFrame{

	MainPanel2 mp;

		public Unique(){

//===========================================================================================================================
// Variables utiles
//===========================================================================================================================

			int width = 400;
			int height = 400;
			String title = "Analyse";

//===========================================================================================================================
// Parametrage
//===========================================================================================================================

			setSize(width,height);
			setTitle(title);
			setLocation(1130,500);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

//===========================================================================================================================
// Methode pour rafraichir le Panel a chaque action de l'utilisateur
//===========================================================================================================================

		public void refresh(String instrument, double frequence, LinkedList<Integer> harmoniques){
			mp = new MainPanel2(instrument, frequence, harmoniques);
			add(mp);
		}
}
