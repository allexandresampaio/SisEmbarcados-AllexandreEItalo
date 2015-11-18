package br.edu.ifba.embarcados.sensorquedaapp;

import br.edu.ifba.embarcados.sensorquedaapp.asyncexec.AsyncExec;

public class Executor {

	public static void main(String[] args) throws InterruptedException {
		AsyncExec async = new AsyncExec("COM3");
		
		ListenerSensores listener = new ListenerSensores();
		
		async.addListener(listener);
		
		Thread t = new Thread(async);
		t.start();
		
		/*for (int i = 0; i < 10; i++){
			Thread.sleep(1000);
		}
		
		async.setContinuar(false);*/
		
		t.join();
	}

}
