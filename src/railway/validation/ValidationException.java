package railway.validation;

/**
 * <p>Simple extension of {@link Exception} to be thrown when a {@link railway.network.Network Network} is invalid.</p>
 * 
 * @author Josh Corne
 *
 */
public class ValidationException extends Exception
{
    /**
	 * <p>Eclipse got mad so I added this. - Jay</p>
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(String s)
    {
        super(s);
    }
}
