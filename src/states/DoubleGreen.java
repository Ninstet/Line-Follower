package states;

import main.Controller;

public class DoubleGreen extends State {

	@Override
	public boolean active() {
		return LEFT_COLOUR_SENSOR.isGreen() && RIGHT_COLOUR_SENSOR.isGreen();
	}

	@Override
	public void control() {
		Controller.DATA.addLog("--DOUBLE GREEN--");
		
		Controller.DATA.addLog("Turn around.");
		Controller.PILOT.rotate(180);
		
		Controller.DATA.addLog("Done!");
	}

}
