package railway.network;

/**
 * <p>Used to denote the direction.</p>
 * @author Jay Kahlil Hussaini
 *
 */
public enum Direction {
	UP, DOWN;
	
	private Direction opposite;
	
	static {
		UP.opposite = DOWN;
		DOWN.opposite = UP;
	}
	
	public Direction toggle() {
		return opposite;
	}
}
