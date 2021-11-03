

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
	
	private MovePilot pilot; 
	
	private State sharedState;
	
	
	
	Trundle(State state,MovePilot robotPilot) {
		sharedState = state;
		pilot = robotPilot;
		
	}
	
	public void action() {
		// moves the robot forward if pilot not already moving
		if (!pilot.isMoving()) {
			pilot.forward();
		}
		

	}
	
	
	public void suppress() {} 
	
	
	public boolean takeControl() { 
		// if the robot is not in finding object return false
		if (!sharedState.isFindingObject()) {
			return false;
		}
		// returns true if the shared state return in 0
		return sharedState.getCurrentBehaviour()==0; 
	}
}