package co.d1egoprog.example.mongodb.stress;

import java.util.ArrayList;
import java.util.List;

public class StressTest3ManyParalell extends Thread {
	
	private List<StressTestManyThread> hilos;

	public StressTest3ManyParalell(int numero) {
		hilos = new ArrayList<StressTestManyThread>();
		for (int i=0;i<numero;i++){
			StressTestManyThread hilo = new StressTestManyThread(i);
			hilos.add(hilo);
			hilo.start();
		}
	}

	public static void main(String args[]) {
		new StressTest3ManyParalell(10);
	}
	
}
