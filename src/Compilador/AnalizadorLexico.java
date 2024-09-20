package Compilador;
/*
 * 	¿Qué le entrega el AL al AS?
		● Un número entero correspondiente a un tipo de token
		● Los números para cada token se definen en la etapa 2
		● En esta etapa pueden definir los valores y luego modificarlos
 * 
 * 	Tabla de Simbolos
 * 		● Cada entrada almacena atributos de los tokens (ej el lexema de un identificador o una constante)
		● Se debe implementar utilizando una estructura dinámica
		● Se recomienda utilizar una estructura que permita escribir 
			y recuperar información de manera eficiente (ej tabla de hash)
 * 
 */

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

import AccionSemantica.AccionSemantica;
import java.io.*;

public class AnalizadorLexico {
	//TOKENS RECONOCIDOS
	public static final int OCTAL = '3';
	
	//COLUMNAS DE LA MATRIZ DE TRANSICION DE ESTADOS
    public static final char TABULACION = '\t';
    public static final char BLANCO = ' ';
    public static final char SALTO_DE_LINEA = '\n';
	private static final String ARCH_MATRIZ_ESTADOS = "Compilador/matrizDeEstados.txt";
    private static final String ARCH_MATRIZ_ACCIONES = "Compilador/matrizDeAcciones.txt";
	private static final int CANTIDAD_ESTADOS = 14;
	private static final int CANTIDAD_CARACTERES = 18;
	private static final int CERO = '0';
	private static final int MENOR7 = '1';
	private static final int MAYOR7 = '2';
	private static final int LETRA = '3';
	private static final int D = '4';
	public static int saltoDeLinea = 0;
    public static int estado_actual = 0;
    public static final StringBuilder token_actual = new StringBuilder();
    
    public static Map<String, Simbolo> TablaDeSimbolos = new HashMap<>();  
	private static final TablaPalabrasReservadas PalabrasReservadas = new TablaPalabrasReservadas("PalabrasReservadas.txt");
    private static final AccionSemantica[][] accionesSemanticas = CargadorDeMatriz.CargarMatrizAS(ARCH_MATRIZ_ESTADOS, CANTIDAD_ESTADOS, CANTIDAD_CARACTERES);
    private static final int[][] transicion_estados = CargadorDeMatriz.CargarMatrizEstados(ARCH_MATRIZ_ACCIONES, CANTIDAD_ESTADOS, CANTIDAD_CARACTERES);

	private static char obtenerTipoCaracter(char caracter) {
        if (Character.isDigit(caracter)) {
			if(caracter == 0){
				return CERO;
			}else{
				if(1<= caracter && caracter  >= 7){
					return MENOR7;
				}else{
					return MAYOR7;
				}
			}
        }if (Character.isLetter(caracter)){
			if(caracter == 'd'){
				return D;
			}else{
				return LETRA;
			}
		}else {
            return caracter;
        }
    }

	public static int siguienteLectura(Reader lector, char caracter) {
        int caracter_actual;
		// Ahora vamos a matchearlo con las columnas de la matriz de estado
        switch (obtenerTipoCaracter(caracter)) {
            case CERO:
                caracter_actual = 0;
                break;
            case MENOR7:
                caracter_actual = 1;
                break;
            case MAYOR7:
                caracter_actual = 2;
                break;
			case LETRA:
                caracter_actual = 3;
                break;
            case D:
                caracter_actual = 4;
                break;
			case '_':
                caracter_actual = 5;
            	break;
			case ']':
                caracter_actual = 6;
            	break;
			case '[':
                caracter_actual = 7;
                break;
            case '+':
                caracter_actual = 8;
                break;
			case '-':
                caracter_actual = 8;
                break;
			case '*':
                caracter_actual = 9;
                break;
            case '/':
                caracter_actual = 9;
                break;
            case ':':
                caracter_actual = 10;
                break;
            case '!':
                caracter_actual = 10;
                break;
            case '<':
                caracter_actual = 11;
                break;
            case '>':
                caracter_actual = 12;
                break;
            case '=':
                caracter_actual = 13;
                break;
            case '(':
                caracter_actual = 14;
                break;
            case ')':
                caracter_actual = 14;
                break;
            case ',':
                caracter_actual = 14;
                break;
            case ';':
                caracter_actual = 14;
                break;
            case '.':
                caracter_actual = 15;
                break;
            case '#':
                caracter_actual = 16;
                break;
            case SALTO_DE_LINEA:
                caracter_actual = 17;
                saltoDeLinea++; // incremento el nro de linea
                break;
            default:
                caracter_actual = 18;
                break;
        }

        AccionSemantica accion_a_ejecutar = accionesSemanticas[estado_actual][caracter_actual];
        int identificador_token = accion_a_ejecutar.ejecutar(caracter, lector, token_actual,PalabrasReservadas,TablaDeSimbolos );//-1 si es activo, -2 si es error y el token si es fin de cadena
        estado_actual = transicion_estados[estado_actual][caracter_actual];

        return identificador_token;
    }

	
}
