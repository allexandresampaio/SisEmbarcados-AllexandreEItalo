package br.edu.ifba.embarcados.winekeeper.sensoriamento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeitorSensoriamento implements 
	Runnable {

	private static final String ARQUIVO_PIPE = 
			"/home/allexandre/arquivos_initd/winekeeper_p";
	
	private RandomAccessFile pipe = null;
	private boolean continuar = true;
	
	private static Integer sensores = 0;

	@Override
	public void run() {
		try {
			pipe = new RandomAccessFile(ARQUIVO_PIPE,
					"r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			return;
		}
		
		continuar = true;
		while (continuar) {
			try {
				String s = pipe.readLine();
				
				if ((s != null) && (!s.equals(""))) {
					System.out.println("Sensores lidos: " + s);
					
					
					synchronized(sensores) {
						sensores = Integer.parseInt(s);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void parar() {
		continuar = false;
	}
	
	public static Integer getSensores() {
		synchronized (sensores) {
			return sensores;
		}
	}
}
