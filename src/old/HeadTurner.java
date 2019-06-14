package old;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class HeadTurner {

	public static void main(String[] args) {
		
		EV3MediumRegulatedMotor HEAD = new EV3MediumRegulatedMotor(MotorPort.B);
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		Keys buttons = ev3brick.getKeys();
		
		HEAD.rotateTo(-130);
		HEAD.rotateTo(140);
		HEAD.rotateTo(-130);
		HEAD.rotateTo(140);
		HEAD.rotateTo(0);
		
		HEAD.stop();
	}

}
