package br.edu.ifba.se.winekeeper.conector;

import java.util.concurrent.Semaphore;

import br.edu.ifba.se.winekeeper.IComunicacaoRF;
import br.edu.ifba.se.winekeeper.Informacao;

public class SingleConector {
	
	private static final String PORTA = "/dev/ttyACM0";
	private static IComunicacaoRF comRF = null; //fica disponível enquanto a JVM estiver no ar
	private static Informacao info;
	private static Semaphore semaforo;
	
	public static void iniciarComunicacoRF(String libPath){
		info = new  Informacao();
		comRF = FabricaConectores.getConector(libPath);
		
		if (comRF.iniciar(PORTA)==0){
			System.out.println("Acesso a sensores iniciado com sucesso.");
			dispensarPrimeirasLeituras();
			semaforo = new Semaphore(1, true);
		}else
			System.out.println("Nao foi possível iniciar acesso a sensores.");
	}
	
	public static void dispensarPrimeirasLeituras(){
		for(int i=0; i<10; i++){
			comRF.ler();
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
	}
	
	public static Informacao getInformacao(){
		Informacao info_ = new Informacao();
		
		try {
			semaforo.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		info_.setUmidade(info.getUmidade());
		info_.setTemperatura(info.getTemperatura());
		info_.setVibracao(info.getVibracao());
		info_.setPresenca(info.getPresenca());
		semaforo.release();
		
		return info_;
	}
	
	public static int ler(){
		try {
			semaforo.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int resultado = comRF.ler();
		
		if(resultado == 0){
			info.setTemperatura(comRF.getTemperatura());
			info.setUmidade(comRF.getUmidade());
			info.setVibracao(comRF.getVibracao());
			info.setPresenca(comRF.getPresenca());
		}
		
		semaforo.release();
		
		return resultado;
	}
	
	public static void  finalizar(){
		comRF.finalizar();
	}
}
