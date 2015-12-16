package br.edu.ifba.se.winekeeper.conector;

import br.edu.ifba.se.winekeeper.IComunicacaoRF;

public class SingleConector {

	static final String PORTA = "COM3";
	
	private static IComunicacaoRF comRF = null;
	
	public static void iniciarComunicacaoRF(String libPath){
		comRF = FabricaConectores.getConector(libPath);
		if (comRF.iniciar(PORTA)==0){//0 em caso de sucesso
			System.out.println("Acesso a sensores iniciado com sucesso.");
			dispensarPrimeirasLeituras();
		}
		else {
			System.out.println("Não foi possível iniciar o acesso aos sensores.");
		}
	}
	
	public static void dispensarPrimeirasLeituras(){
		for (int i=0; i<10; i++){
			comRF.ler();
		}
		
		System.out.println("Dispensando leitura [U/T/P/V]:" + 
				comRF.getUmidade() + "/" +
				comRF.getTemperatura() + "/" +
				comRF.getPresenca() + "/" +
				comRF.getVibracao());
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static IComunicacaoRF getConector(){
		return comRF;
	}
}
