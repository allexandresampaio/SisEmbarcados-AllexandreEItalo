package br.edu.ifba.se.winekeeper.contexto;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.sun.faces.config.ConfigureListener;

import br.edu.ifba.se.winekeeper.conector.SingleConector;
import br.edu.ifba.se.winekeeper.leitor.LeitorAssincrono;

public class ConfiguradorContexto extends ConfigureListener{

	private LeitorAssincrono leitor;
	private Thread tLeitor;
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		ServletContext sc = sce.getServletContext();
		String libPath = sc.getRealPath("/WEB-INF/lib");
		
		SingleConector.iniciarComunicacoRF(libPath);
		
		leitor = new LeitorAssincrono();
		tLeitor = new Thread(leitor);
		tLeitor.start();

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		leitor.parar();
		try {
			tLeitor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SingleConector.finalizar();
		
		super.contextDestroyed(sce);
	}

}
