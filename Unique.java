import javax.swing.*;
import java.util.LinkedList;

public class Unique extends JFrame{

	MainPanel2 mp;

		public Unique(){

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

//===========================================================================================================================
//	Set Visible
//===========================================================================================================================

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		public void refresh(String instrument, double frequence, LinkedList<Integer> harmoniques){
			mp = new MainPanel2(instrument, frequence, harmoniques);
			add(mp);
		}
}