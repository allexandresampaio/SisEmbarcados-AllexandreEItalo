package br.edu.ifba.embarcados.sensorquedaapp.asyncexec;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.embarcados.sensorquedaapp.conector.FabricaConectores;
import br.edu.ifba.embarcados.sensorquedaapp.conector.IComunicacaoSensores;

public class AsyncExec implements Runnable{

	private String porta;
	private boolean continuar;
	private List<IListenerSensores> listeners;
	
	public void addListener(IListenerSensores listener){
		listeners.add(listener);
	}
	
	public AsyncExec(String porta){
		this.porta = porta;
		listeners = new ArrayList<IListenerSensores>();
	}
	
	
	public void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}
	
	private void notificar(short altitude, short queda){
		for (IListenerSensores listener : listeners){
			listener.notificarAltitude(altitude);
			listener.notificarQueda(queda);
		}
	}

	@Override
	public void run() {
			
		IComunicacaoSensores conector = FabricaConectores.getConector();
		if (conector.iniciar(porta) == 0){
			this.continuar = true;
			while (continuar){
				conector.ler();
				notificar(conector.getAltitude(), conector.getQueda());
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			conector.finalizar();
		}
		
	}
	

}
