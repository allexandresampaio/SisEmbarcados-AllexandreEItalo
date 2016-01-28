package br.edu.ifba.embarcados.clientewinekeeper_;

import br.edu.ifba.embarcados.clientewinekeeper_.conector.Conector;

public class Executor {

	public static void main(String[] args) throws InterruptedException {
		Conector conector = new Conector();

		while (true) {
			Integer id = conector.getId();
			System.out.println("id = " + id);
			
			System.out.println("Sensores...");
			
			Integer presenca = conector.getPresenca();
			System.out.println("Presença: " + presenca);
			Integer vibracao = conector.getVibracao();
			System.out.println("Vibração: " + vibracao);
			Integer temperatura = conector.getTemperatura();
			System.out.println("Temperatura: " + temperatura + "ºC.");
			Integer umidade = conector.getUmidade();
			System.out.println("Umidade: " + umidade + "%");
			
			System.out.println("Final de leitura!");
			System.out.println("...");
			
			
			Thread.sleep(3000);
		}
	}

}
