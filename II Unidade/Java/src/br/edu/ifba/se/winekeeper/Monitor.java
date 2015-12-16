package br.edu.ifba.se.winekeeper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.MeterGaugeChartModel;

import br.edu.ifba.se.winekeeper.conector.SingleConector;

@ManagedBean(name = "monitor")
public class Monitor {

	private MeterGaugeChartModel modeloMedidorTemperatura;
	private MeterGaugeChartModel modeloMedidorUmidade;
	
	public MeterGaugeChartModel getModeloMedidorTemperatura() {
		return modeloMedidorTemperatura;
	}

	public MeterGaugeChartModel getModeloMedidorUmidade() {
		return modeloMedidorUmidade;
	}

	@PostConstruct
	public void iniciar(){
		configurarMedidores();
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

	public void lerSensores() {
		// acionar a leitura do arduino
		SingleConector.getConector().ler();

		int temperatura = SingleConector.getConector().getTemperatura();
		int umidade = SingleConector.getConector().getUmidade();

		System.out.println("Temperatura = " + temperatura);
		System.out.println("Umidade = " + umidade);
		System.out.println("Vibração = " + getVibracaoDetectada());
		System.out.println("Presença = " + getPresencaDetectada());

		modeloMedidorTemperatura.setValue(temperatura);
		modeloMedidorUmidade.setValue(umidade);
	}
	
	public boolean getVibracaoDetectada(){
		//se tiver movimento ele retorna 1
		return (SingleConector.getConector().getVibracao() == 1);
	}
	
	public boolean getPresencaDetectada(){
		//se tiver presenca ele retorna 1
		return (SingleConector.getConector().getPresenca() == 1);
	}

}
