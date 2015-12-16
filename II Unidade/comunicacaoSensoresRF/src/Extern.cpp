/*
 * Extern.h
 *
 *  Created on: 15/12/2015
 *      Author: Italo Miranda
 */

#include "Extern.h"
#include <Comunicacao.h>
#include <cstdlib>


struct InfoRF{
	short id;
	short umidade;
	short temperatura;
	short vibracao;
	short presenca;
}infoRF;

Comunicacao com = NULL;

int iniciar(char* porta){
	com = Comunicacao(porta);
	return com.iniciar();
}

int ler(){
	int resultado = EXIT_FAILURE;
	char ci = 0, cf = 0;
	if ((com.ler(&ci, sizeof(char)) == 0) && (ci == 'I')) {
			if (com.ler((char*) &infoRF, sizeof(InfoRF)) == 0) {
				if ((com.ler(&cf, sizeof(char)) == 0) && (cf == 'F')) {
					resultado = EXIT_SUCCESS;
				}
			}
		}
	return resultado;
}

int getId(){
	return infoRF.id;
}

int getUmidade(){
	return infoRF.umidade;
}

int getTemperatura(){
	return infoRF.temperatura;
}

int getVibracao(){
	return infoRF.vibracao;
}

int getPresenca(){
	return infoRF.presenca;
}

int finalizar(){
	return com.finalizar();
}
