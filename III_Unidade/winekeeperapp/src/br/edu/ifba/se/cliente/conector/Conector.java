package br.edu.ifba.se.cliente.conector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class Conector {

	private static final String ENDERECO_WS = "http://localhost:8080/winekeeper/v1/sw/";

	public String acessar(String urlFuncao) {
		String resultado = "";

		@SuppressWarnings("resource")
		HttpClient cliente = new DefaultHttpClient();
		HttpGet get = new HttpGet(ENDERECO_WS + urlFuncao);
		HttpResponse resposta;
		try {
			resposta = cliente.execute(get);
			BufferedReader br = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent()));

			resultado = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultado;
	}

	public Integer getId() {
		Integer id = 0;

		String sid = acessar("id");
		if (sid != "") {
			id = Integer.parseInt(sid);
		}

		return id;
	}

	public Integer getPresenca() {
		Integer presenca = 0;

		String spresenca = acessar("sensores/presenca");
		if (spresenca != "") {
			presenca = Integer.parseInt(spresenca);
		}

		return presenca;
	}
	
	public Integer getVibracao() {
		Integer vibracao = 0;

		String svibracao = acessar("sensores/vibracao");
		if (svibracao != "") {
			vibracao = Integer.parseInt(svibracao);
		}

		return vibracao;
	}

	public Integer getTemperatura() {
		Integer temperatura = 0;

		String stemperatura = acessar("sensores/temperatura");
		if (stemperatura != "") {
			temperatura = Integer.parseInt(stemperatura);
		}

		return temperatura;
	}
	
	public Integer getUmidade() {
		Integer umidade = 0;

		String sumidade = acessar("sensores/umidade");
		if (sumidade != "") {
			umidade = Integer.parseInt(sumidade);
		}

		return umidade;
	}

}
