import javax.sound.sampled.*;
public class Sound
{
    public static float SAMPLE_RATE = 8000f;
    
    public static void tone(int hz, int msecs) 
    throws LineUnavailableException 
    {
        tone(hz, msecs, 1.0);
    }

    public static void tone(int hz, int msecs, double vol)
    throws LineUnavailableException 
    {
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(SAMPLE_RATE,8,1,true,false);     
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        for (int i=0; i < msecs*8; i++) {
              double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
              buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
              sdl.write(buf,0,1);
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }

    //partie pour choisir la frequence(1er variable) et la duree(2eme variable)
    
    public static void main(String[] args) throws Exception {
        double f=1.122462;		//2^(1/6)
    	Sound.tone(440,100,12); 
    	Sound.tone((int)(440*f),100,1); 
        Sound.tone((int)(440*Math.pow(f,2)),100,1); 
        Sound.tone((int)(440*Math.pow(f,3)),100,1); 
        Sound.tone((int)(440*Math.pow(f,4)),100,1); 
        Sound.tone((int)(440*Math.pow(f,5)),100,1); 
        Sound.tone((int)(440*Math.pow(f,6)),100,1); 
        Sound.tone((int)(440*Math.pow(f,7)),100,1); 
       
    }
}