package old;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class WallStopper {
	
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);

	public static void main(String[] args) {
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		Port s1 = ev3brick.getPort("S1");
		EV3IRSensor sonicSensor = new EV3IRSensor(s1);
		
		SampleProvider rangeSampler = sonicSensor.getDistanceMode();
		float[] sample = new float[rangeSampler.sampleSize()];
		
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 5.5).offset(-6.0);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 5.5).offset(6.0);
		
		Chassis chassis = new WheeledChassis(new Wheel[] {wheel1, wheel2 },
				WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		pilot.setLinearSpeed(5.0);
		
		// Main
		
		buttons.waitForAnyPress();

		rangeSampler.fetchSample(sample, 0);
		while (sample[0] > 0.01 || sample[0] < 0.0) {
				
			LCD.clear();
			LCD.drawString("Distance: " + sample[0], 0, 1);
			rangeSampler.fetchSample(sample, 0);
			pilot.travel(1.0);
			
		}
		
		
	}

}
