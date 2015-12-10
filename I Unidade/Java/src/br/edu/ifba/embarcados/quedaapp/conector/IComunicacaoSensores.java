package br.edu.ifba.embarcados.quedaapp.conector;

import com.sun.jna.Library;

public interface IComunicacaoSensores extends Library {

	public int iniciar(String porta);
	public int ler();
	public short getAltitude();
	public short getQueda();
	public int finalizar();
}
