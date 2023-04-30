package stronghold.controller.messages;

public enum LoginMenuMessage {
	SPECIFY_REQUIRED_FIELDS("Error: please specify the required fields"),

	// login
	LOGIN_SUCCESS("user logged in successfully!"),
	USERNAME_NOT_FOUND("Username and password didn't match!"),
	INCORRECT_PASSWORD("Username and password didn't match!"),
	TRY_AFTER_DELAY("Try again after the delay"),

	FORGOT_PASSWORD_SUCCESSFUL("Password recovered successsfully!"),
	FORGOT_PASSWORD_NOT_SUCCESSFUL("Password couldn't be recovered!"),
	;

	private String errorString;

	private LoginMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}