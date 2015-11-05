#include <Wire.h>
#include "ADXL345.h"

ADXL345 acel = ADXL345();//cria uma instancia do ADXL345 

struct Eixos{//struct (estrutura de dados) e usado pra dar mais performance, ja q os dados n serao enviados um a um
  int acelX, acelY, acelZ;
  //int giroX, giroY, giroZ;
};
Eixos eixos;//tipo de estrutur Eixos e a variavel eixos

void setup() {
  //inicia a porta serial a uma taxa de leitura de 9600
  Serial.begin(9600);
  acel.powerOn();//liga o acelerometro
}

void enviarEixos(){
  int tam = sizeof(eixos);//sizeof retorna o tamanho da coisa em bytes
  char buff[tam];

  memcpy(&buff, &eixos, tam);//funcao nativa do C que coloca na memoria passada como buffer a coisa passada do tamanho passado...

  Serial.write('I');//inicio
  Serial.write((uint8_t*)&buff, tam);  //faz um cast para o tipo passado q e o endereco de buffer, tbm pega o tamanho do buffer
  Serial.write('F');//final
};

void loop() {
  acel.readAccel(&eixos.acelX, &eixos.acelY, &eixos.acelZ);//o & pega o endereco da variavel...

  enviarEixos();
  
  delay (100);//da um tempo nna leitura do acelerometro
}
