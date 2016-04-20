package softarch.portal.app;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;

import java.util.List;

import javax.xml.rpc.ServiceException;

import java.net.MalformedURLException;
import java.util.Date;

/**
 * This class implements a facade for all of the application layer's
 * functionality.
 * 
 * @author Niels Joncheere
 */
public class ApplicationFacade {
	private UserManager userManager;
	private QueryManager localQueryManager;
	private QueryManager webserviceQueryManager;
	private AdministrationManager administrationManager;
	private OperationManager operationManager;
	private String currentUserName;

	/**
	 * Creates a new application facade.
	 */
	public ApplicationFacade(String dbType, String dbUser, String dbPassword, String dbUrl) {

		softarch.portal.db.DatabaseFacade dbFacade = null;
		
		if(dbType.equals("sql")) {
			dbFacade = new softarch.portal.db.sql.DatabaseFacade(dbUser, dbPassword, dbUrl);
		} else if (dbType.equals("json")) {
			dbFacade = new softarch.portal.db.json.DatabaseFacade(dbUser, dbPassword, dbUrl);
		} else {
			throw new Error("Not a valid database type given: " + dbType);
		}

		userManager = new UserManager(dbFacade);
		localQueryManager = new QueryManager(dbFacade);
		try {
			webserviceQueryManager = new QueryManager(new softarch.portal.db.webservice.DatabaseFacade());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		administrationManager = new AdministrationManager(dbFacade);
		operationManager = new OperationManager(dbFacade);
	}

	/**
	 * Adds a user profile to the user database.
	 * 
	 * @param profile
	 *            The profile to be added.
	 */
	public void add(UserProfile profile) throws ApplicationException {

		userManager.add(profile);
	}

	/**
	 * Returns the user profile for the user with the specified username.
	 */
	public UserProfile findUser(String username) throws ApplicationException {

		return userManager.findUser(username);
	}

	/**
	 * Returns the user profile for the user with the specified username, and
	 * throws an exception if his session ID does not match the one that is
	 * provided as a parameter.
	 */
	public UserProfile findUser(String username, Number sessionId) throws ApplicationException {

		return userManager.findUser(username, sessionId);
	}

	/**
	 * Logs in the user with the specified username and password.
	 */
	public Number login(String username, String password) throws ApplicationException {

		return userManager.login(username, password);
	}

	/**
	 * Returns a list of all users that are currently logged in.
	 */
	public List getActiveUsers() throws ApplicationException {

		return userManager.getActiveUsers();
	}

	/**
	 * Logs out the user with the specified username and session ID.
	 */
	public void logout(String username, Number sessionId) throws ApplicationException {

		userManager.logout(username, sessionId);
	}

	/**
	 * Returns a list containing all records of the given information type that
	 * match the given query string.
	 * 
	 * @param informationType
	 *            The information type the user wants to query.
	 * @param queryString
	 *            The query string that should be used to carry out the search
	 *            (for example "+foo -bar").
	 */
	public List findRecords(String informationType, String queryString) throws ApplicationException {
		List webserviceResults = webserviceQueryManager.findRecords(informationType, queryString);
		List localResults = localQueryManager.findRecords(informationType, queryString);
		localResults.addAll(webserviceResults);
		return localResults;
	}

	/**
	 * Returns a list containing all records of the given information type that
	 * were added after the given date.
	 */
	public List findRecordsFrom(String informationType, Date date) throws ApplicationException {
		List webserviceResults = webserviceQueryManager.findRecordsFrom(informationType, date);
		List localResults = localQueryManager.findRecordsFrom(informationType, date);
		localResults.addAll(webserviceResults);
		return localResults;
	}

	/**
	 * Adds a new regular data object to the regular database.
	 * 
	 * @param rd
	 *            The regular data object that should be added to the regular
	 *            database.
	 */
	public void add(RegularData rd) throws ApplicationException {

		administrationManager.add(rd);
	}

	/**
	 * Returns a list that contains all raw data that is currently stored in the
	 * raw database.
	 */
	public List getRawData() throws ApplicationException {

		return administrationManager.getRawData();
	}

	/**
	 * Returns a specific raw data object.
	 */
	public RawData getRawData(int id) throws ApplicationException {

		return administrationManager.getRawData(id);
	}

	/**
	 * Creates a new raw data object with an empty source. The structure of the
	 * object is specified by the <code>rd</code> parameter.
	 */
	public void addRawData(RegularData rd) throws ApplicationException {

		administrationManager.addRawData(rd);
	}

	/**
	 * Deletes a raw data object.
	 * 
	 * @param rd
	 *            The raw data object that should be deleted.
	 */
	public void deleteRawData(RawData rd) throws ApplicationException {

		administrationManager.deleteRawData(rd);
	}

	/**
	 * Updates a raw data object.
	 * 
	 * @param rd
	 *            The raw data object that should be updated.
	 */
	public void updateRawData(RawData rd) throws ApplicationException {

		administrationManager.updateRawData(rd);
	}

	/**
	 * Returns the number of records of the given information type in the
	 * regular database.
	 */
	public int getNumberOfRegularRecords(String informationType) throws ApplicationException {

		return operationManager.getNumberOfRegularRecords(informationType);
	}

	/**
	 * Returns the number of records in the raw database.
	 */
	public int getNumberOfRawRecords() throws ApplicationException {

		return operationManager.getNumberOfRawRecords();
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}
	
	
}
