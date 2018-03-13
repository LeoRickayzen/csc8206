package railway.network;

/**
 * <p>Used to denote the direction.</p>
 * @author Jay Kahlil Hussaini
 *
 */
public enum Direction {
	UP, DOWN;

	//Store the opposite Direction of this Direction.
	private Direction opposite;

	//Set the opposite Direction.
	static {
		UP.opposite = DOWN;
		DOWN.opposite = UP;
	}

	/**
	 * <p>Get the opposite Direction.</p>
	 * @return Direction this == UP ? DOWN : UP
	 */
	public Direction toggle() {
		return opposite;
	}
}
