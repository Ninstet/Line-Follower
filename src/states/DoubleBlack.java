package states;

import main.Controller;
import main.Data;

public class DoubleBlack extends State {

	@Override
	public boolean active() {
		return LEFT_COLOUR_SENSOR.isBlack() && RIGHT_COLOUR_SENSOR.isBlack();
	}

	@Override
	public void control() {
		Controller.DATA.addLog("--DOUBLE BLACK--");
		Controller.LED("RED");
		
		Controller.PILOT.travel(Data.WHEEL_SEPARATION/2);
		
		boolean cont = false;
		double angR = 180.0;
		double angL = 180.0;
		double forward = GYRO.getAngle();
		
		Controller.DATA.addLog("Checking right...");
		Controller.PILOT.arcForward(Data.ARC_RADIUS);
		
		while (Math.abs(GYRO.getAngle() - forward) < 90) {
			if (LEFT_COLOUR_SENSOR.isBlack() && Math.abs(GYRO.getAngle() - forward) < 30) { cont = true; break; }
			if (RIGHT_COLOUR_SENSOR.isBlack()) {
				Controller.LED("AMBER");
				angR = Math.abs(forward - GYRO.getAngle());
				break;
			}
		}
		
		Controller.LED("RED");
		GYRO.rotateTo(forward);
		
		Controller.DATA.addLog("Checking left...");
		Controller.PILOT.arcForward(-Data.ARC_RADIUS);
		
		while (Math.abs(GYRO.getAngle() - forward) < 90 && !cont) {
			if (RIGHT_COLOUR_SENSOR.isBlack() && Math.abs(GYRO.getAngle() - forward) < 30) { cont = true; break; }
			if (LEFT_COLOUR_SENSOR.isBlack()) {
				Controller.LED("AMBER");
				angL = Math.abs(forward - GYRO.getAngle());
				break;
			}
		}
		
		Controller.LED("RED");
		
		if (cont == true) {
			Controller.DATA.addLog("Move forward.");
			GYRO.rotateTo(forward);
			
		} else {
			Controller.DATA.addLog("L: " + ((int)angL) + " / R: " + ((int)angR));
			
			if (angL > angR) {
				Controller.DATA.addLog("Turn right.");
				GYRO.rotateTo(forward);
				Controller.PILOT.arcForward(Data.ARC_RADIUS);
				while (!RIGHT_COLOUR_SENSOR.isBlack()) {}
				Controller.PILOT.rotate(20);
				
			} else if (angL < angR) {
				Controller.DATA.addLog("Turn left.");
				Controller.PILOT.rotate(-20);
			}
		}
		
		Controller.DATA.addLog("Done!");
	}

}
