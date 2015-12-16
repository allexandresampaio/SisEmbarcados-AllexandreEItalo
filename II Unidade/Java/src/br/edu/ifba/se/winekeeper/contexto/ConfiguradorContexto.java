package br.edu.ifba.se.winekeeper.contexto;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import br.edu.ifba.se.winekeeper.conector.SingleConector;

import com.sun.faces.config.ConfigureListener;

public class ConfiguradorContexto extends ConfigureListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		
		ServletContext sc = sce.getServletContext();
		String libPath = sc.getRealPath("WEB-INF/lib");
		SingleConector.iniciarComunicacaoRF(libPath);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		SingleConector.getConector().finalizar();
		
		super.contextDestroyed(sce);
	}

}
