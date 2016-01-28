package br.edu.ifba.se.cliente;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.MeterGaugeChartModel;
import br.edu.ifba.se.cliente.conector.Conector;

@ManagedBean(name = "monitor")
public class Monitor {
	
	int temperatura, umidade;
	boolean vibracao, presenca;
	Conector conector = new Conector();

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
		presenca = false;
	}

	public void lerSensores() {
		temperatura = conector.getTemperatura();
		umidade = conector.getUmidade();
		vibracao = getVibracaoDetectada();
		presenca = getPresencaDetectada();

		System.out.println("Temperatura = " + temperatura);
		System.out.println("Umidade = " + umidade);
		System.out.println("Vibração = " + vibracao);
		System.out.println("Presença = " + presenca);

		modeloMedidorTemperatura.setValue(temperatura);
		modeloMedidorUmidade.setValue(umidade);
		
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
		return (conector.getVibracao() == 1);
	}

	public boolean getPresencaDetectada() {
		return (conector.getPresenca() == 1);
	}

	public String getAcaoTemperatura() {
		String retorno = "";
		if (temperatura < 14 || temperatura > 18){
			retorno = "Temperatura fora dos padrões. Verificar condições da adega.";
			criarAviso(retorno);
		}
		return retorno;
	}

	public String getAcaoUmidade() {
		String retorno = "";
		if (umidade < 70 || umidade > 75){
			retorno = "Umidade fora dos padrões. Verificar condições da adega.";
			criarAviso(retorno);
		}
		return retorno;
	}

	public String getAcaoVibracao() {
		String retorno="";
		if (vibracao == true && presenca == false){
			retorno = "Vibração detectada. Verificar condições da adega.";
			criarAlerta(retorno);
		} 
		return retorno;
	}
	
	public String getAcaoPresenca() {
		String retorno="";
		if (presenca == true && !ControladorPresenca.isPresencaAnterior()){
			retorno = "Presença detectada. Ligar luzes e oxigenar adega.";
			criarAlerta(retorno);
		} 
		
		else if (presenca == false && ControladorPresenca.isPresencaAnterior()){
			retorno = "Não há presença. Desligar luzes e desoxigenar adega.";
			criarAlerta(retorno);
		}
		ControladorPresenca.setPresencaAnterior(presenca);
		return retorno;
	}
	
	public void criarAlerta(String mensagem){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "WineKeep",  "Atenção: " + mensagem));
	}
	
	public void criarAviso(String aviso){
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", aviso));
	}

}
