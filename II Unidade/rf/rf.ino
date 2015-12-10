#include <Wire.h>
#include "RCSwitch.h"
#include "ADXL345.h"

//instancia do emissor e receptor
RCSwitch emissor = RCSwitch();
RCSwitch receptor = RCSwitch();

//instancia do acelerometro
ADXL345 acel = ADXL345();

//atende a uma faixa de 10 RFs
#define RFID_LIMITE_INFERIOR 88987
#define RFID_LIMITE_SUPERIOR 88997

#define DESLOCAMENTO_RFID 17
#define DESLOCAMENTO_MOVMT 16
#define DESLOCAMENTO_BATMT 8

#define PINO_BATIMENTOS 0
#define PINO_TEMPERATURA 1

//criando estrutura InfoRF e ja instanciando infoRF
struct InfoRF{
  int id;
  int batimentos;
  int temperatura;
  boolean movimento;
} infoRF;

void setup(){
  Serial.begin(9000);

  //configuracao do emissor/receptor rf
  emissor.enableTransmit(4);
  receptor.enableReceive(0);

  //configuracao do acelerometro p/ deteccao de movimentos
  acel.powerOn();
  acel.setInactivityX(1);
  acel.setInactivityY(1);
  acel.setInactivityZ(1);
  acel.setActivityX(1);
  acel.setActivityY(1);
  acel.setActivityZ(1);

  //calibracao de sensibilidade em relacao aa atividade e inatividade
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

  long batimentos = analogRead(PINO_BATIMENTOS);
  long temperatura = analogRead(PINO_TEMPERATURA);
  long movimento = 0;

  batimentos = map(batimentos, 0, 1023, 0, 200);
  temperatura = map(temperatura, 0, 1023, 0, 40);

  byte interruptAcel = acel.getInterruptSource();
  if (acel.triggered(interruptAcel, ADXL345_INT_INACTIVITY_BIT)){
    movimento = 1;
  }
  
  long rf = RFID_LIMITE_INFERIOR;
  long info = rf << DESLOCAMENTO_RFID;
  info = info | (movimento << DESLOCAMENTO_MOVMT);
  info = info | (batimentos << DESLOCAMENTO_BATMT);
  info = info | temperatura;
  return info;
}

boolean RFIDValido(long info){
  boolean valido = false;
  infoRF.id = info >> DESLOCAMENTO_RFID;
  if ((infoRF.id >= RFID_LIMITE_INFERIOR) && (infoRF.id <= RFID_LIMITE_SUPERIOR)){
    valido = true;
  }
}

void emitir(long info){
  emissor.send(info, 32);
}

long receber(){
  long info = -1;

  if (receptor.available()){
    info = receptor.getReceivedValue();
    receptor.resetAvailable();
    }
    return info;
}

void enviarParaUSB(){
  char buff[sizeof(InfoRF)] = {0};

  memcpy(&buff, &infoRF, sizeof(InfoRF));
  Serial.write('I');
  Serial.write((uint8_t*) &buff, sizeof(InfoRF));
}

boolean extrairMovimento(long info){
  int movimento = (info & 65536) >> DESLOCAMENTO_MOVMT;

  return (movimento == 1);
}

int extrairBatimentos(long info){
  int batimentos = (info & 65280) >> DESLOCAMENTO_BATMT;
}

int extrairTemperatura(long info){
  int temperatura = (info & 255);

  return temperatura;
}

void loop(){
  //ENVIO DE DADOS
  long info = lerSensoresRF();
  emitir(info);
  delay(50);

  //RECEPCAO DE DADOS
  if (receptor.available()){
    long info = receber();

    if (info != -1){
      if (RFIDValido(info)){
        infoRF.movimento = extrairMovimento(info);
        infoRF.batimentos = extrairBatimentos(info);
        infoRF.temperatura = extrairTemperatura(info);
        enviarParaUSB();
        }
    }
  }
}
