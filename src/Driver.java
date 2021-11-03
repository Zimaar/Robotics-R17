import java.util.Timer;
import java.util.TimerTask;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {
	
	
	// The diameter (mm) of the wheels
	final static float WHEEL_DIAMETER = 55; 
	// The distance (mm) of the two driven wheels
	final static float AXLE_LENGTH = 150; 
	// The linear speed (mm/s) of the robot for backwards and forward travel
	final static float LINEAR_SPEED = 70; 
	
	// the period before updating the state
	final static int STATE_DELAY = 4000; 
	
// 	public static Socket btSocket;
// 	private static BufferedInputStream bis = null;
	
	
	
	public static void main(String[] args) {
		// creates a new pilot object for the robot
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.B);
		// creates a new UltraSonic Sensor for the robot connected to Sensor Port 1
		EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
		// creates a Sample Provider for ultrasonic provider
		SampleProvider ultrasonicSensorSampleProvider =  ultrasonicSensor.getDistanceMode();
		// creates a new Colour Sensor for the robot connected to Sensor Port 2
		EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S2);
		// set the linear speed of the pilot
		pilot.setLinearSpeed(LINEAR_SPEED);
		
		// creates a sharedState 
		State sharedState = new State();
		// runs the timer as a daemon 
		Timer timer = new Timer(true);
		
        TimerTask StateManagerTask = new StateManagerTask(sharedState); 

		// calls the run method after each delay
		timer.schedule(StateManagerTask, 0,STATE_DELAY);
		
// 		SocketAddress sa = new InetSocketAddress(IPADDRESS, EV3PORT); Code commented out is related to bluetooth, this was
// 									      this was attempted and almost worked but testing it showed inconsistency
// 									      during testing.
// 		try {
// 			connection.connect(sa, 1500); // Timeout possible
// 		} catch (Exception ex) {
			
// 			LCD.drawString(ex.getMessage(), 0,6);
// 			connection = null;
// 		}
// 		if (connection != null) {
// 			in = new BufferedInputStream( connection.getInputStream());
// 			out= connection.getOutputStream();
// 			LCD.drawString("Connected", 0, 0);
// 		}
// 		Button.ENTER.waitForPressAndRelease();
		
		// creates behaviours
		Behavior WallDetection = new WallDetection(sharedState,pilot,ultrasonicSensorSampleProvider);
		Behavior  Trundle = new Trundle (sharedState,pilot);
		Behavior  Rotate = new Rotate (sharedState,pilot);
		Behavior  TravelArc = new TravelArc (sharedState,pilot);
		Behavior BatteryLevel = new BatteryLevel();
// 		Behavior BluetoothReader = new Bluetooth Reader(bis);

		
		// creates the Arbitrator to coordinate the the behaviours
		Arbitrator arbitrator = new Arbitrator(new Behavior[] {Trundle,Rotate});
		// displays authors names
		LCD.drawString("Kate,Ramizz",2,2);
		LCD.drawString("Ismaeel,Dmani",2,3);
		// displays version name
		LCD.drawString("v4",2,4);
		// waits for users to press button before running the program
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		// runs the arbitrator
		arbitrator.go(); 
	}
	
	
	// create a MovePilot object using two motor ports
	private static MovePilot getPilot(Port left, Port right) {
		// Creates a the left wheel
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wLeft = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
		// Creates a the right wheel
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wRight = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		// adds both wheels to the chassis
		Chassis chassis = new WheeledChassis((new Wheel[] {wRight, wLeft}),
		WheeledChassis.TYPE_DIFFERENTIAL);
		// returns the new MovePilot object
		return new MovePilot(chassis);
	}
	
	private static class StateManagerTask extends TimerTask {
		
		private State sharedState;
		
		public StateManagerTask(State sharedState) {
			this.sharedState = sharedState;
		}

		@Override
		public void run() {
			
			sharedState.generateRadomBehaviour(); 
			LCD.drawString(""+sharedState.getCurrentBehaviour(), 1, 2);
			
		}
		
	}
	
	
}
