import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Rotate implements Behavior {
	
	final static double ROTATION_DEGREES = 360;
	
	final static double ROTATION_SPEED = 130;

	private MovePilot pilot; 

	private State sharedState ;
	
	
	 Rotate(State state,MovePilot robotPilot) {
		sharedState = state;
		pilot = robotPilot;
			
	}
	
	
	public void action() {
		LCD.clear();
		LCD.drawString("Rotation", 1, 1);
		//pilot.setAngularSpeed(ROTATION_SPEED);
		pilot.rotate(ROTATION_DEGREES,true);
	}
	
	
	public void suppress() {} 
	
	
	public boolean takeControl() { 
		// if the robot is not in finding object return false
		if (!sharedState.isFindingObject()) {
			return false;
		}
		
		// returns true if the shared state return in 
		return sharedState.getCurrentBehaviour()==2; 
	}
}