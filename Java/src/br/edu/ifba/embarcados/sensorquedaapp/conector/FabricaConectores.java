package br.edu.ifba.embarcados.sensorquedaapp.conector;

import com.sun.jna.Native;
import com.sun.jna.Platform;

public class FabricaConectores {

	public static IComunicacaoSensores getConector(){
		
		IComunicacaoSensores conector = null;
		if(Platform.isWindows()){
			conector = (IComunicacaoSensores) Native.loadLibrary(
					"sensorqueda.dll", IComunicacaoSensores.class);
		}else if (Platform.isLinux()){
			conector = (IComunicacaoSensores) Native.loadLibrary(
					"sensorqueda.so", IComunicacaoSensores.class);
		}
		
		return conector;
	}
}
