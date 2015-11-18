package br.edu.ifba.embarcados.quedaapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import br.edu.ifba.embarcados.quedaapp.asyncexec.IListenerSensores;

public class ListenerSensores implements IListenerSensores {

	private static final Logger logger = LogManager
			.getLogger("ListenerSensores");// cria um logger
	private short altitudeI;
	private short altitudeF;
	private short altitude;
	private short a = 0;

	@Override
	public void notificarAltitude(short altitude) {
		this.altitude = altitude;
		logger.info("Altitude: " + altitude);
		// System.out.println("Altitude: "+altitude);
	}

	@Override
	public void notificarQueda(short queda) {
		if (queda != 1) {
			logger.info("Estabilizado.");
			// System.out.println("Estabilizado");
			this.altitudeI = this.altitude;
		} else {
			logger.warn("Caindo!");
			// System.out.println("Caindo");
			this.altitudeF = this.altitude;
			if (this.a == this.altitude) {
				logger.info("QUEDA DETECTADA. ALTITUDE INICIAL: "
						+ this.altitudeI + ". ALTITUDE FINAL: "+ this.altitudeF+".");
				this.altitudeI = this.altitude;
			} else {
				this.a = this.altitude;
			}
		}
	}

}
