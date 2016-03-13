package softarch.portal.app;

import softarch.portal.db.sql.DatabaseFacade;
//import softarch.portal.db.json.DatabaseFacade;

/**
 * This class is an abstract superclass for all <i>managers</i>.
 * @author Niels Joncheere
 */
public abstract class Manager {
	protected DatabaseFacade dbFacade;
}
