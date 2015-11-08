#include <Wire.h>
#include "ADXL345.h"
#include "Adafruit_BMP085.h"

ADXL345 acel = ADXL345();//cria uma instancia do ADXL345 
Adafruit_BMP085 barom = Adafruit_BMP085();//cria uma instancia do BMP085

//struct (estrutura de dados) e usado pra dar mais performance, ja q os dados n serao enviados um a um
struct Dados{
  int acelX, acelY, acelZ;
  float altura;
};

Dados dados;//instanciando tipo de estrutura Eixos e a variavel eixos

void setup() {
  Serial.begin(9600);//inicia a porta serial a uma taxa de leitura de 9600
  acel.powerOn();//liga o acelerometro
  barom.begin();//liga o barometro
}

void loop() {
  acel.readAccel(&dados.acelX, &dados.acelY, &dados.acelZ);//o & pega o endereco da variavel...
  dados.altura = calcAltitude(barom.readPressure());//calculando altura
  enviarDados();
  delay (1000);//da um tempo na leitura do acelerometro
}

//calcula altitude de acordo com a pressao passada
float calcAltitude(float pressure){
  float A = pressure/101325;//pressao atual dividida pela pressao ao nivel do mar
  float B = 1/5.25588;
  float C = pow(A,B);
  C = 1 - C;
  C = C /0.0000225577;
  //C = 44330 * C;//equivalente a linha acima...
  return C;
}

void enviarDados(){
  int tam = sizeof(dados);//sizeof retorna o tamanho da coisa em bytes
  char buff[tam];

  memcpy(&buff, &dados, tam);//funcao nativa do C que coloca na memoria passada como buffer a coisa passada do tamanho passado...

  Serial.write('I');//inicio
  Serial.write((uint8_t*)&buff, tam);  //faz um cast para o tipo passado q e o endereco de buffer, tbm pega o tamanho do buffer
  Serial.write('F');//final
  /*Serial.write(" - Eixo x:");
  Serial.println(dados.acelX);
  Serial.write(" - Eixo y:");
  Serial.println(dados.acelY);
  Serial.write(" - Eixo z:");
  Serial.println(dados.acelZ);
  Serial.write(" - Altitude:");
  Serial.println(dados.altura);*/
};
