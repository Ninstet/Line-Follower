package states;

import main.Controller;
import main.Data;

public class DoubleBlack extends State {
	int angR, angL;

	@Override
	public boolean active() {
		return LEFT_COLOUR_SENSOR.isBlack() && RIGHT_COLOUR_SENSOR.isBlack();
	}

	@Override
	public void control() {
		Controller.DATA.addLog("--DOUBLE BLACK--");
		Controller.LED("RED");
		
		Controller.PILOT.travel(Data.WHEEL_SEPARATION/2);
		
		angR = 180;
		angL = 180;
		
		Controller.DATA.addLog("Checking right...");
		for (int i = 0; i < 9; i++) {
			Controller.PILOT.rotate(10);
			if (RIGHT_COLOUR_SENSOR.isBlack()) { Controller.LED("AMBER"); angR = i * 10; }
		}
		Controller.LED("RED");
		Controller.PILOT.rotate(-90);
		
		Controller.DATA.addLog("Checking left...");
		for (int j = 0; j < 9; j++) {
			Controller.PILOT.rotate(-10);
			if (LEFT_COLOUR_SENSOR.isBlack()) { Controller.LED("AMBER"); angL = j * 10; }
		}
		Controller.LED("RED");
		Controller.DATA.addLog("L: " + angL + " / R: " + angR);
		
		if (Math.abs(angR - angL) <= 10) {
			Controller.PILOT.rotate(90);
			Controller.DATA.addLog("Move forward.");
		} else if (angL > angR) {
			Controller.DATA.addLog("Turn right.");
			Controller.PILOT.rotate(90 + angR + 10);
		} else if (angL < angR) {
			Controller.DATA.addLog("Turn left.");
			Controller.PILOT.rotate(90 - angL - 10);
		}
		
		Controller.DATA.addLog("Done!");
	}

}
