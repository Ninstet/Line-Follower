package states;

import main.Controller;
import main.Data;

public class DoubleBlack extends State {
	int angR, angL;
	boolean cont;

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
		cont = false;
		
		Controller.DATA.addLog("Checking right...");
		for (int i = 0; i < 9; i++) {
			if (LEFT_COLOUR_SENSOR.isBlack() && i <= 3) { cont = true ;}
			Controller.PILOT.rotate(10);
			if (RIGHT_COLOUR_SENSOR.isBlack()) { Controller.LED("AMBER"); angR = i * 10; }
		}
		Controller.LED("RED");
		Controller.PILOT.rotate(-90);
		
		Controller.DATA.addLog("Checking left...");
		for (int j = 0; j < 9; j++) {
			if (RIGHT_COLOUR_SENSOR.isBlack() && j <= 3) { cont = true ;}
			Controller.PILOT.rotate(-10);
			if (LEFT_COLOUR_SENSOR.isBlack()) { Controller.LED("AMBER"); angL = j * 10; }
		}
		Controller.LED("RED");
		Controller.DATA.addLog("L: " + angL + " / R: " + angR);
		
		//Math.abs(((angR+angL)/2)-90) <= 20
		if (Math.abs(angR - angL) <= 20) {
			if (cont == true) {
				Controller.DATA.addLog("Move forward.");
				Controller.PILOT.rotate(80);
			} else {
				if (Controller.DATA.getLogs()[2].contains("--LEFT BLACK--")) {
					Controller.DATA.addLog("Continue right.");
					Controller.PILOT.rotate(90 + angR + 20);
					
				} else {
					Controller.DATA.addLog("Continue left.");
					Controller.PILOT.rotate(90 - angL - 20);
				}
			}
			
		} else if (angL > angR) {
			Controller.DATA.addLog("Turn right.");
			Controller.PILOT.rotate(90 + angR + 20);
			
		} else if (angL < angR) {
			Controller.DATA.addLog("Turn left.");
			Controller.PILOT.rotate(90 - angL - 20);
		}
		
		Controller.DATA.addLog("Done!");
	}

}
