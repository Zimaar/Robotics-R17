

import java.util.Random;

import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class TravelArc implements Behavior {
	
	final static double ARC_TRAVEL = 1.50;
	
	final static int MAX_RADIUS = 10;
	
	private Random rgen = new Random(); 

	

	private MovePilot pilot; 


	private State sharedState ;
	
	
	TravelArc(State state,MovePilot robotPilot) {
		sharedState = state;
		pilot = robotPilot;
			
	}
	
	
	public void action() {
		LCD.clear();
		LCD.drawString("Travel Arc Called called", 1, 1);
		// create a random radius upto maximum radius value
		double randomRadius=rgen.nextDouble()* MAX_RADIUS;
		pilot.travelArc(randomRadius,ARC_TRAVEL,true);
	}
	
	
	public void suppress() {} 
	
	
	public boolean takeControl() { 
		// if the robot is not in finding object return false
		if (!sharedState.isFindingObject()) {
			return false;
		}
		
		// returns true if the shared state return in 1
		return sharedState.getCurrentBehaviour()==1; 
	}
}