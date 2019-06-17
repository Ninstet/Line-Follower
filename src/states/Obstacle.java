package states;

import main.Controller;
import main.Data;

public class Obstacle extends State {

	@Override
	public boolean active() {
		return IR_SENSOR.getDistance() < 10;
	}

	@Override
	public void control() {
		Controller.DATA.addLog("--OBSTACLE--");
		Controller.PILOT.rotate(-90);
		
		Controller.DATA.addLog("Losing line...");		
		followWall(25, 5);
		Controller.DATA.addLog("Line found.");
		
		Controller.PILOT.arcForward(-1);
		while (!LEFT_COLOUR_SENSOR.isBlack()) {}
		Controller.PILOT.forward();
		Controller.DATA.addLog("Done!");
	}
	
	private void followWall(double distance, double tolerance) {
		IR_SENSOR.setAngle(135);
		
		while (!RIGHT_COLOUR_SENSOR.isBlack()) {
			Controller.PILOT.forward();
			while (Math.abs(IR_SENSOR.getDistance()-distance) < tolerance && !LEFT_COLOUR_SENSOR.isBlack() && !RIGHT_COLOUR_SENSOR.isBlack()) {}
			
			if (IR_SENSOR.getDistance() < distance - tolerance && !RIGHT_COLOUR_SENSOR.isBlack()) {
				Controller.LED("AMBER");
				Controller.PILOT.arcForward(-Data.WHEEL_SEPARATION);
				while (IR_SENSOR.getDistance() < distance && !LEFT_COLOUR_SENSOR.isBlack() && !RIGHT_COLOUR_SENSOR.isBlack()) {}
				
			} else if (IR_SENSOR.getDistance() > distance - tolerance && !RIGHT_COLOUR_SENSOR.isBlack()) {
				Controller.LED("AMBER");
				Controller.PILOT.arcForward(Data.WHEEL_SEPARATION);
				while (IR_SENSOR.getDistance() > distance && !LEFT_COLOUR_SENSOR.isBlack() && !RIGHT_COLOUR_SENSOR.isBlack()) {}
			}
			
			Controller.LED("RED");
		}
		
		Controller.PILOT.stop();
		Controller.LED("AMBER");
		IR_SENSOR.setAngle(0);
	}

}
