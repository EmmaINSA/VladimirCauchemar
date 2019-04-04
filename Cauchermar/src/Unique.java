import javax.swing.*;
import java.util.LinkedList;

public class Unique extends JFrame{
		public Unique(String instrument, double frequence, LinkedList<Integer> harmoniques){

//===========================================================================================================================
// Just some variables
//===========================================================================================================================

			int width = 400;
			int height = 400;
			String title = "Analyse";

//===========================================================================================================================
// Bounds and name
//===========================================================================================================================

			setSize(width,height);
			setTitle(title);
			setLocation(1130,500);



			MainPanel2 mp = new MainPanel2(instrument, frequence, harmoniques);
			add(mp);

//===========================================================================================================================
//	Set Visible
//===========================================================================================================================

			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
}