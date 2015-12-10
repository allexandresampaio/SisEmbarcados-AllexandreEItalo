<<<<<<< HEAD:I Unidade/Java/src/br/edu/ifba/embarcados/quedaapp/Executor.java
package br.edu.ifba.embarcados.quedaapp;

import br.edu.ifba.embarcados.quedaapp.asyncexec.AsyncExec;

public class Executor {
	
	public static void main(String[] args) throws InterruptedException {
		
		AsyncExec async = new AsyncExec("COM5");
		
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
=======
package br.edu.ifba.embarcados.quedaapp;

import br.edu.ifba.embarcados.quedaapp.asyncexec.AsyncExec;

public class Executor {
	
	public static void main(String[] args) throws InterruptedException {
		
		AsyncExec async = new AsyncExec("COM5");
		
		ListenerSensores listener = new ListenerSensores();
		
		async.addListener(listener);
		
		Thread t = new Thread(async);
		t.start();
		
		// FIXME nao estah sendo utilizado o mecanismo de interrupcao da thread (o tjoin nao tem efeito)
		/*for (int i = 0; i < 10; i++){
			Thread.sleep(1000);
		}
		
		async.setContinuar(false);*/
		
		t.join();
	}

}
>>>>>>> origin/master:Java/src/br/edu/ifba/embarcados/quedaapp/Executor.java
