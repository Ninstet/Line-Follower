package states;

import java.util.ArrayList;

import main.Controller;
import sensors.ColourSensor;

public abstract class State {
	
	public static final ArrayList<State> STATES = new ArrayList<>();

	public static final LeftBlack LEFT_BLACK = new LeftBlack();
	public static final LeftGreen LEFT_GREEN = new LeftGreen();
	public static final RightBlack RIGHT_BLACK = new RightBlack();
	public static final RightGreen RIGHT_GREEN = new RightGreen();
	public static final DoubleBlack DOUBLE_BLACK = new DoubleBlack();
	public static final DoubleGreen DOUBLE_GREEN = new DoubleGreen();
	
	protected static final ColourSensor LEFT_COLOUR_SENSOR = new ColourSensor(Controller.EV3_BRICK.getPort("S1"));
	protected static final ColourSensor RIGHT_COLOUR_SENSOR = new ColourSensor(Controller.EV3_BRICK.getPort("S2"));
	
	protected State() {
		if (!STATES.contains(this)) {
			STATES.add(this);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass());
	}
	
	public abstract boolean active();
	public abstract void control();

}
