package main;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import sensors.Gyro;

public class Test {

	public static final EV3 EV3_BRICK = (EV3) BrickFinder.getLocal();
	public static final Keys KEYS = EV3_BRICK.getKeys();
	
	public static void main(String[] args) {
		Gyro GYRO = new Gyro(EV3_BRICK.getPort("S3"));
		
		while (KEYS.getButtons() != Keys.ID_ESCAPE) {
			LCD.drawString(Double.toString(GYRO.getAngle()), 1, 1);
//			LCD.drawString(Double.toString(sensor.getDistance()), 1, 1);
//			sensor.setAngle(120);
//			LCD.drawString(Double.toString(sensor.getDistance()), 1, 2);
//			sensor.setAngle(-130);
		}
	}

}