/*
 * Executor.cpp
 *
 *  Created on: 15/12/2015
 *      Author: Italo Miranda
 */

#include <iostream>
#include <Comunicacao.h>

using namespace std;

struct InfoRF {
	short id;
	short umidade;
	short temperatura;
	short vibracao;
	short presenca;
};

int main(int argc, char **argv) {
	InfoRF info = { 0 };
	char ci = 0, cf = 0;
	Comunicacao com("COM3");
	com.iniciar();

	while (true) {
		if ((com.ler(&ci, sizeof(char)) == 0) && (ci == 'I')) {
			if (com.ler((char*) &info, sizeof(InfoRF)) == 0) {
				if ((com.ler(&cf, sizeof(char)) == 0) && (cf == 'F')) {
					cout << "id = " << info.id << endl;
					cout << "umidade = " << info.umidade << endl;
					cout << "temperatura = " << info.temperatura << endl;
					cout << "vibracao = " << info.vibracao << endl;
					cout << "presenca = " << info.presenca << endl;
				}
			}
		}

		Sleep(3000);
	}

	return (0);
}
