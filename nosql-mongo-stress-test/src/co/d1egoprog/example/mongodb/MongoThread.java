package co.d1egoprog.example.mongodb;
import java.util.Random;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoThread extends Thread{
	
	private int id;
	
	public MongoThread(int id) {
		this.id = id;
	}
	
	public void run(){
		System.out.println("Hilo "+id + " Activado!");
		Random rn = new Random();
		try{
				MongoClient conexion = new MongoClient("localhost", 27017);
				MongoDatabase base = conexion.getDatabase("pruebaStress");
				MongoCollection collection = base.getCollection("muchos");
				
				for(int i=0;i<=Long.MAX_VALUE;i++){
					char[] palabra = new char[rn.nextInt(8)+3]; 
			        for(int j = 0; j < palabra.length; j++){
			        	palabra[j] = (char)('a' + rn.nextInt(26));
			        }
					Document documento = new Document("palabra", palabra.toString()).append("cantidad", rn.nextInt());
					collection.insertOne(documento);
				}
				conexion.close();
		}catch(Exception e){
			System.out.println("Hilo "+id + " Fallo en Algo");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Hilo "+id + " Finalizado!");
	}
}
