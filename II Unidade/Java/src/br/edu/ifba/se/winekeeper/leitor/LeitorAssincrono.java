package br.edu.ifba.se.winekeeper.leitor;

import br.edu.ifba.se.winekeeper.conector.SingleConector;

public class LeitorAssincrono implements Runnable{

	private boolean continuar;
	
	
	public LeitorAssincrono() {
	}
	
	@Override
	public void run() {
		continuar = true;
		
		while(continuar){
			SingleConector.ler();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void parar(){
		continuar = false;
	}

}
