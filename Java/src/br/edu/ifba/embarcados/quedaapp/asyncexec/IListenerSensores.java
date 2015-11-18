package br.edu.ifba.embarcados.quedaapp.asyncexec;

public interface IListenerSensores {
	
	public void notificarAltitude(short altitude);
	public void notificarQueda(short queda);
	

}
