package co.d1egoprog.example.mongodb;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class StressTestSingle {

	public StressTestSingle() {
		try {
			MongoClient conexion = new MongoClient("localhost", 27017);
			MongoDatabase base = conexion.getDatabase("pruebaStress");
			Document documento = new Document("fruta", "Manzana").append("cantidad", 10);
			base.getCollection("unitario").insertOne(documento);
			System.out.println("Documento Insertado Correctamente");
			conexion.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static void main(String args[]) {
		new StressTestSingle();
	}

}
