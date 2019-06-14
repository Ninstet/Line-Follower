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
		
		Controller.PILOT.travel(Data.WHEEL_SEPARATION/2);
		
		
		
//		Controller.PILOT.travel(-Data.WHEEL_SEPARATION/2);
		
		Controller.DATA.addLog("Done!");
	}

}
