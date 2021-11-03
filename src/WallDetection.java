

import java.util.Random;

import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class WallDetection implements Behavior {
	
	
	final static float DEGREE_TURN = 50;
	
	final static float BACKWARD_TRAVEL = -50;
	//TODO update with claw
	final static float WALL_PROXIMITY  = 0.10f;
	

	private MovePilot pilot; 

	private SampleProvider ultrasonicSensorSampleProvider;
	
	private State sharedState;
	
	private Random rgen = new Random(); 
	
	// holds value provided by the sample provider
	private float[] distance = new float[1]; 
	
	
	WallDetection(State state,MovePilot robotPilot, SampleProvider sp) {
		sharedState=state;
		pilot = robotPilot;
		ultrasonicSensorSampleProvider = sp;
	}
	
	
	public void action() {
		LCD.clear();
		LCD.drawString("Wall Detection called", 1, 1);
		// move the robot backwards by the distance
		pilot.travel(BACKWARD_TRAVEL);
		// right or left 30 degrees at random.
		pilot.rotate((2 * rgen.nextInt(2) - 1) * DEGREE_TURN); 
	}
	
	
	public void suppress() {} 
	
	
	public boolean takeControl() { 
		// if the robot is not in finding object return false
		if (!sharedState.isFindingObject()) {
			return false;
		}
		// fetches a new sample from ultrasonic sensor
		ultrasonicSensorSampleProvider.fetchSample(distance, 0);
		// returns true if distance is less than the allowed wall proximity value
		return distance[0] < WALL_PROXIMITY; 
	}
}