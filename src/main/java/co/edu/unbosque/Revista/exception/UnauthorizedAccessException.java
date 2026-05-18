package co.edu.unbosque.Revista.exception;

public class UnauthorizedAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 564622284936158647L;

	public UnauthorizedAccessException(String msg) {
		super(msg);
	}

}
