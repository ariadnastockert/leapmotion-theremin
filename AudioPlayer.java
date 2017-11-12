import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.UGen;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 * Plays a tone, with variable frequency, waveform, and volume
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
	
	/**
	 * Converts the frequency float to something useful
	 * @param frequency The float 0.0 - 1.0
	 * @return The frequency in Hz
	 */
	public static float convert(float frequency) {
		//Convert Frequency
		double lowerRange = 1.7;
		double upperRange = 4;
		double midRange = upperRange - lowerRange;
		double base = 10;
		frequency = (int) Math.pow(base, frequency * midRange + lowerRange);
		
		return frequency;
	}
	
	/**
	 * Converts the float wave to a waveform
	 * @param wave The float from 0.0 - 1.0
	 * @return The waveform
	 */
	public static Buffer convertWave(float wave) {
		//Convert Wave to Index
		if(wave < 0.25) {
			return Buffer.SAW;
		}else if(wave < 0.5) {
			return Buffer.SINE;
		}else if(wave < 0.75) {
			return Buffer.SQUARE;
		}else if(wave < 1) {
			return Buffer.TRIANGLE;
		}else {
			return null;
		}
	}
	
	/**
	 * Starts Playing a sound
	 * @param frequency The float 0.0 - 1.0 to represent the Frequency
	 * @param volume The float 0.0 - 1.0 to represent the Volume
	 */
	public void startSound(float frequency, float volume, float wave) {
		frequency = (int) convert(frequency);
		System.out.println(frequency);
		System.out.println(volume);
		
		ac = new AudioContext();
		freqEnv = new Envelope(ac, frequency);
		volEnv = new Envelope(ac, volume);
		wp = new WavePlayer(ac, freqEnv, convertWave(wave));

		g = new Gain(ac, 1, volEnv);

		g.addInput(wp);
		ac.reset();
		ac.out.addInput(g);
		ac.start();
	}
	
	/**
	 * Changes the current sound
	 * @param frequency The float 0.0 - 1.0 to represent the frequency
	 * @param volume The float 0.0 - 1.0 to represent the volume
	 */
	public static void changeSound(float frequency, float volume, float wave) {
		frequency = (int) convert(frequency);
		
		freqEnv.addSegment(frequency, 10);
		volEnv.addSegment(volume, 10);
		System.out.println(wp.getBuffer());
		wp.setBuffer(convertWave(wave));
		
		ac.reset();
		ac.out.addInput(g);
		ac.start();
	}
	
	/**
	 * Stops the current sound
	 */
	public void stopSound() {
		ac = null;
	}
	
	/**
	 * The function which plays the tones (Deprecated)
	 * @param y The frequency of the tone, on a scale of -200 - 200
	 * @param duration The duration of the tone in ms
	 * @param x The volume of the tone, on a scale of -200 - 200
	 * @throws LineUnavailableException 
	 */
	public void playSound(int y, int duration, int x) throws LineUnavailableException{
		//Convert Frequency from Scale to Hz
		double lowerRange = 1.7;
		double upperRange = 4;
		double midRange = upperRange - lowerRange;
		double base = 10;
		int freq = (int) Math.pow(base, ((((y + 200)/400.0) * midRange) + lowerRange));
		//The above math is magic granted to me by Tristan, hallowed by her name
		
		//Convert Volume to Scale of 0-100
		int volume = (x + 200)/4;
		
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
}