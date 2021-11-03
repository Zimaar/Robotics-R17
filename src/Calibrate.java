
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class Calibrate implements Behavior{

	private static int DISPLAY_X = 1;
	private static int DISPLAY_Y = 3;
	private State sharedState; 
	final static int HIGH = 2; 
	final static int NORMAL = 1; 
	final static int LOW =  0; 
	private SampleProvider sampleProvider;
	private float[] samples = new float[3];

	public Calibrate(EV3ColorSensor ls,State ss){
		sampleProvider = ls.getAmbientMode();
		sharedState=ss;
		
	}

	public void getCalibrationValues(String displayString, int index) {
		LCD.clear();
		LCD.drawString(displayString, DISPLAY_X, DISPLAY_Y);
		Button.ENTER.waitForPressAndRelease();
		sampleProvider.fetchSample(samples, index);
		LCD.drawString("V:"+samples[index], DISPLAY_X, DISPLAY_Y+1);
		Button.ENTER.waitForPressAndRelease();
	
	}

	public void action(){
		LCD.clear();
		LCD.drawString("Light calibration. Please follow the instructions",DISPLAY_X,DISPLAY_Y);
		getCalibrationValues("Darkest Point?",LOW);
		getCalibrationValues("Any area?",NORMAL);
		getCalibrationValues("Brightest Point?",HIGH);
		float normalised = (samples[NORMAL] - samples[LOW]) / (samples[HIGH] - samples[LOW]);
		LCD.clear();
		LCD.drawString("Calibration successful",DISPLAY_X,DISPLAY_Y);
		sharedState.updateCalibration(true,normalised);
	}
	
	public void suppress() {} 

	public boolean takeControl() { 
		return !sharedState.isCalibrated();
	}
	
}
