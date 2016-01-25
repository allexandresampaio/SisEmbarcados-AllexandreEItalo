package br.edu.ifba.embarcados.winekeeper.sensoriamento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeitorSensoriamento implements Runnable {

	private static final int DESLOCAMENTO_RFID  = 18;
	private static final int DESLOCAMENTO_PRESENCA = 17;
	private static final int DESLOCAMENTO_VIBRACAO = 16;
	private static final int DESLOCAMENTO_TEMPERATURA = 8;
	
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
	
	public static int getPresenca() {
		int pre = getSensores();
		
		pre = (pre & 131072) >> DESLOCAMENTO_PRESENCA;
		
		return pre;
	}
	
	public static int getVibracao() {
		int vib = getSensores();
		
		vib = (vib & 65536) >> DESLOCAMENTO_VIBRACAO;
		
		return vib;
	}
	
	public static int getTemperatura() {
		int temp = getSensores();
		
		temp = (temp & 65280);
		
		return temp;
	}

	public static int getUmidade() {
		int umi = getSensores();
		
		umi = (umi & 255);
		
		return umi;
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
