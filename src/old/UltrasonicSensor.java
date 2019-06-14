package old;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

public class UltrasonicSensor {

	public static void main(String[] args) {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		Keys buttons = ev3brick.getKeys();
		
		Port s1 = ev3brick.getPort("S1");
		
		EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(s1);
		
		SampleProvider sonicdistance = sonicSensor.getDistanceMode();
		
		float[] sample = new float[sonicdistance.sampleSize()];
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			sonicdistance.fetchSample(sample, 0);
			LCD.clear();
			LCD.drawString("Distance: " + sample[0], 0, 1);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
		}
		
		sonicSensor.close();
		
	}

}
