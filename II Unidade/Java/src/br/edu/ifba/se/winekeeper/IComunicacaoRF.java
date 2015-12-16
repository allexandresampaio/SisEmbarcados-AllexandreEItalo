package br.edu.ifba.se.winekeeper;

import com.sun.jna.Library;

public interface IComunicacaoRF extends Library{

	public int iniciar(String porta);

	public int ler();

	public int getId();

	public int getUmidade();

	public int getTemperatura();

	public int getVibracao();
	
	public int getPresenca();

	public int finalizar();
	
}
