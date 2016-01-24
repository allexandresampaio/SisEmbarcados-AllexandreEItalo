package br.edu.ifba.embarcados.winekeeper.sensoriamento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeitorSensoriamento implements Runnable {

	private static final int DESLOCAMENTO_RFID  = 17;
	private static final int DESLOCAMENTO_MOVMT = 16;
	private static final int DESLOCAMENTO_BATMT = 8;
	
	// referencia/acesso estatico a sensores
	private static Integer sensores = 0;

	public static int getSensores() {
		synchronized (sensores) {
			return sensores;
		}
	}
	
	public static int getRFID() {
		int id = getSensores();
		
		id = id >> DESLOCAMENTO_RFID;
		
		return id;
	}
	
	public static int getMovimento() {
		int mov = getSensores();
		
		mov = (mov & 65536) >> DESLOCAMENTO_MOVMT;
		
		return mov;
	}
	
	public static int getBatimentos() {
		int bat = getSensores();
		
		bat = (bat & 65280) >> DESLOCAMENTO_BATMT;
		
		return bat;
	}
	
	public static int getTemperatura() {
		int temp = getSensores();
		
		temp = (temp & 255);
		
		return temp;
	}

	// acesso a arquivo PIPE de sensoriamento
	private static final String ARQUIVO_PIPE = "/home/allexandre/arquivos_initd/winekeeper_p";
	private RandomAccessFile fifo = null;

	private boolean continuar = true;

	public LeitorSensoriamento() {
		try {
			fifo = new RandomAccessFile(ARQUIVO_PIPE, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		continuar = true;
		while (continuar) {
			String s = "";
			try {
				if (((s = fifo.readLine()) != null) && !s.equals("")) {
					synchronized (sensores) {
						sensores = Integer.parseInt(s);
					}

					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			fifo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parar() {
		continuar = false;
	}
	
}