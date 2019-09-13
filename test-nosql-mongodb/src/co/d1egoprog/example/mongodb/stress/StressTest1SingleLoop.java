package co.d1egoprog.example.mongodb.stress;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class StressTest1SingleLoop {
	
	public static final String SERVER = "localhost";
	public static final int PORT = 27017;
	public static final String DATABASE = "tests";
	public static final String COLLECTION = "insertSingleLoop";

	public StressTest1SingleLoop() {
		try {
			MongoClient conection = MongoClients.create("mongodb://"+ SERVER + ":" + PORT);
			MongoDatabase database = conection.getDatabase(DATABASE);
			MongoCollection<Document> collection = database.getCollection(COLLECTION);
			for(int i=0;i<=Long.MAX_VALUE;i++){
				Document manzana = new Document("Nombre", "Manzana").append("Cantidad", 10);
				collection.insertOne(manzana);
			}
			conection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void main(String args[]) {
		new StressTest1SingleLoop();
	}

}
