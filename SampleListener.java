import javax.sound.sampled.LineUnavailableException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.InteractionBox;

class SampleListener extends Listener {
	
	AudioPlayer player;
	HandManager manager

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        player = new AudioPlayer();
        manager = new HandManager(player);
        
    }
    
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        manager.check(frame);
        
        
    }
	
}
