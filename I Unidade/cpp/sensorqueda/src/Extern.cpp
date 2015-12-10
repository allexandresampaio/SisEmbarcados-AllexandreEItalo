/*
 * Extern.cpp
 *
 *Externalizacao das rotinas de acesso ao acelerometro
 *
 *
 *  Created on: 15/10/2015
 *      Author: Allexandre
 */

#include <Comunicacao.h>
#include "Extern.h"
#include <cstdlib>

struct Dados { //struct (estrutura de dados) e usado pra dar mais performance, ja q os dados n serao enviados um a um
	short altura; //aqui sera do tipo short pq o int do pc usa 4 bytes, mas o que vem do arduino eh de 2 bytes;
	short queda;//o tipo short usa tbm 2 bytes e, por isso, vai ser compativel....
};

//instanciando globalmente Dados e Comunicacao para uso depois
Dados dados;
Comunicacao com = NULL;

int iniciar(char* porta) {
	com = Comunicacao(porta);
	return com.iniciar();
}
int ler() {
	char ci, cf;
	//realizar a leitura do caractere i que representa o inicio
	int resultado = com.ler((char*) &ci, sizeof(ci)); //manda ler e capta o resultado
	if (resultado == EXIT_SUCCESS && (ci == 'I')) { //se o resultado for sucesso e ele tiver encontrado o i
		resultado = com.ler((char*) &dados, sizeof(dados)); //vai ler o conteudo de dados depois do i na estrutura
		if (resultado == EXIT_SUCCESS) {  //se leu os dados ok
			resultado = com.ler((char*) &cf, sizeof(cf)); //o resultado sera a leitura do f
			if (resultado == EXIT_SUCCESS && (cf == 'F')) { //se leu tudo certinho, vai marcar o resultado como sucesso
				resultado = EXIT_SUCCESS;
			}
		}
	}
	return resultado;
}

short getAltitude(){
	return dados.altura;
}

short int getQueda(){
	return dados.queda;
}

int finalizar(){
	return com.finalizar();

}
