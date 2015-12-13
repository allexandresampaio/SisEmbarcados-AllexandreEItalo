#include "RCSwitch.h"
#include "Wire.h"
#include "ADXL345.h"

//Instancia o emissor e o receptor
RCSwitch emissor = RCSwitch();
RCSwitch receptor = RCSwitch();

//Instancia o acelerometro
ADXL345 acel = ADXL345();

//Define os pinos dos sensores
#define P_UMIDADE 0
#define P_TEMPERATURA 1
#define P_PRESENCA 3

#define RFID 1298

//Define os valores que serao usados no deslocamento

#define DESLOC_RFID 18
#define DESLOC_PRESENCA 17
#define DESLOC_VIBRACAO 16
#define DESLOC_TEMPERATURA 8

struct info{
  int id;
  int umidade;
  int temperatura;
  boolean vibracao;
  boolean presenca;
} infoRF ;

void setup() {
  Serial.begin(9600);
  //Configuracao do emissor/Receptor RF
  emissor.enableTransmit(4);
  receptor.enableReceive(0);//Pega o primeiro pino de interrupcao

  //ConfiguraÃ§Ã£o do acelerometro para deteccao de vibracao
  acel.powerOn();

  acel.setInactivityX(1); //Define INATIVIDADE (repouso)
  acel.setInactivityY(1);
  acel.setInactivityZ(1);

  acel.setActivityX(1); //Define ATIVIDADE (vibracao)
  acel.setActivityY(1);
  acel.setActivityZ(1);

  //Acionamento da deteccao de atividade e inatividade
  acel.setInterruptMapping(ADXL345_INT_ACTIVITY_BIT, ADXL345_INT1_PIN);
  acel.setInterruptMapping(ADXL345_INT_INACTIVITY_BIT, ADXL345_INT1_PIN);

  acel.setInterrupt(ADXL345_INT_ACTIVITY_BIT, 1);
  acel.setInterrupt(ADXL345_INT_INACTIVITY_BIT, 1);

  //calibracao  de sensibilidade em relacao aa atividade e inatividade
  acel.setActivityThreshold(50);
  acel.setInactivityThreshold(50);
  acel.setTimeInactivity(10);

  //acionamento da deteccao de atividade e inatividade
  acel.setInterruptMapping(ADXL345_INT_ACTIVITY_BIT, ADXL345_INT1_PIN);
  acel.setInterruptMapping(ADXL345_INT_INACTIVITY_BIT, ADXL345_INT1_PIN);

  acel.setInterrupt(ADXL345_INT_ACTIVITY_BIT, 1);
  acel.setInterrupt(ADXL345_INT_INACTIVITY_BIT, 1);
}

long lerSensoresRF(){
  
  long umidade = analogRead(P_UMIDADE);
  long temperatura = analogRead(P_TEMPERATURA);
  long presenca = digitalRead(P_PRESENCA);

  //Para presenca o valor zero significa inatividade
  long vibracao = 0;

  //Mapeamento do potenciometro
  umidade = map(umidade, 0, 1023, 0, 100);
  temperatura = map(temperatura, 0, 1023, 0, 20);

  //Pega o interruptor da deteccao de movimento
  byte interruptAcel = acel.getInterruptSource();

  //Se ocorrer uma interrupcao, a vibracao eh setado como true
  if(acel.triggered(interruptAcel, ADXL345_INT_ACTIVITY_BIT)){
    vibracao = 1;
  }
  long rf = RFID;
  long info = rf << DESLOC_RFID;
  info = info | (presenca << DESLOC_PRESENCA);
  info = info | (vibracao << DESLOC_VIBRACAO);
  info = info | (temperatura << DESLOC_TEMPERATURA);
  info = info | umidade;

  return info;
}

void loop() {
  // emissao de dados
  emitir(lerSensoresRF());

  //Delay de 3 segundos para sincronizar com o sensor de presenca
  delay(3000);

  //recepcao de dados
  long info = receber();
  if(info == -1){
    infoRF.presenca = extrairPresenca(info);
    infoRF.vibracao = extrairVibracao(info);
    infoRF.umidade = extrairUmidade(info);
    infoRF.temperatura = extrairTemperatura(info);
    enviarParaUSB();
  }
}


void emitir(long info){
  emissor.send(info, 32);
  Serial.println("ID = "+extrairRFID(info));
  Serial.println("Temperatura = "+extrairTemperatura(info));
  Serial.println("Presenca = "+extrairPresenca(info));
  Serial.println("Umidade = "+extrairUmidade(info));
  Serial.println("Vibracao = "+extrairVibracao(info));
}

long receber(){
  long info = -1;

  if(receptor.available()){ //se ha dados disponiveis
    info = receptor.getReceivedValue();
    receptor.resetAvailable(); //zera o receptor
  }
  return info;
}


void enviarParaUSB(){
  char buff[sizeof(infoRF)]={0};
  memcpy(&buff, &infoRF, sizeof(infoRF));
  Serial.print('I');
  Serial.write(buff, sizeof(infoRF));
}

int extrairRFID(long info){
  int rf = (info & ‭66846720‬) >> DESLOC_RFID;
  return rf;
}

boolean extrairPresenca(long info){
  boolean presenca = (info & ‭131072‬) >> DESLOC_PRESENCA;
  return presenca;
}

boolean extrairVibracao(long info){
  boolean vibracao = (info & 65536) >> DESLOC_VIBRACAO;
  return vibracao;
}

int extrairTemperatura(long info){
  int temperatura = (info & 65280) >> DESLOC_TEMPERATURA;
  return temperatura;
}

int extrairUmidade(long info){
  int umidade = (info & 255);
  return umidade;
}
