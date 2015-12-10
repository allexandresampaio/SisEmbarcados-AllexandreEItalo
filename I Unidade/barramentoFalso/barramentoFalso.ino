#include <Wire.h>

struct Dados{
  int altura;
  int queda;
};

int j = 100;
int i = 0;

Dados dados;

void setup() {
  Serial.begin(9600);
  j = 100;
  i = 0;
}

void loop() {
  if ((i > 10 && i < 30) || (i > 60 && i < 90)){
    dados.queda = 1;
  } else {
    dados.queda = 0;
  }
  i++;
  
  dados.altura = j;
  if (dados.altura < 0){
    dados.altura = 0;
  }
  j--;
  
  enviarDados();
  delay (100);
}

void enviarDados(){
  int tam = sizeof(dados);
  char buff[tam];
  memcpy(&buff, &dados, tam);

  Serial.write('I');
  Serial.write((uint8_t*)&buff, tam);
  Serial.write('F');
  /*Serial.print("Altura: ");
  Serial.println(dados.altura);
  Serial.print("Queda: ");
  Serial.println(dados.queda);*/
};
