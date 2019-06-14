package main;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.Sounds;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import states.State;

public class Controller {
	
	public static final Data DATA = new Data();
	
	public static final EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	public static final EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);
	public static final Wheel WHEEL1 = WheeledChassis.modelWheel(LEFT_MOTOR, 5.697).offset(-Data.WHEEL_SEPARATION/2);
	public static final Wheel WHEEL2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 5.697).offset(Data.WHEEL_SEPARATION/2);
	public static final Chassis CHASSIS = new WheeledChassis(new Wheel[] {WHEEL1, WHEEL2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	
	public static final MovePilot PILOT = new MovePilot(CHASSIS);
	public static final EV3 EV3_BRICK = (EV3) BrickFinder.getLocal();
	public static final Keys KEYS = EV3_BRICK.getKeys();

	public Controller() {
		init();
		
		PILOT.forward();
		
		exit: while (KEYS.getButtons() != Keys.ID_ESCAPE) {
			for (State s : State.STATES) {
				if (s.active()) {
					s.control();
					PILOT.forward();
					continue exit;
				}
			}
		}
	}
	
	private static void init() {
		DATA.addLog("Initialising...");
		
		State.DOUBLE_BLACK.active();
		
		PILOT.rotate(10.0);
		PILOT.rotate(-20.0);
		PILOT.rotate(10.0);
		PILOT.setLinearSpeed(Data.LINEAR_SPEED);
		PILOT.setAngularSpeed(Data.ANGULAR_SPEED);
		PILOT.setLinearAcceleration(1.0);
		
		DATA.addLog("Done!");
		
		Sound.playNote(Sounds.FLUTE, 1500, 200);
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
		Sound.playNote(Sounds.FLUTE, 1500, 200);

		DATA.addLog("Press any key...");
		KEYS.waitForAnyPress();
		DATA.clearLogs();
	}

	public static void main(String[] args) {
		new Controller();
	}

}
