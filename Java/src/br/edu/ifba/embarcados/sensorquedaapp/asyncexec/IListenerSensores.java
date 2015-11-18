package br.edu.ifba.embarcados.sensorquedaapp.asyncexec;

public interface IListenerSensores {
	
	public void notificarAltitude(short altitude);
	public void notificarQueda(short queda);
	

}
