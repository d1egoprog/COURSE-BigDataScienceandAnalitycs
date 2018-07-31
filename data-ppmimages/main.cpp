#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include <time.h>
#include <cstdlib>
#include <iostream>

#include "ClassPPM.cpp"

//Declaracion e Inicializacion de Datos
PPM *imagenOrigen = NULL;
PPM *imagenDestino = NULL;

//Funciones Principales Requeridas Seriales
void cargarImagen(char *rutaEntrada);
void imprimirImagen(void);
void contarPixeles(void);
void escribirImagen(char *rutaSalida);

int main(int argc, char *argv[]){
	//std::string rutaEntrada = "nombredelaimagen.ppm";
	//std::string rutaSalida = "nombredelaimagen.ppm";
	//char rutaEntrada[3] = {'0','0','0'};
	//char rutaSalida[3] = {'0','0','0'};
	char rutaEntrada[21] = {'c',':','\\','t','e','m','p','\\','p','p','m','\\','b','0','x','.','p','p','m'};//{"c:\\temp\\ppm\\box.ppm"}
	char rutaSalida[21] = {'c',':','\\','t','e','m','p','\\','p','p','m','\\','b','1','x','.','p','p','m'};//{"c:\\temp\\ppm\\box.ppm"}
	cargarImagen(rutaEntrada);
	imprimirImagen();
	contarPixeles();
	escribirImagen(rutaSalida);
	return 0;
}

void cargarImagen(char *rutaEntrada){
	printf("\n\nFuncion para Cargar Una Imagen en Memoria\n");
	double inicio=clock();
	imagenOrigen = new PPM();
	imagenOrigen->readFile(rutaEntrada);
	printf("Alto: %i \t Ancho: %i \n",imagenOrigen->width, imagenOrigen->height);
	imagenDestino = imagenOrigen;
	double final=clock();
	double total=(final-inicio)/(double) CLOCKS_PER_SEC;
	printf("La Funcion cargarImagen se Demoro %f segundos\n", total);
	printf("Imagen Cargada Exitosamente!! \n");
	return;
}

void escribirImagen(char *rutaSalida){
	printf("\n\nFuncion para escribir Una Imagen en el Disco\n");
	double inicio=clock();
	imagenDestino->writeFile(rutaSalida);
	double final=clock();
	double total=(final-inicio)/(double) CLOCKS_PER_SEC;
	printf("La Funcion escribirImagen se Demoro %f segundos\n", total);
	return;
}

void contarPixeles(void){
	int i,j;
	printf("\n\nFuncion para contar Pixeles\n");
	//Numero de Pixeles
	int pixeles[2] = {0,0};
	double inicio=clock();
	for(j=0;j<imagenOrigen->width;j++){
		for(i=0;i<imagenOrigen->height;i++){
			if(imagenDestino->pixelArray[j][i].red == 0 &&
				imagenDestino->pixelArray[j][i].green == 0 &&
				imagenDestino->pixelArray[j][i].blue == 0 ){
				pixeles[0]++;
			}else{
				pixeles[1]++;
			}
		}	
	}
	printf("Cantidad de pixeles NEGROS %i --\n",pixeles[0]);
	printf("Cantidad de pixeles NO NEGROS %i --\n",pixeles[1]);
	double final=clock();
	double total=(final-inicio)/(double) CLOCKS_PER_SEC;
	printf("El analisis de la imagen se Demoro %f segundos\n", total);
	return;
}

void imprimirImagen(void){
	int i,j;
	printf("\n\nFuncion para imprimir la matriz\n");
	double inicio=clock();
	for(j=0;j<imagenOrigen->width;j++){
		for(i=0;i<imagenOrigen->height;i++){
			printf(" %u-",imagenDestino->pixelArray[j][i].red);
			printf("%u",imagenDestino->pixelArray[j][i].green);
			printf("-%u ",imagenDestino->pixelArray[j][i].blue);
		}
		printf("--\n");
	}
	double final=clock();
	double total=(final-inicio)/(double) CLOCKS_PER_SEC;
	printf("El analisis de la imagen se Demoro %f segundos\n", total);
	return;
}





