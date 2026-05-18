package co.edu.unbosque.Revista.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.Revista.exception.InvalidCredentialException;
import co.edu.unbosque.Revista.exception.PasswordNotValidException;
import co.edu.unbosque.Revista.exception.ResourceNotFoundException;
import co.edu.unbosque.Revista.exception.UnauthorizedAccessException;
import co.edu.unbosque.Revista.exception.UserAlreadyExistsException;

public class LanzadorDeException {

	public static void verifyPassword(String password) {
		if (password == null || password.trim().isEmpty()) {
			throw new PasswordNotValidException();
		}

		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.\\-_])[A-Za-z\\d@$!%*?&.\\-_]{8,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);

		if (!matcher.matches()) {
			throw new PasswordNotValidException();
		}
	}

	public static void verifyUniqueUsername(boolean exists, String username) {
		if (exists) {
			throw new UserAlreadyExistsException("Error: The username '" + username + "' is already taken.");
		}
	}

	public static void verifyCredentials(boolean isValid) {
		if (!isValid) {
			throw new InvalidCredentialException();
		}
	}

	public static void verifyResourceFound(boolean exists, String resourceName) {
		if (!exists) {
			throw new ResourceNotFoundException(
					"Error: The requested resource (" + resourceName + ") was not found in the database.");
		}
	}

	public static void verifyAccess(boolean hasPermission) {
		if (!hasPermission) {
			throw new UnauthorizedAccessException(
					"Action denied: You do not have the required permissions to perform this action.");
		}
	}

}
