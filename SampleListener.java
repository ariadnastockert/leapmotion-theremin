import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.Vector;

class SampleListener extends Listener {

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }
    
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        HandList hands = frame.hands();
        Hand firstHand = hands.get(0);
        Finger frontFinger = frame.fingers().frontmost();
       
        
        getCoordnates(frontFinger);
        //getExtendedFingers();
 
    }

	private double[] getCoordnates(Finger finger) {
		Vector stablePosition = finger.stabilizedTipPosition();
		double [] posArray = new double[3];
		
		posArray[0]= stablePosition.getX();
		posArray[1]= stablePosition.getY();
		posArray[2]= stablePosition.getZ();
		
		
		System.out.println( stablePosition.toString());
		
		return posArray;
		
		
	}
}
