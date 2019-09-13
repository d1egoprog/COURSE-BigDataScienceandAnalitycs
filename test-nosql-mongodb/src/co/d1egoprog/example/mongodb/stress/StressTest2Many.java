package co.d1egoprog.example.mongodb.stress;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class StressTest2Many {
	
	public static final String SERVER = "localhost";
	public static final int PORT = 27017;
	public static final String DATABASE = "tests";
	public static final String COLLECTION = "insertMany";

	public StressTest2Many() {
		try {
			MongoClient conection = MongoClients.create();
			MongoDatabase database = conection.getDatabase(DATABASE);
			MongoCollection<Document> collection = database.getCollection(COLLECTION);
			
			List<Document> list = new ArrayList<Document>();
			for(int i=0;i<10000;i++){
				Document manzana = new Document("Nombre", "Manzana").append("Cantidad", 10);
				list.add(manzana);
			}
			collection.insertMany(list);
			System.out.println("Insertado....");
			conection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void main(String args[]) {
		new StressTest2Many();
	}

}
