/*
 * Executor.cpp
 *
 *Testa a comunicacao com o arduino, retornando e exibindo
 *Testa valores dos eixos do acelerometro.
 *
 *
 *
 *  Created on: 15/10/2015
 *      Author: Allexandre
 */

#include "Comunicacao.h"
#include "iostream"
using namespace std;

struct Dados { //struct (estrutura de dados) e usado pra dar mais performance, ja q os dados n serao enviados um a um
	short altura;
	short queda;
};

int main(int argc, char **argv) {

	//criar uma instancia da classe de comunicacao
	Comunicacao com = Comunicacao("COM5"); //verificar a porta correta na interface do arduino, no linux sera diferente

	//iniciou a comunicacao
	if (com.iniciar() == EXIT_SUCCESS) {  //se foi iniciado com sucesso
		char ci, cf;
		Dados dados;

		while (true) {//vai fazer o loop pra pegar os resultados do loop do arduino
			//realizar a leitura do caractere i que representa o inicio
			int resultado = com.ler((char*) &ci, sizeof(ci)); //manda ler e capta o resultado
			if (resultado == EXIT_SUCCESS && (ci == 'I')) { //se o resultado for sucesso e ele tiver encontrado o i
				resultado = com.ler((char*) &dados, sizeof(dados)); //vai ler o conteudo de dados depois do i na estrutura
				if (resultado == EXIT_SUCCESS) {  //se leu os dados ok
					resultado = com.ler((char*) &cf, sizeof(cf)); //o resultado sera a leitura do f
					if (resultado == EXIT_SUCCESS && (cf == 'F')) { //se leu tudo certinho, vai imprimir
						cout << "Altura = " << dados.altura << endl;
						cout << "Queda = " << dados.queda << endl;
					}
				}
			}
			Sleep(100);//pra n ficar batendo no arduino, ele vai esperar o mesmo tempo q o arduino leva pra enviar
		}
	}

	return EXIT_SUCCESS;
}
