package softarch.portal.data;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import java.util.Date;
import java.util.HashMap; 
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Represents a <i>free subscription</i> user account.
 * @author Niels Joncheere
 */
public class FreeSubscription extends RegularUser {
	
	public FreeSubscription(HttpServletRequest request) {
		this(	request.getParameter("Username"),
			request.getParameter("Password"),
			request.getParameter("FirstName"),
			request.getParameter("LastName"),
			request.getParameter("EmailAddress"),
			new Date());
	}

	public FreeSubscription(ResultSet rs)
		throws SQLException, ParseException {

		this.username		= rs.getString("Username");
		this.password		= rs.getString("Password");
		this.firstName		= rs.getString("FirstName");
		this.lastName		= rs.getString("LastName");
		this.emailAddress	= rs.getString("EmailAddress");
		this.lastLogin		= df.parse(rs.getString("LastLogin"));
	}
	
	/**
	 * Creates a new <i>free subscription</i> account.
	 */
	public FreeSubscription(
			        String	username,
					String	password,
					String	firstName,
					String	lastName,
					String	emailAddress,
					Date	lastLogin) {

		this.username		= username;
		this.password		= password;
		this.firstName		= firstName;
		this.lastName		= lastName;
		this.emailAddress	= emailAddress;
		this.lastLogin		= lastLogin;
	}

	public String asXml() {
		return	"<FreeSubscription>" +
			"<username>" + normalizeXml(username) + "</username>" +
			// password is not returned,
			// as it should only be used internally
			"<firstName>" +
			normalizeXml(firstName) + 
			"</firstName>" +
			"<lastName>" + normalizeXml(lastName) + "</lastName>" +
			"<emailAddress>" + 
			normalizeXml(emailAddress) +
			"</emailAddress>" +
			"<lastLogin>" + df.format(lastLogin) + "</lastLogin>" +
			"</FreeSubscription>";
	}
	
	public Map<String, String> asData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("FirstName", firstName);
		map.put("LastName", lastName);
		map.put("Username", username);
		map.put("Password", password);
		map.put("EmailAddress", emailAddress);
		map.put("LastLogin", df.format(lastLogin));
		return map;
	}
}
