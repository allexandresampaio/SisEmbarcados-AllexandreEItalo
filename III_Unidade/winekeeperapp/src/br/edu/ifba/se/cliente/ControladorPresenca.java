package br.edu.ifba.se.cliente;

public class ControladorPresenca {
	private static ControladorPresenca instance = null;
	   protected ControladorPresenca() {
	      // Exists only to defeat instantiation.
	   }
	   public static ControladorPresenca getInstance() {
	      if(instance == null) {
	         instance = new ControladorPresenca();
	      }
	      return instance;
	   }
	   
	private static boolean presencaAnterior;
	   
	public static void setPresencaAnterior(boolean presenca) {
			presencaAnterior = presenca;
	}
	
	public static boolean isPresencaAnterior() {
		return presencaAnterior;
	}
}
