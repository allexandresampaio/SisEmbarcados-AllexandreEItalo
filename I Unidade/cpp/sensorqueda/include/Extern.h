/*
 * Extern.h
 *
 *  Created on: 15/10/2015
 *      Author: Allexandre
 */

#ifndef EXTERN_H_
#define EXTERN_H_

#ifdef __cplusplus
extern "C"{
#endif

int iniciar (char* porta);
int ler();
short getAltitude();
short getQueda();
int finalizar();

#ifdef __cplusplus
}
#endif

#endif /* EXTERN_H_ */
