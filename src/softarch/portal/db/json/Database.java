package softarch.portal.db.json;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
	
	protected static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public FileWriter writeConnection() throws DatabaseException {
		FileWriter file;
		try {
			file = new FileWriter(this.dbUrl);
		} catch (IOException e) {
			throw new DatabaseException(
					"Unable to open a write connection!");
		}
		return file;
	}
	
	public FileReader readConnection() throws DatabaseException {
		FileReader file;
		try {
			file = new FileReader(this.dbUrl);
		} catch (IOException e) {
			throw new DatabaseException(
					"Unable to open a read connection!");
		}
		return file;
	}
	
	public void writeObject(JSONObject obj) throws DatabaseException {
		FileWriter file = this.writeConnection();
		try {
			file.write(obj.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			throw new DatabaseException(
					"Error in writing an object!");
		}
		
	}
	
	public void writeArray(JSONArray arr) throws DatabaseException {
		FileWriter file = this.writeConnection();
		try {
			file.write(arr.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			throw new DatabaseException(
					"Error in writing an array!");
		}
		
	}
	
	public JSONObject readObject() throws DatabaseException {
		FileReader file = this.readConnection();
		JSONParser parser = new JSONParser();
		JSONObject obj;
		try {
			obj = (JSONObject) parser.parse(file);
		} catch (ParseException e) {
			throw new DatabaseException(
					"Could not parse file!");
		} catch (IOException e) {
			throw new DatabaseException(
					"Unable to open a read connection!");
		}
		return obj;
	}
	
	public JSONArray readArray() throws DatabaseException {
		FileReader file = this.readConnection();
		JSONParser parser = new JSONParser();
		JSONArray arr;
		try {
			arr = (JSONArray) parser.parse(file);
		} catch (ParseException e) {
			throw new DatabaseException(
					"Could not parse file!");
		} catch (IOException e) {
			throw new DatabaseException(
					"Unable to open a read connection!");
		}
		return arr;
	}
	
	

}