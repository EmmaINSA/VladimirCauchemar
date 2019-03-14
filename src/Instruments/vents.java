package Instruments;

import Instruments.instrument;

public class vents extends instrument {
	double longueur;
	String materiau;
	String[] notes;


	public vents(double longueur, String materiau, String[] notes){
		this.longueur=longueur;
		this.materiau=materiau;
		this.notes=notes;
		}
}
