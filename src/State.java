import java.util.Random;

public class State {
	
	// holds the state boolean as to whether an object is found
	private boolean findingObject=true;
	private boolean foundObject=false;
	private boolean calibrated = false;
	private boolean hasSeenQR = false;
	private float normalisedColourValue;
	// attributes for selecting random wander behaviour
	private Random rgen = new Random(); 
	private int currentBehaviourChoice = 0;
	public char[] dataRead = new char[3];
	
	State() { } 
  	
	// returns boolean on if robot is trying to find an object
	public boolean isFindingObject() {
		return findingObject;
	}
	
	// returns boolean on if robot has found an object
	public boolean hasFoundObject() {
		return foundObject;
	}
	
	// updates the state of the robot is finding an object
	public void setFindingObject(boolean bool) {
		 findingObject=bool;
	}
	
	// updates the state of the robot if an object is found
	public void setFoundObject(boolean bool) {
		 foundObject=bool;
	}

	public void updateCalibration(boolean cali, float normalised){
		setNormalisedValue(normalised);
		setCalibration(cali);
		setFindingObject(cali);
	}
	
	// updates the calibration state
	public void setCalibration(boolean cali){
		calibrated = cali;
		
	}

	//retrieves the calibration state 
	public boolean isCalibrated(){
		return calibrated;
	}

	public void setNormalisedValue(float normalised){
		normalisedColourValue = normalised;
	}

	public float getNormalisedColourValue(){
		return normalisedColourValue;
	}

	
	public void generateRadomBehaviour() {
		currentBehaviourChoice=rgen.nextInt(3);	
	}
	
// 	public void setDataRead(byte[] dataBuff) { //Bluetooth code that we did not use but attempted.

// 		for (int i = 0; i < dataRead.length; i++) { /Converts the bytes read to char values and sends them to an array 
// 			dataRead[i] = (char) dataBuff[i];   /that can be read by other classes
// 		}

// 		// for testing - prints chars to lcd
// 		// for (int i = 0; i < dataRead.length; i++) {
// 		//       LCD.drawChar(dataRead[i], i + 4, 3)
// 		//}
// 	}
	
	// returns the current behaviour choice
	public int getCurrentBehaviour() {
		return currentBehaviourChoice;
	}
	
	
}
