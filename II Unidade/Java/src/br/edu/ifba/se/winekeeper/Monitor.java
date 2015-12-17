package br.edu.ifba.se.winekeeper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.MeterGaugeChartModel;

import br.edu.ifba.se.winekeeper.conector.SingleConector;

@ManagedBean(name = "monitor")
public class Monitor {
	
	int temperatura, umidade;
	boolean vibracao, presenca, presencaAnterior=false;

	private MeterGaugeChartModel modeloMedidorTemperatura;
	
	private MeterGaugeChartModel modeloMedidorUmidade;

	public MeterGaugeChartModel getModeloMedidorTemperatura() {
		return modeloMedidorTemperatura;
	}

	public MeterGaugeChartModel getModeloMedidorUmidade() {
		return modeloMedidorUmidade;
	}

	public void setModeloMedidorTemperatura(
			MeterGaugeChartModel modeloMedidorTemperatura) {
		this.modeloMedidorTemperatura = modeloMedidorTemperatura;
	}

	public void setModeloMedidorUmidade(
			MeterGaugeChartModel modeloMedidorUmidade) {
		this.modeloMedidorUmidade = modeloMedidorUmidade;
	}

	@PostConstruct
	public void iniciar() {
		configurarMedidores();
	}

	public void lerSensores() {
		// acionar a leitura do arduino
		Informacao info = SingleConector.getInformacao();

		temperatura = info.getTemperatura();
		umidade = info.getUmidade();
		vibracao = getVibracaoDetectada();
		presenca = getPresencaDetectada();

		// int temperatura = (int) (Math.random()*50);
		// int umidade = (int) (Math.random()*100);

		System.out.println("Temperatura = " + temperatura);
		System.out.println("Umidade = " + umidade);
		System.out.println("Vibra��o = " + vibracao);
		System.out.println("Presen�a = " + presenca);

		modeloMedidorTemperatura.setValue(temperatura);
		modeloMedidorUmidade.setValue(umidade);
		// atualizar os valores nos medidores

	}

	private void configurarMedidores() {
		modeloMedidorTemperatura = criarModeloTemperatura();
		modeloMedidorTemperatura.setTitle("Temperatura");
		modeloMedidorTemperatura.setGaugeLabel("Graus Celsius");

		modeloMedidorUmidade = criarModeloUmidade();
		modeloMedidorUmidade.setTitle("Umidade");
		modeloMedidorUmidade.setGaugeLabel("Umidade %");
	}

	private MeterGaugeChartModel criarModeloTemperatura() {
		List<Number> marcadores = new ArrayList<Number>();
		marcadores.add(0);
		marcadores.add(5);
		marcadores.add(10);
		marcadores.add(15);
		marcadores.add(20);
		marcadores.add(25);
		marcadores.add(30);
		marcadores.add(35);
		marcadores.add(40);
		marcadores.add(45);
		marcadores.add(50);

		return new MeterGaugeChartModel(0, marcadores);
	}

	private MeterGaugeChartModel criarModeloUmidade() {
		List<Number> marcadores = new ArrayList<Number>();
		marcadores.add(0);
		marcadores.add(10);
		marcadores.add(20);
		marcadores.add(30);
		marcadores.add(40);
		marcadores.add(50);
		marcadores.add(60);
		marcadores.add(70);
		marcadores.add(80);
		marcadores.add(90);
		marcadores.add(100);

		return new MeterGaugeChartModel(0, marcadores);
	}

	public boolean getVibracaoDetectada() {
		return (SingleConector.getInformacao().getVibracao() == 1);

		// return ((Math.random()*10) <= 5);
	}

	public boolean getPresencaDetectada() {
		return (SingleConector.getInformacao().getPresenca() == 1);

		// return ((Math.random()*10) <= 5);
	}

	public String getAcaoTemperatura() {
		if (temperatura < 15 || temperatura > 22){
			return "Temperatura fora dos padr�es. Verificar condi��es da adega.";
		}
		else return "";
	}

	public String getAcaoUmidade() {
		if (umidade < 5 || umidade > 20){
			return "Umidade fora dos padr�es. Verificar condi��es da adega.";
		}
		else return "";
	}

	public String getAcaoVibracao() {
		if (vibracao == true && presenca == false){
			return "Vibra��o detectada. Verificar condi��es da adega.";
		} 
		else return "";
	}
	
	public String getAcaoPresenca() {
		String retorno="";
		
		if (presenca == true && presencaAnterior == false){
			retorno = "Presen�a detectada. Ligar luzes e oxigenar adega.";
		} 
		
		else if (presenca == false && presencaAnterior == true){
			retorno = "N�o h� presen�a. Desligar luzes e desoxigenar adega.";
		}
		presencaAnterior = presenca;
		return retorno;
	}

}
