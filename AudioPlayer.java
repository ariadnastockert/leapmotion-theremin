import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 * Plays a tone, with variable frequency, duration, and volume
 * @author Ivy O'Neal-Odom
 *
 */
public class AudioPlayer {	
//	int MIN_RANGE = 50;
	
	static AudioContext ac;
	static Envelope freqEnv;
	static Envelope volEnv;
	static WavePlayer wp;
	static Gain g;
//	static Glide gl;
	/**
	 * The Constructor for the player (currently contains commented-out test tones)
	 */
	public AudioPlayer(){
	}
	
	public static float convert(int frequency) {
		//Convert Frequency
		double lowerRange = 1.7;
		double upperRange = 4;
		double midRange = upperRange - lowerRange;
		double base = 10;
		frequency = (int) Math.pow(base, ((((frequency + 200)/400.0) * midRange) + lowerRange));
		
		//Convert Volume
//		float vol = (float) ((volume + 200)/400.0);
		
		return frequency;
	}
	
	public void startSound(int frequency, float volume) {
		frequency = (int) convert(frequency);
		System.out.println(frequency);
		System.out.println(volume);
		
		ac = new AudioContext();
		freqEnv = new Envelope(ac, frequency);
		volEnv = new Envelope(ac, volume);
		wp = new WavePlayer(ac, freqEnv, Buffer.SINE);

//		gl = new Glide(ac, volume, 1000);
		g = new Gain(ac, 1, volEnv);
		
//		g.removeAllConnections(wp);
//		ac.out.removeAllConnections(g);
		g.addInput(wp);
		ac.reset();
		ac.out.addInput(g);
		ac.start();
	}
	
	public static void changeSound(int frequency, float volume) {
		frequency = (int) convert(frequency);
		
		freqEnv.addSegment(frequency, 10);
		
//		freqEnv = new Envelope(ac, frequency);
		volEnv.addSegment(volume, 10aaac);
//		
//		g.removeAllConnections(wp);
//		ac.out.removeAllConnections(g);
//		g.addInput(wp);
		ac.reset();
		ac.out.addInput(g);
		ac.start();
	}
	
//	/**
//	 * The function which plays the tones
//	 * @param y The frequency of the tone, on a scale of -200 - 200
//	 * @param duration The duration of the tone in ms
//	 * @param x The volume of the tone, on a scale of -200 - 200
//	 */
//	public void playSound(int y, int duration, int x){
//		//Convert Frequency from Scale to Hz
//		double lowerRange = 1.7;
//		double upperRange = 4;
//		double midRange = upperRange - lowerRange;
//		double base = 10;
//		int freq = (int) Math.pow(10, ((((y + 200)/400.0) * midRange) + lowerRange));
//		//The above math is magic granted to me by Tristan, hallowed by her name
//		
//		//Convert Volume to Scale of 0-100
//		int volume = (x + 200)/4;
//		
////		float frequency = 44100;
////		byte[] buf = new byte[1];
////		AudioFormat af = new AudioFormat(frequency,8,1,true,false);	//AudioFormat(Number of Samples per Second, Number of Bits in each Sample, Number of Channels (1 for mono, 2, for stereo, etc.), idk, idk)
////		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
////		sdl.open(af);
////		sdl.start();
////		
////		for(int i = 0; i < duration*frequency/1000; i++){
////			double angle = i/(frequency/freq)*2.0*Math.PI;
////			buf[0] = (byte)(Math.sin(angle)*volume);
////			sdl.write(buf, 0, 1);
////		}
////		
////		sdl.drain();
////		sdl.stop();
////		sdl.close();
//	}
	
	/**
	 * Runs automatically, generates the player and runs the code in the constructor
	 * @param args Arguments passed from cmd
	 */
	public static void main(String[] args){
//		AudioPlayer am = new AudioPlayer();
	}
}