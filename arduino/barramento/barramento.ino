#include <Wire.h>
#include "ADXL345.h"
#include "Adafruit_BMP085.h"

ADXL345 acel = ADXL345();//cria uma instancia do ADXL345 
Adafruit_BMP085 barom = Adafruit_BMP085();

struct Eixos{//struct (estrutura de dados) e usado pra dar mais performance, ja q os dados n serao enviados um a um
  int acelX, acelY, acelZ;
};

Eixos eixos;//tipo de estrutur Eixos e a variavel eixos

void setup() {
  //inicia a porta serial a uma taxa de leitura de 9600
  Serial.begin(9600);
  acel.powerOn();//liga o acelerometro
  barom.begin();//liga o barometro

}

void verAltitude(){
  float alt = barom.readAltitude(barom.readSealevelPressure(923));//le altitude pela biblioteca do barometro, passando a altitude em metros

  Serial.write(" - Atitude:");
  Serial.print(alt);

  Serial.write(" - Atitude2:");
  Serial.print(calcAltitude(barom.readPressure()));  
}

float calcAltitude(float pressure){
  float A = pressure/101325;
  float B = 1/5.25588;
  float C = pow(A,B);
  C = 1 - C;
  C = C /0.0000225577;
  return C;
}

void enviarEixos(){
  //int tam = sizeof(eixos);//sizeof retorna o tamanho da coisa em bytes
  //char buff[tam];

  //memcpy(&buff, &eixos, tam);//funcao nativa do C que coloca na memoria passada como buffer a coisa passada do tamanho passado...

  //Serial.write('I');//inicio
  //Serial.write((uint8_t*)&buff, tam);  //faz um cast para o tipo passado q e o endereco de buffer, tbm pega o tamanho do buffer
  //Serial.write('F');//final
  Serial.write(" - Eixo x:");
  Serial.print(eixos.acelX);
  Serial.write(" - Eixo y:");
  Serial.print(eixos.acelY);
  Serial.write(" - Eixo z:");
  Serial.print(eixos.acelZ);
};

void loop() {
  acel.readAccel(&eixos.acelX, &eixos.acelY, &eixos.acelZ);//o & pega o endereco da variavel...
  enviarEixos();
  verAltitude();
  delay (1000);//da um tempo nna leitura do acelerometro
}
