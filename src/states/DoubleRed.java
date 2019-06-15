package states;

import lejos.hardware.Sound;
import lejos.hardware.Sounds;
import main.Controller;

public class DoubleRed extends State {

	@Override
	public boolean active() {
		return LEFT_COLOUR_SENSOR.isRed() && RIGHT_COLOUR_SENSOR.isRed();
	}

	@Override
	public void control() {
		Controller.DATA.addLog("--DOUBLE RED--");
		
		Sound.playNote(Sounds.XYLOPHONE, 1500, 200);
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
		Sound.playNote(Sounds.XYLOPHONE, 1500, 200);
		
		Controller.PILOT.stop();
		Controller.DATA.addLog("End reached.");
	}


}
