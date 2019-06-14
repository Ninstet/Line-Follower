package states;

import main.Controller;
import main.Data;

public class LeftGreen extends State {

	@Override
	public boolean active() {
		return LEFT_COLOUR_SENSOR.isGreen() && !RIGHT_COLOUR_SENSOR.isGreen();
	}

	@Override
	public void control() {
		Controller.DATA.addLog("--LEFT GREEN--");
		
		Controller.PILOT.setLinearSpeed(1.0);
		Controller.PILOT.forward();
		while (active()) {}
		Controller.PILOT.setLinearSpeed(Data.LINEAR_SPEED);
		Controller.PILOT.forward();
		
		if (LEFT_COLOUR_SENSOR.isBlack() || RIGHT_COLOUR_SENSOR.isBlack()) {	// Space was left green and now we have reached junction
			Controller.DATA.addLog("Turn left.");
			Controller.PILOT.travel(Data.WHEEL_SEPARATION/2);
			Controller.PILOT.rotate(-80.0);
			
		} else if (DOUBLE_GREEN.active()) {					// Space was never left green, we just had to move on to double green space
			Controller.DATA.addLog("* Double green *");
			DOUBLE_GREEN.control();
			
		}													// Else space was left green however no junction after so we continue moving
		
		Controller.DATA.addLog("Done!");
	}

}
