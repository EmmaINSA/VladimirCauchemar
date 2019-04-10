public class Run{
	public static void main(String[] args) {
		String instrument = "hautbois";
		double frequence = 520.0;
		Synthesis s = new Synthesis (instrument, frequence);
		Unique u = new Unique (instrument, frequence);
		Analysis a = new Analysis (instrument, frequence);
	}
}