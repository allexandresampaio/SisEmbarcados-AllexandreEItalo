package br.edu.ifba.se.winekeeper;

public class Informacao {
	
	private int umidade;
	private int vibracao;
	private int temperatura;
	private int presenca;
	
	
	public int getUmidade() {
		return umidade;
	}
	public int getVibracao() {
		return vibracao;
	}
	public int getTemperatura() {
		return temperatura;
	}
	public void setUmidade(int umidade) {
		this.umidade = umidade;
	}
	public void setVibracao(int vibracao) {
		this.vibracao = vibracao;
	}
	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}
	public int getPresenca() {
		return presenca;
	}
	public void setPresenca(int presenca) {
		this.presenca = presenca;
	}

}
