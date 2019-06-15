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
		followWall(20, 5);
		
//		for (int i = 0; i < 2; i++) {
//			followWall(15, 3, 30);
//			
//			Controller.LED("AMBER");
//			Controller.PILOT.travel(15);
//			Controller.PILOT.rotate(90);
//			Controller.PILOT.travel(15);
//			Controller.LED("RED");
//		}
		
		Controller.DATA.addLog("Line found.");
		Controller.PILOT.arcForward(-1);
		Controller.LED("AMBER");
		while (!LEFT_COLOUR_SENSOR.isBlack()) {}
		Controller.PILOT.forward();
		Controller.DATA.addLog("Done!");
	}
	
	private void followWall(double distance, double tolerance) {
		IR_SENSOR.setAngle(135);
		
		while (!LEFT_COLOUR_SENSOR.isBlack() && !RIGHT_COLOUR_SENSOR.isBlack()) {
			Controller.PILOT.forward();
			while (Math.abs(IR_SENSOR.getDistance() - distance) < tolerance && !LEFT_COLOUR_SENSOR.isBlack() && !RIGHT_COLOUR_SENSOR.isBlack()) {}
			
			if (IR_SENSOR.getDistance() < distance - tolerance) {
				Controller.LED("AMBER");
				Controller.PILOT.arcForward(-Data.WHEEL_SEPARATION);
				while (IR_SENSOR.getDistance() < distance && !LEFT_COLOUR_SENSOR.isBlack() && !RIGHT_COLOUR_SENSOR.isBlack()) {}
				
			} else if (IR_SENSOR.getDistance() > distance - tolerance) {
				Controller.LED("AMBER");
				Controller.PILOT.arcForward(Data.WHEEL_SEPARATION);
				while (IR_SENSOR.getDistance() > distance && !LEFT_COLOUR_SENSOR.isBlack() && !RIGHT_COLOUR_SENSOR.isBlack()) {}
			}
			
			Controller.LED("RED");
		}
		
		IR_SENSOR.setAngle(0);
	}

}
