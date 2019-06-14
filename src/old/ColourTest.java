package old;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColourTest {

	public static void main(String[] args) {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		
		Port s2 = ev3brick.getPort("S2");
		Port s3 = ev3brick.getPort("S3");
		EV3ColorSensor leftColourSensor = new EV3ColorSensor(s2);
		EV3ColorSensor rightColourSensor = new EV3ColorSensor(s3);

		leftColourSensor.setFloodlight(false);
		
		SampleProvider leftColourSampler = leftColourSensor.getRGBMode();
		float[] leftColourSample = new float[leftColourSampler.sampleSize()];
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			
			leftColourSampler.fetchSample(leftColourSample, 0);
			
			LCD.drawString("R: " + leftColourSample[0], 1, 1);
			LCD.drawString("G: " + leftColourSample[1], 1, 2);
			LCD.drawString("B: " + leftColourSample[2], 1, 3);
			
		}
		
	}

}
