package br.edu.ifba.embarcados.webcoisas;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.ifba.embarcados.webcoisas.sensoriamento.LeitorSensoriamento;

@Path("servico")
public class Servico {
	
	@GET
	@Path("sensores")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSensores() {
		return LeitorSensoriamento.getSensores().
				toString();
	}

}
