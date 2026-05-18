package co.edu.unbosque.Revista.exception;

public class UserAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6737476930458339502L;

	public UserAlreadyExistsException(String msg) {
		super(msg);
	}

}
