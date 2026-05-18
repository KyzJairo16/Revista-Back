package co.edu.unbosque.Revista.exception;

public class InvalidCredentialException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818716819853089239L;
	
	public InvalidCredentialException() {
		super("Nombre de usuario o contraseña incorrectos. Inténtalo de nuevo");
	}
	
	

}
