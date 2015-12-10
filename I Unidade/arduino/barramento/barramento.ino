#include <Wire.h>
#include "ADXL345.h"
#include "Adafruit_BMP085.h"

ADXL345 acel = ADXL345();//cria uma instancia do ADXL345 
Adafruit_BMP085 barom = Adafruit_BMP085();//cria uma instancia do BMP085

//struct (estrutura de dados) e usado pra dar mais performance, ja q os dados n serao enviados um a um
struct Dados{
  int altura;
  int queda;
};

Dados dados;//instanciando tipo de estrutura Eixos e a variavel eixos

void setup() {
  Serial.begin(9600);//inicia a porta serial a uma taxa de leitura de 9600
  acel.powerOn();//liga o acelerometro
  barom.begin();//liga o barometro
  
  //set values for what is considered freefall (0-255)
  //aceleracao menor que o threshold por mais tempo que o duration
  acel.setFreeFallThreshold(5); //(5 - 9) recommended - 62.5mg per increment
  acel.setFreeFallDuration(20); //(20 - 70) recommended - 5ms per increment

  //setting all interupts to take place on int pin 1
  //I had issues with int pin 2, was unable to reset it
  acel.setInterruptMapping( ADXL345_INT_FREE_FALL_BIT,    ADXL345_INT1_PIN );
  acel.setInterrupt( ADXL345_INT_FREE_FALL_BIT,  1);
}

void loop() {
  
  //getInterruptSource clears all triggered actions after returning value
  //so do not call again until you need to recheck for triggered actions
  byte interrupts = acel.getInterruptSource();
  
  dados.altura = barom.readAltitude(101325);//calculo de altitude passando a pressao padrao ao nivel do mar...
  
  // freefall  
  dados.queda = acel.triggered(interrupts, ADXL345_FREE_FALL);
  
  enviarDados();
  delay (100);//da um tempo na leitura do acelerometro, ideal para esperar o calculo de celeraco freefall
}

void enviarDados(){
  int tam = sizeof(dados);//sizeof retorna o tamanho da coisa em bytes
  char buff[tam];
  memcpy(&buff, &dados, tam);//funcao nativa do C que coloca na memoria passada como buffer a coisa passada do tamanho passado...

  Serial.write('I');//inicio
  Serial.write((uint8_t*)&buff, tam);  //faz um cast para o tipo passado q e o endereco de buffer, tbm pega o tamanho do buffer
  Serial.write('F');//final
  /*Serial.write(" - Altitude:");
  Serial.println(dados.altura);
  Serial.write(" - Queda:");
  Serial.println(dados.queda);*/
};
