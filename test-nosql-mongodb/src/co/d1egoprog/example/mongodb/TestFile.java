package co.d1egoprog.example.mongodb;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import co.d1egoprog.utilities.TextFileUtilities;

public class TestFile {

	public static final String SERVER = "localhost";
	public static final int PORT = 27017;
	public static final String DATABASE = "tests";
	public static final String COLLECTION = "insertFile";

	public TestFile() {
		try {
			MongoClient conection = MongoClients.create("mongodb://"+ SERVER + ":" + PORT);
			MongoDatabase database = conection.getDatabase(DATABASE);
			MongoCollection<Document> collection = database.getCollection(COLLECTION);
			String json = TextFileUtilities.readFileInUTF8("c:\\temp", "test.json");
			Document doc = Document.parse(json);
			collection.insertOne(doc);
			conection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void main(String args[]) {
		new TestFile();
	}

}
