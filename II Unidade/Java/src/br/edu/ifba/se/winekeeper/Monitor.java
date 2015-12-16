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
	private MeterGaugeChartModel modeloMedidorBatimentos;
	
	public MeterGaugeChartModel getModeloMedidorTemperatura() {
		return modeloMedidorTemperatura;
	}

	public MeterGaugeChartModel getModeloMedidorBatimentos() {
		return modeloMedidorBatimentos;
	}

	@PostConstruct
	public void iniciar(){
		configurarMedidores();
	}

	private void configurarMedidores() {
		modeloMedidorTemperatura = criarModeloTemperatura();
		modeloMedidorTemperatura.setTitle("Temperatura");
		modeloMedidorTemperatura.setGaugeLabel("Graus Celsius");
		
		modeloMedidorBatimentos = criarModeloBatimentos();
		modeloMedidorBatimentos.setTitle("Batimentos");
		modeloMedidorBatimentos.setGaugeLabel("BPM");
		
	}

	private MeterGaugeChartModel criarModeloTemperatura() {
		List<Number> marcadores = new ArrayList<Number>();
		marcadores.add(0); 
		marcadores.add(10);
		marcadores.add(20);
		marcadores.add(30);
		marcadores.add(40);
		marcadores.add(50);
		
		return new MeterGaugeChartModel(0, marcadores);
	}

	private MeterGaugeChartModel criarModeloBatimentos() {
		List<Number> marcadores = new ArrayList<Number>();
		marcadores.add(0); 
		marcadores.add(25);
		marcadores.add(50);
		marcadores.add(75);
		marcadores.add(100);
		marcadores.add(125);
		marcadores.add(150);
		marcadores.add(175);
		marcadores.add(200);
		
		return new MeterGaugeChartModel(0, marcadores);
	}

	public void lerSensores() {
		// acionar a leitura do arduino
		SingleConector.getConector().ler();

		int temperatura = SingleConector.getConector().getTemperatura();
		int batimentos = SingleConector.getConector().getBatimentos();

		System.out.println("Temp = " + temperatura);
		System.out.println("Bat = " + batimentos);
		System.out.println("Mov = " + getMovimentoDetectado());

		modeloMedidorTemperatura.setValue(temperatura);
		modeloMedidorBatimentos.setValue(batimentos);
	}
	
	public boolean getMovimentoDetectado(){
		//se tiver movimento ele retorna 1
		return (SingleConector.getConector().getMovimentos() == 1);
	}

}
