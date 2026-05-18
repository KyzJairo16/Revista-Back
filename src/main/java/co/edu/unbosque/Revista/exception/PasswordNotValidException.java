package co.edu.unbosque.Revista.exception;

public class PasswordNotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8871780593060508089L;

	public PasswordNotValidException() {
		super("La contraseña no cumple con el estándar " + "(mínimo 8 caracteres, al menos una letra minúscula, "
				+ "al menos una letra mayúscula, " + "al menos un número y al menos un símbolo).");
	}
}
