package softarch.portal.db.json;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import softarch.portal.db.sql.DatabaseException;


public class Database {
	protected String dbUrl;

	/**
	 * Creates a new database.
	 */
	public Database(String dbUrl) {
		this.dbUrl	= dbUrl;
	}
	
	public FileWriter writeConnection() throws IOException, DatabaseException {
		FileWriter file;
		try {
			file = new FileWriter(this.dbUrl);
		} catch (IOException e) {
			throw new DatabaseException(
					"Unable to open a write connection!");
		}
		return file;
	}
	
	public FileReader readConnection() throws IOException, DatabaseException {
		FileReader file;
		try {
			file = new FileReader(this.dbUrl);
		} catch (IOException e) {
			throw new DatabaseException(
					"Unable to open a read connection!");
		}
		return file;
	}
	
	public void writeObject(JSONObject obj) throws IOException, DatabaseException {
		FileWriter file = this.writeConnection();
		file.write(obj.toJSONString());
		file.flush();
		file.close();
	}
	
	public void writeArray(JSONArray arr) throws IOException, DatabaseException {
		FileWriter file = this.writeConnection();
		file.write(arr.toJSONString());
		file.flush();
		file.close();
	}
	
	public JSONObject readObject() throws IOException, DatabaseException {
		FileReader file = this.readConnection();
		JSONParser parser = new JSONParser();
		JSONObject obj;
		try {
			obj = (JSONObject) parser.parse(file);
		} catch (ParseException e) {
			throw new DatabaseException(
					"Could not parse file!");
		}
		return obj;
	}
	
	public JSONArray readArray() throws IOException, DatabaseException {
		FileReader file = this.readConnection();
		JSONParser parser = new JSONParser();
		JSONArray arr;
		try {
			arr = (JSONArray) parser.parse(file);
		} catch (ParseException e) {
			throw new DatabaseException(
					"Could not parse file!");
		}
		return arr;
	}

}