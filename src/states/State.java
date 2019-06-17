package states;

import java.util.ArrayList;

import main.Controller;
import sensors.Colour;
import sensors.Gyro;
import sensors.IR;

public abstract class State {
	
	public static final ArrayList<State> STATES = new ArrayList<>();

	public static final LeftBlack LEFT_BLACK = new LeftBlack();
	public static final LeftGreen LEFT_GREEN = new LeftGreen();
	public static final RightBlack RIGHT_BLACK = new RightBlack();
	public static final RightGreen RIGHT_GREEN = new RightGreen();
	public static final DoubleBlack DOUBLE_BLACK = new DoubleBlack();
	public static final DoubleGreen DOUBLE_GREEN = new DoubleGreen();
	public static final DoubleRed DOUBLE_RED = new DoubleRed();
	public static final Obstacle OBSTACLE = new Obstacle();
	
	protected static final Colour LEFT_COLOUR_SENSOR = new Colour(Controller.EV3_BRICK.getPort("S1"));
	protected static final Colour RIGHT_COLOUR_SENSOR = new Colour(Controller.EV3_BRICK.getPort("S2"));
	protected static final Gyro GYRO = new Gyro(Controller.EV3_BRICK.getPort("S3"));
	protected static final IR IR_SENSOR = new IR(Controller.EV3_BRICK.getPort("S4"));
	
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
