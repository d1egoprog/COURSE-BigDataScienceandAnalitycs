package co.d1egoprog.example.mongodb.stress;

import java.util.Random;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class StressTestManyThread extends Thread{
	
	private int id;
	public static final String SERVER = "localhost";
	public static final int PORT = 27017;
	public static final String DATABASE = "tests";
	public static final String COLLECTION = "insertManyParalell";
	
	public StressTestManyThread(int id) {
		this.id = id;
	}
	
	public void run(){
		System.out.println("Hilo "+id + " Activado!");
		MongoClient conection = MongoClients.create("mongodb://"+ SERVER + ":" + PORT);
		MongoDatabase database = conection.getDatabase(DATABASE);
		MongoCollection<Document> collection = database.getCollection(COLLECTION);
		Random rn = new Random();
		try{
			for(int i=0;i<=Integer.MAX_VALUE;i++){
				char[] palabra = new char[rn.nextInt(8)+3]; 
		        for(int j = 0; j < palabra.length; j++){
		        	palabra[j] = (char)('a' + rn.nextInt(26));
		        }
				
		        Document documento = new Document("palabra", palabra.toString());
				documento.append("cantidad", rn.nextInt());
				documento.append("Hilo", id);
				
				collection.insertOne(documento);
			}
			conection.close();
		}catch(Exception e){
			System.out.println("Hilo "+id + " Fallo en Algo");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Hilo "+id + " Finalizado!");
	}
}
