package softarch.portal.db.webservice;

import librarysearch.soft.*;
import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.xml.rpc.ServiceException;

import org.apache.axis.message.MessageElement;

public class DatabaseFacade implements softarch.portal.db.DatabaseFacade {
	private LibrarySearchSOAPBindingStub service;

	public DatabaseFacade() throws MalformedURLException, ServiceException {
		service = (LibrarySearchSOAPBindingStub) new LibrarySearchServiceLocator()
				.getLibrarySearchServicePort(new URL("http://localhost:8080/ode/processes/LibrarySearchService"));
	}

	private List toBooksList(BookList books) throws Exception {
		List results = new ArrayList();
		for(MessageElement element : books.get_any()) {
			if(element.getName().equals("searchBooksResponse")) {
				Iterator<MessageElement> iterator = element.getChildren().iterator();
				while(iterator.hasNext()) {
					MessageElement current = iterator.next();
					be.ac.vub.soft.Book book = (be.ac.vub.soft.Book) current.getObjectValue(be.ac.vub.soft.Book.class);
					Calendar c = Calendar.getInstance();
					c.set(book.getYear(), 0, 0);
					softarch.portal.data.Book internalBook = new softarch.portal.data.Book(
							new Date(),
							book.getAuthor(),
							book.getIsbn(),
							0,
							c.getTime(),
							book.getPublisher(),
							"",
							"",
							book.getTitle());
					results.add(internalBook);
				}
			} else {
				be.library.Book book = (be.library.Book) element.getObjectValue(be.library.Book.class);
				softarch.portal.data.Book internalBook = new softarch.portal.data.Book(
						new Date(),
						book.getAuthor(),
						Long.parseLong(book.getIsbn()),
						0,
						book.getDate().getTime(),
						book.getPublisher(),
						"",
						"",
						book.getTitle());
				results.add(internalBook);
				
			}
		}
		return results;
	}

	public List findRecords(String informationType, String queryString) {
		if (!informationType.equals("Book")) return new ArrayList(); //Return empty list if Books aren't needed
		LibrarySearchRequest req = new LibrarySearchRequest(queryString);
		LibrarySearchResponse res;
		try {
			res = service.process(req);
			return toBooksList(res.getBooks());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List findRecordsFrom(String informationType, Date date) {
		throw new Error("Not implemented");
	}

	public void insert(UserProfile profile) {
		throw new Error("Not implemented");
	}

	public void update(UserProfile profile) {
		throw new Error("Not implemented");
	}

	public UserProfile findUser(String username) {
		throw new Error("Not implemented");
	}

	public boolean userExists(String username) {
		throw new Error("Not implemented");
	}

	public void add(RegularData rd) {
		throw new Error("Not implemented");
	}

	public int getNumberOfRegularRecords(String informationType) {
		throw new Error("Not implemented");
	}

	public List getRawData() {
		throw new Error("Not implemented");
	}

	public RawData getRawData(int id) {
		throw new Error("Not implemented");
	}

	public void addRawData(RegularData rd) {
		throw new Error("Not implemented");
		
	}

	public void deleteRawData(RawData rd) {
		throw new Error("Not implemented");
		
	}

	public void updateRawData(RawData rd) {
		throw new Error("Not implemented");
		
	}

	public int getNumberOfRawRecords() {
		throw new Error("Not implemented");
	}
}
