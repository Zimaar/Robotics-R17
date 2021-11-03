
import lejos.hardware.lcd.LCD;
import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class BatteryLevel implements Behavior  {
  
    final static float MINIMUM_BATTERY_VOLTAGE = 1.00f;

    
    private boolean SUPPRESS_FLAG = false;
    private float powerLevel;


    public BatteryLevel() {}

    

    public void action() {

    
	    while(!SUPPRESS_FLAG) {
	    	LCD.clear();
	    	LCD.drawString( "Battery Level Low :  "+powerLevel,2,2 );
	    	// beeps to alert 
	    	Sound.beepSequenceUp();
	    	// start the LEDs flashing Green
	    	Button.LEDPattern(4);
	        Delay.msDelay(1000);
	    	
	    }
    }
    
    public void suppress() {

        SUPPRESS_FLAG = true;

    }

    public boolean takeControl() {
    	powerLevel = Battery.getVoltage();
    	return powerLevel < MINIMUM_BATTERY_VOLTAGE;
    }

}
