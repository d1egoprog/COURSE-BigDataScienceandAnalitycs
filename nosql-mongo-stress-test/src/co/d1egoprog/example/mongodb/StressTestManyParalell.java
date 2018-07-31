package co.d1egoprog.example.mongodb;

import java.util.ArrayList;
import java.util.List;

public class StressTestManyParalell extends Thread {
	
	private List<MongoThread> hilos;

	public StressTestManyParalell(int numero) {
		hilos = new ArrayList<MongoThread>();
		for (int i=0;i<numero;i++){
			MongoThread hilo = new MongoThread(i);
			hilos.add(hilo);
			hilo.start();
		}
	}

	public static void main(String args[]) {
		new StressTestManyParalell(8);
	}
	
}
