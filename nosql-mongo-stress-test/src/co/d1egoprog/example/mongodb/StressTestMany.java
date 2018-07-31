package co.d1egoprog.example.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class StressTestMany {

	public StressTestMany() {
		try {
			MongoClient conexion = new MongoClient("localhost", 27017);
			MongoDatabase base = conexion.getDatabase("pruebaStress");
			for(int i=0;i<=Long.MAX_VALUE;i++){
				Document documento = new Document("fruta", "Manzana").append("cantidad", 10);
				base.getCollection("muchos").insertOne(documento);
			}
			conexion.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void main(String args[]) {
		new StressTestMany();
	}

}
