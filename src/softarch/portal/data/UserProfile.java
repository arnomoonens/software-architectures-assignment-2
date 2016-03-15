package softarch.portal.data;

import java.util.Date;
import java.util.Map;

/**
 * This is an abstract superclass for all user profiles.
 * @author Niels Joncheere
 */
public abstract class UserProfile extends Data {
	protected	String	username;
	protected	String	password;
	protected	String	firstName;
	protected	String	lastName;
	protected	String	emailAddress;
	protected	Date	lastLogin;

	/**
	 * Returns an map that allows the system to add/update the account
	 * in any kind of database.
	 */
	public abstract Map<String, String> asData();

	/**
	 * When a user has logged in successfully, he will be
	 * redirected to this page.
	 */
	public abstract String getDefaultPage();

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public UserProfile setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}
}
