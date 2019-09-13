package co.d1egoprog.example.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestSingle {
	
	public static final String SERVER = "localhost";
	public static final int PORT = 27017;
	public static final String DATABASE = "tests";
	public static final String COLLECTION = "insertSingle";

	public TestSingle() {
		try {
			MongoClient conection = MongoClients.create("mongodb://"+ SERVER + ":" + PORT);
			MongoDatabase database = conection.getDatabase(DATABASE);
			MongoCollection<Document> collection = database.getCollection(COLLECTION);
			
			Document manzana = new Document("Nombre", "Manzana").append("Cantidad", 10);
			collection.insertOne(manzana);

			Document pera = new Document("Nombre", "Pera").append("Cantidad", 20);
			collection.insertOne(pera);
			
			List<Document> list = new ArrayList<Document>();
			list.add(manzana);
			list.add(pera);
			
			Document frutas = new Document("Frutas", list);
			collection.insertOne(frutas);
						
			conection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void main(String args[]) {
		new TestSingle();
	}

}
