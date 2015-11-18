package br.edu.ifba.embarcados.sensorquedaapp;

import br.edu.ifba.embarcados.sensorquedaapp.asyncexec.IListenerSensores;

public class ListenerSensores implements IListenerSensores{

	@Override
	public void notificarAltitude(short altitude) {
		System.out.println("Altitude: "+altitude);		
	}

	@Override
	public void notificarQueda(short queda) {
		if(queda==1){
			System.out.println("Caindo");
		} else {
			System.out.println("Estabilizado");
		}
	}

	
}
