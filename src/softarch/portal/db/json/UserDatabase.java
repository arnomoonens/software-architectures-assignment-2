package softarch.portal.db.json;

import java.util.Iterator;
import java.util.Map;

import softarch.portal.data.CheapSubscription;
import softarch.portal.data.ExpensiveSubscription;
import softarch.portal.data.ExpertAdministrator;
import softarch.portal.data.ExternalAdministrator;
import softarch.portal.data.FreeSubscription;
import softarch.portal.data.Operator;
import softarch.portal.data.RegularAdministrator;
import softarch.portal.data.UserProfile;
import softarch.portal.db.sql.DatabaseException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class UserDatabase extends Database {
	
	public UserDatabase(String dbUrl) {
		super(dbUrl);
	}
	
	private JSONObject createJSONProfile(UserProfile profile) {
		Map<String, String> map = profile.asInsertData();
		JSONObject JSONProfile = new JSONObject();
		for(Map.Entry<String, String> entry : map.entrySet()) {
			JSONProfile.put(entry.getKey(), entry.getValue());
		}
		JSONProfile.put("UserType", profile.getClass().getSimpleName());
		return JSONProfile;
	}

	public void insert(UserProfile profile) throws DatabaseException {
		JSONObject newUser = createJSONProfile(profile);
		JSONObject content = super.readObject();
		JSONArray users = (JSONArray) content.get("users");
		users.add(newUser);
		JSONObject newContent = new JSONObject();
		newContent.put("users", users);
		super.writeObject(newContent);
	}

	public UserProfile findUser(String username) throws DatabaseException {
		try {
			JSONObject content = super.readObject();
			JSONArray users = (JSONArray) content.get("users");
			Iterator it = users.iterator();
			UserProfile profile = null;
			while(it.hasNext()) {
				JSONObject user = (JSONObject) it.next();
				if (user.get("Username").equals(username)) {
					if (user.get("UserType").equals("CheapSubscription")) {
						profile = new CheapSubscription(
								user.get("Username").toString(),
								user.get("Password").toString(),
								user.get("FirstName").toString(),
								user.get("LastName").toString(),
								user.get("EmailAddress").toString(),
								df.parse(user.get("LastLogin").toString()));
						break;
					} else if (user.get("UserType").equals("ExpensiveSubscription")) {
						profile = new ExpensiveSubscription(
								user.get("Username").toString(),
								user.get("Password").toString(),
								user.get("FirstName").toString(),
								user.get("LastName").toString(),
								user.get("EmailAddress").toString(),
								df.parse(user.get("LastLogin").toString()));
						break;
					} else if (user.get("UserType").equals("ExpertAdministrator")) {
						profile = new ExpertAdministrator(
								user.get("Username").toString(),
								user.get("Password").toString(),
								user.get("FirstName").toString(),
								user.get("LastName").toString(),
								user.get("EmailAddress").toString(),
								df.parse(user.get("LastLogin").toString()));
						break;
					} else if (user.get("UserType").equals("ExternalAdministrator")) {
						profile = new ExternalAdministrator(
								user.get("Username").toString(),
								user.get("Password").toString(),
								user.get("FirstName").toString(),
								user.get("LastName").toString(),
								user.get("EmailAddress").toString(),
								df.parse(user.get("LastLogin").toString()));
						break;
					} else if (user.get("UserType").equals("FreeSubscription")) {
						profile = new FreeSubscription(
								user.get("Username").toString(),
								user.get("Password").toString(),
								user.get("FirstName").toString(),
								user.get("LastName").toString(),
								user.get("EmailAddress").toString(),
								df.parse(user.get("LastLogin").toString()));
						break;
					} else if (user.get("UserType").equals("Operator")) {
						profile = new Operator(
								user.get("Username").toString(),
								user.get("Password").toString(),
								user.get("FirstName").toString(),
								user.get("LastName").toString(),
								user.get("EmailAddress").toString(),
								df.parse(user.get("LastLogin").toString()));
						break;
					} else if (user.get("UserType").equals("RegularAdministrator")) {
						profile = new RegularAdministrator(
								user.get("Username").toString(),
								user.get("Password").toString(),
								user.get("FirstName").toString(),
								user.get("LastName").toString(),
								user.get("EmailAddress").toString(),
								df.parse(user.get("LastLogin").toString()));
						break;
					} else {
						throw new DatabaseException("Invalid UserType!");
					}
				}
			}
			if (profile != null) {
				return profile;
			} else {
				throw new DatabaseException("Invalid username!");
			}
		} catch (java.text.ParseException e) {
			throw new DatabaseException(
					"Parse Exception: " + e.getMessage());
		}
	}

	public boolean userExists(String username) throws DatabaseException {
		JSONObject content = super.readObject();
		JSONArray users = (JSONArray) content.get("users");
		Iterator it = users.iterator();
		while(it.hasNext()) {
			JSONObject user = (JSONObject) it.next();
			if (user.get("Username").equals(username)) {
				return true;
			}
		}
		return false;
	}

	public void update(UserProfile profile) throws DatabaseException {
		JSONObject content = super.readObject();
		JSONArray users = (JSONArray) content.get("users");
		Iterator it = users.iterator();
		while(it.hasNext()) {
			JSONObject user = (JSONObject) it.next();
			if (user.get("Username").equals(profile.getUsername())) {
				Map<String, String> data = profile.asInsertData();
				user.put("Username", data.get("Username"));
				user.put("Password", data.get("Password"));
				user.put("FirstName", data.get("FirstName"));
				user.put("LastName", data.get("LastName"));
				user.put("EmailAddress", data.get("EmailAddress"));
				content.put("users", users);
				return;
			}
		}
	return;	
	}
	
}
