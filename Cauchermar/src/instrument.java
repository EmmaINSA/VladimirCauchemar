import java.util.List;
import java.util.ArrayList;

public class instrument{
	String nom;
    double[] f=new double[15];
    double df=1.122462048;
    List<Double> amplitudeTable = new ArrayList<>();
    //ce tableau contiendra les harmoniques avec leurs amplitudes pond¡§r¡§s
    //les harmoniques sont obtenus par mutltiple de f fondamental
    double coeffAmplitude;
    //si besoin amplifier amplitudes selon instrument car tableau relatif a A0 = 1
	
	public instrument (String nom){
		this.nom=nom;
        if (nom == "Flute"){
            double[] tempTable = {1,9.1,3.7,1.75,0.25,0.1,0.01,0.05,0.01,0.02,0,0.01,0,0.01,0.01};
            coeffAmplitude = 1;
                for (int j = 0; j<tempTable.length ; j++) {
                amplitudeTable.add(tempTable[j]);
                }
            }
        else if (nom == "Oboe"){
            double[] tempTable = {1,0.97,2.11,0.19,0.21,0.23,0.54,0.3,0.2,0.03,0.04,0.05,0.02,0,0.01};
            coeffAmplitude = 1;
                for (int j = 0; j<tempTable.length ; j++) {
                amplitudeTable.add(tempTable[j]);
                }
            }
        else if (nom == "Clarinet"){
            double[] tempTable = {1,0.36,0.26,0.02,0.08,0.2,0.03,0.01,0.01,0,0,0,0,0.01,0};
            coeffAmplitude = 1;
                for (int j = 0; j<tempTable.length ; j++) {
                amplitudeTable.add(tempTable[j]);
                }
            }
        else if (nom == "Pan_Flute"){
            double[] tempTable = {1,0.65,0.61,0.15,0.09,0.02,0.02,0.01,0.01,0.01,0,0,0,0.01,0};
            coeffAmplitude = 1;
                for (int j = 0; j<tempTable.length ; j++) {
                amplitudeTable.add(tempTable[j]);
                }
            }
        else if (nom == "Church_Organ"){
            double[] tempTable = {1,0.65,0.49,0.51,0.5,0.5,0.38,0.1,0.01,0.08,0.06,0,0,0.01,0};
            coeffAmplitude = 1;
                for (int j = 0; j<tempTable.length ; j++) {
                amplitudeTable.add(tempTable[j]);
                }
            }
            
           
           f[0]=440;
           for(int i=1;i<15;i++){
			   f[i]=f[i-1]*df;
			  }
		}
    
	
	
        public double synthesisFunction (double frequence, double temps){
            
            double synthesisAmplitude = 0;
            
            for(int i = 0; i<15; i++){
                
                synthesisAmplitude += amplitudeTable.get(i)*Math.sin(2*Math.PI*frequence*0.000001*temps*(i+1));
                
                }
                
            synthesisAmplitude = synthesisAmplitude*coeffAmplitude;
                
            return synthesisAmplitude;
            
            }
            
        public double simpleFunction (double frequence, double temps, int n){  
            // n=0 est bien la fondamentale et non pas n=1!!!
            
            double synthesisAmplitude = amplitudeTable.get(n)*Math.sin(2*Math.PI*frequence*0.000001*temps*(n+1));
            
            synthesisAmplitude = synthesisAmplitude*coeffAmplitude;

            return synthesisAmplitude;
            
            } 
}