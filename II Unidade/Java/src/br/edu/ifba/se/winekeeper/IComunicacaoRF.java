package br.edu.ifba.se.winekeeper;

import com.sun.jna.Library;

public interface IComunicacaoRF extends Library {

	public int iniciar(String porta);

	public int ler();

	public int getId();

	public int getBatimentos();

	public int getTemperatura();

	public int getMovimentos();

	public int finalizar();

}
