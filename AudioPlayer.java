import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Plays a tone, with variable frequency, duration, and volume
 * @author Ivy O'Neal-Odom
 *
 */
public class AudioPlayer {	
	
	/**
	 * The Constructor for the player (currently contains test tones)
	 * @throws LineUnavailableException
	 */
	public AudioPlayer() throws LineUnavailableException{
		playSound(441, 200, 50);
		playSound(741, 300, 50);
		playSound(341, 400, 50);
	}
	
	/**
	 * The function which plays the tones
	 * @param freq The frequency of the tone
	 * @param duration The 
	 * @param volume
	 * @throws LineUnavailableException
	 */
	public void playSound(int freq, int duration, int volume) throws LineUnavailableException{
		float frequency = 44100;
		byte[] buf = new byte[1];
		AudioFormat af = new AudioFormat(frequency,8,1,true,false);	//AudioFormat(Number of Samples per Second, Number of Bits in each Sample, Number of Channels (1 for mono, 2, for stereo, etc.), idk, idk)
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		
		for(int i = 0; i < duration*frequency/1000; i++){
			double angle = i/(frequency/freq)*2.0*Math.PI;
			buf[0] = (byte)(Math.sin(angle)*volume);
			sdl.write(buf, 0, 1);
		}
		
		sdl.drain();
		sdl.stop();
		sdl.close();
	}
	
	/**
	 * 
	 * @param args
	 * @throws LineUnavailableException
	 */
	public static void main(String[] args) throws LineUnavailableException {
		AudioPlayer am = new AudioPlayer();
	}
}