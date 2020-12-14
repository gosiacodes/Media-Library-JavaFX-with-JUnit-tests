package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
	
	public static boolean validateSSN(String userSSN) {
		if (userSSN != null) {
			// requires a valid SSN with digits according to the pattern YYMMDD-XXXX, 
			// where month pattern is from 01 to 12, day pattern is from 01 to 31
			String SSN_PATTERN = "([0-9][0-9])+([0][1-9]|[1][0-2])+([0][1-9]|[1-2][0-9]|[3][0-1])+([-][0-9][0-9][0-9][0-9])";
			Pattern pattern = Pattern.compile(SSN_PATTERN);
			Matcher matcher = pattern.matcher(userSSN);
			if (matcher.matches())
				return true;
		}
		return false;
	}

	public static boolean validatePassword(String password) {
		if (password != null) {
			// requires a valid password with at least 2 numbers, 2 lower case letters, 2
			// upper case letters, 1 special character, no blank spaces, and at least 8 characters
			String PASSWORD_PATTERN = "(?=.*[0-9]{2,})(?=.*[a-z]{2,})(?=.*[A-Z]{2,})(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
			Matcher matcher = pattern.matcher(password);
			if (matcher.matches())
				return true;
		}
		return false;
	}

}