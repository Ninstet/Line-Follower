package states;

import main.Controller;
import main.Data;

public class RightBlack extends State {

	@Override
	public boolean active() {
		return !LEFT_COLOUR_SENSOR.isBlack() && RIGHT_COLOUR_SENSOR.isBlack();
	}

	@Override
	public void control() {
		Controller.DATA.addLog("--RIGHT BLACK--");
		
		Controller.PILOT.arcForward(Data.ARC_RADIUS);
		while (active()) {}
		Controller.LED("AMBER");
		
		if (DOUBLE_BLACK.active()) {						// Space was never right black, we just had to move on to double black space
			Controller.DATA.addLog("* Double black *");
			DOUBLE_BLACK.control();
		} else {													// Space was right black and now we continue moving
			Controller.PILOT.rotate(5);
			if (DOUBLE_BLACK.active()) { Controller.DATA.addLog("* Double black *"); DOUBLE_BLACK.control(); }
		}
		
		Controller.DATA.addLog("Done!");
	}

}
