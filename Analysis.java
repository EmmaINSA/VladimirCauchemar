//===========================================================================================================================
//
// Objet extension de JFrame qui programme un des 3 graphiques : l'analyse de Fourier
//
//===========================================================================================================================


import javax.swing.JFrame;

public class Analysis extends JFrame{

	MainFourier mp;

		public Analysis(){

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
			setLocation(610,500);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
        
//===========================================================================================================================
// Methode pour rafraichir le Panel a chaque action de l'utilisateur
//===========================================================================================================================

		public void refresh(String instrument, double frequence){
 			mp = new MainFourier(instrument, frequence);
			add(mp);
		}
}
