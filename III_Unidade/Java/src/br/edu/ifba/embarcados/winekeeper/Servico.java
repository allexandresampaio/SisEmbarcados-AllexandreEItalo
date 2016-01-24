package br.edu.ifba.embarcados.winekeeper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.ifba.embarcados.winekeeper.sensoriamento.LeitorSensoriamento;

@Path("sw")
public class Servico {

	@GET
	@Path("/id/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getId() {
		return LeitorSensoriamento.getRFID() + "";
	}

	@GET
	@Path("/sensores")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSensores() {
		return LeitorSensoriamento.getSensores() + "";
	}

	@GET
	@Path("/sensores/movimento")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMovimento() {
		return LeitorSensoriamento.getMovimento() + "";
	}

	@GET
	@Path("/sensores/batimentos")
	@Produces(MediaType.TEXT_PLAIN)
	public String getBatimentos() {
		return LeitorSensoriamento.getBatimentos() + "";
	}

	@GET
	@Path("/sensores/temperatura")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTemperatura() {
		return LeitorSensoriamento.getTemperatura() + "";
	}
	
}