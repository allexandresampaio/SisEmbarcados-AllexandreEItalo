package br.edu.ifba.embarcados.quedaapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifba.embarcados.quedaapp.asyncexec.IListenerSensores;

public class ListenerSensores implements IListenerSensores{
	
	private static final Logger logger = LogManager.getLogger("ListenerSensores");//cria um logger

	@Override
	public void notificarAltitude(short altitude) {
		logger.trace("Altitude: "+altitude);
		//System.out.println("Altitude: "+altitude);		
	}

	@Override
	public void notificarQueda(short queda) {
		if(queda==1){
			logger.trace("Queda: "+queda);
			//System.out.println("Caindo");
		} else {
			logger.trace("Queda: "+queda);
			//System.out.println("Estabilizado");
		}
	}

	
}
