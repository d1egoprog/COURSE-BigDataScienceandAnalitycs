#include <stdio.h>
#include <stdlib.h>
#include <string.h>


struct pixel{
	unsigned char red;
	unsigned char green;
	unsigned char blue;	
};
typedef pixel pixel;

/*
 * Clase que Carga los datos del archivo ppm
 */
class PPM {
public:
	FILE *file;
	FILE *fileBinary;
	char *path;
	int width;
	int height;
	int color;
	int headerLength;
	int pixelArrayLength;
	unsigned char *header;
	pixel **pixelArray;
public:

	PPM() {
	}
	
	void readFile(char *source) {
		int n;
		path = source;
		n = chargeFile(source);
		if (n){
			return;
		}
		n = readHeader();
		if (n){
			return;
		}
		createPixelMatrix();
		return;
	}
	
	/*
	 * Abre el Archivo para su lectura de modo Binario y Ordinario
	 */
	int chargeFile(char *source){
		path = source;
	
		file = fopen(path, "r");
		fileBinary = fopen(path, "rb");
	
		if (file==NULL){
		   printf ("Error al abrir el archivo ubicado en %s\n",path);
		   return 1;
		}
		if (fileBinary==NULL){
		   printf ("Error al abrir el archivo ubicado en %s en modo Binario\n",path);
		   return 1;
		}
	
		printf ("Archivo Abierto Correctamente!! \n");
		return 0;
	}
	
	/*
	 * Lee la cabezera del archivo Asigna los valores a la clase
	 */
	int readHeader(void){
		int i,endOfHeader = 3;
		char boobyLine[200];
		for (i=0;i<endOfHeader;i++){
			switch (i){
				case 0:{
					fgets(boobyLine, 200, file);
					if(!strcmp("P6",boobyLine)){
						printf ("Formato Invalido Compruebe la Linea 1 del Header \n");
						return 1;
					}
					break;
				}
				case 1:{
					fgets(boobyLine, 200, file);
					if(boobyLine[0] == '#' ){
						endOfHeader = 4;
					}else{
						readHeaderData();
					}
					break;
				}
				case 2:{
					if (endOfHeader == 4){
						readHeaderData();
					}
					break;
				}
			}
		}
		printf ("Cabezera Leida Correctamente !! \n");
		return 0;
	}
	
	/*
	 * Lee Los valores de datos del archivo
	 */
	void readHeaderData(){
		fscanf (file, "%i", &width);
		fscanf (file, "%i", &height);
		fscanf (file, "%i", &color);
		return;
	}
	
	/*
	 * Lee y Almacena los datos de los pixels en una Matriz
	 */
	void createPixelMatrix(){
		unsigned char *boobyLine;
		int leido, k, i, j;
		unsigned long fileLen;
		int cols, rows;
	
		fseek(fileBinary, 0, SEEK_END);
		fileLen=ftell(fileBinary);
		fseek(fileBinary, 0, SEEK_SET);
	
		boobyLine=(unsigned char *)malloc(fileLen+1);
	
		if (!boobyLine){
			printf("Error Reservando Memoria para Leer el Archivo\n");
			return;
		}
		printf("Memoria para leer Reservada !!\n");
		leido = fread(boobyLine, sizeof(unsigned char), fileLen, fileBinary);
		//Va hasta el final de la Cabezera
		for (k=0;k<leido;k++){
			if (boobyLine[k]==0 && boobyLine[k+1]==0 && boobyLine[k+2]==0){
				break;
			}
		}
		//Asignacion de Memoria para los Nuevos datos
		headerLength = k;
		header=(unsigned char *)malloc(headerLength + 1);
		if (!header){
			printf("Error Reservando Memoria para la Cabezera\n");
			return;
		}
		printf("Memoria para la Cabezera Reservada !!\n");
		for (i=k,k=0;k<i;k++){
			header[k] = boobyLine[k];
		}
		pixelArrayLength = leido - k;
		pixelArray = (pixel**)malloc(width * sizeof(pixel*));
		if (!pixelArray){
			printf("Error Reservando Memoria para los Pixels\n");
			return;
		}
		for(i = 0; i < width; i++){
			pixelArray[i] = (pixel*)malloc(height * sizeof(pixel));
			if (!pixelArray[i]){
					printf("Error Reservando Memoria para los Pixels\n");
					return;
			}
		}
		printf("Memoria para los Pixels Reservada !!\n");	
		for (rows=0,cols=0;k<leido;k+=3,cols++){
			if (cols==width){
				cols=0;
				rows++;
			}
			pixelArray[rows][cols].red = boobyLine[k];
			pixelArray[rows][cols].green = boobyLine[k+1];
			pixelArray[rows][cols].blue = boobyLine[k+2];
		}
		return;
	}
	
	/**
	 * Escribe un Archivo en un resultado
	 */
	void writeFile(char *source) {
		unsigned char *boobyLine;
		int i,j,k;
		path = source;
	
		boobyLine=(unsigned char *)malloc(pixelArrayLength+1);
		if (!boobyLine){
			printf("Error Reservando Memoria para Escribir el Archivo\n");
			return;
		}
	
		fileBinary = fopen(path, "wb");
		if (fileBinary==NULL){
		   printf ("Error al abrir el archivo ubicado en %s en modo Binario\n",path);
		   return;
		}
		printf ("Archivo Abierto Correctamente!! \n");
	
		fwrite (header, sizeof(unsigned char), headerLength, fileBinary);
	
		for(j=0;j<width;j++){
			for(i=0;i<height;i++){
					boobyLine[k] = pixelArray[j][i].red;k++;
					boobyLine[k] = pixelArray[j][i].green;k++;
					boobyLine[k] = pixelArray[j][i].blue;k++;
			}
		}
	
		fwrite (boobyLine, sizeof(unsigned char), pixelArrayLength, fileBinary);
		printf("Escritura de Archivo Correcta!\n");
		return;
	}
	
	~PPM() {
		fclose(file);
		fclose(fileBinary);
	}

};

