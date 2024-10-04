package Compilador;
import java.util.Map;
import java.util.HashMap;

import AccionSemantica.*;
import java.io.*;



public class AnalizadorLexico {
	//TOKENS RECONOCIDOS
	public static final int OCTAL = '3';
	public static boolean SEREPITE=false;
	//COLUMNAS DE LA MATRIZ DE TRANSICION DE ESTADOS
    public static final char TABULACION = '\t';
    public static final char BLANCO = ' ';
    public static final char SALTO_DE_LINEA = '\n';
	private static final String ARCH_MATRIZ_ESTADOS = "resources\\matrizDeEstados.txt";
    private static final String ARCH_MATRIZ_ACCIONES = "resources\\matrizDeAcciones.txt";
	private static final int CANTIDAD_ESTADOS = 14;
	private static final int CANTIDAD_CARACTERES = 20;
	private static final int CERO = '0';
	private static final int MENOR7 = '1';
	private static final int MAYOR7 = '2';
	private static final int LETRA = '3';
	private static final int D = '4';
	public static int saltoDeLinea = 0;
	public static String Lexema;
    public static int estado_actual = 0;
    public static final StringBuilder token_actual = new StringBuilder();
    public static Reader archivo_original;
    public static Map<String, Simbolo> TablaDeSimbolos = new HashMap<>();  
	private static final TablaPalabrasReservadas PalabrasReservadas = new TablaPalabrasReservadas("resources\\PalabrasReservadas.txt");
    private static final AccionSemantica[][] accionesSemanticas = CargadorDeMatriz.CargarMatrizAS(ARCH_MATRIZ_ACCIONES, CANTIDAD_ESTADOS, CANTIDAD_CARACTERES);
    private static final int[][] transicion_estados = CargadorDeMatriz.CargarMatrizEstados(ARCH_MATRIZ_ESTADOS, CANTIDAD_ESTADOS, CANTIDAD_CARACTERES);

	private static char getChar(char caracter) {
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

	public static Short siguienteLectura(Reader lector, char caracter) {
        int caracter_actual;
		// Ahora vamos a matchearlo con las columnas de la matriz de estado
        switch (getChar(caracter)) {
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
			case ',':
                caracter_actual = 5;
            	break;
			case '+':
                caracter_actual = 6;
            	break;
			case '-':
                caracter_actual = 6;
            	break;
			case '*':
                caracter_actual = 7;
                break;
			case '/':
                caracter_actual = 7;
                break;
            case '_':
                caracter_actual = 8;
                break;
            case '@':
                caracter_actual = 9;
                break;
			case '[':
                caracter_actual = 10;
                break;
			case ']':
                caracter_actual = 11;
                break;
            case '#':
                caracter_actual = 12;
                break;
            case SALTO_DE_LINEA:
            	saltoDeLinea++; // incremento el nro de linea
                caracter_actual = 13;
                break;
            case ':':
                caracter_actual = 14;
                break;
            case '!':
                caracter_actual = 14;
                break;
            case '=':
                caracter_actual = 15;
                break;
            case '(':
                caracter_actual = 16;
                break;
            case ')':
                caracter_actual = 16;
                break;
            case ';':
                caracter_actual = 16;
                break;
            case '<':
                caracter_actual = 17;
                break;
            case '>':
                caracter_actual = 17;
                break;
            case '.':
                caracter_actual = 18;
                break;
            default:
                caracter_actual = 19;
                break;
        }

        AccionSemantica accion_a_ejecutar = accionesSemanticas[estado_actual][caracter_actual];
        //System.out.println(accion_a_ejecutar.toString());
        Short identificador_token = accion_a_ejecutar.ejecutar(caracter, lector, token_actual,PalabrasReservadas,TablaDeSimbolos);//-1 si es activo, -2 si es error y el token si es fin de cadena
        //System.out.println("estaba en el estado -> "+estado_actual);
        estado_actual = transicion_estados[estado_actual][caracter_actual];
        //System.out.println("Va al estado -> "+estado_actual);
        //System.out.println("El token es" + identificador_token);
        return identificador_token;
    }

	/*public static int getToken(){
		int caracter=SEREPITE;
		if(SEREPITE==-10) {
			try {
				caracter = archivo_original.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			SEREPITE=-10;
		}
		int token;
		// Recorrer cada carácter del archivo
		try {
            while (caracter!= -1) {
            	// Convertimos el entero leído a char
            	char letra = (char) caracter;
            	System.out.println(letra);
            	token = AnalizadorLexico.siguienteLectura(archivo_original, letra);
            	// Llamar al método que procesa el carácter
            	if (token == -2) {
            		System.out.println("ERROR EN LA LINEA " + AnalizadorLexico.saltoDeLinea);
            	} else if (token != -1) { // Si no es un token activo, se carga en el archivo.
            		System.out.println(token);
            		return token;
            	}
            	caracter = archivo_original.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return 0; //fin de archivo.
	}*/
	
	public static int getToken(){
		int caracter;
		int token;
		// Recorrer cada carácter del archivo
		//System.out.println("Entro al get token");
		try {
            while ((caracter = archivo_original.read()) != -1) {
        		if(SEREPITE==true) {
        			try {
        				//System.out.println("se resetea >>>>>>>");
        				archivo_original.reset();
        				caracter = archivo_original.read();
        				SEREPITE=false;
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		}
            	// Convertimos el entero leído a char
            	char letra = (char) caracter;
            	Lexema = null;
            	token = AnalizadorLexico.siguienteLectura(archivo_original, letra);
            	// Llamar al método que procesa el carácter
            	if (token != -1) { // Si no es un token activo, se carga en el archivo.
            		System.out.println(" --> El TOKEN devuelto es " + token);
            		return token;
            	}
            	archivo_original.mark(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		

		
		
		return 0; //fin de archivo.
	}
	


	 
	
/*
    public static void main(String[] args) {
        /*for (int i = 0; i < CANTIDAD_ESTADOS; i++) {
            for (int j = 0; j < CANTIDAD_CARACTERES; j++) {
                System.out.print(transicion_estados[i][j] + " ");
            }
            System.out.println();
        }*/
        /*for (int i = 0; i < CANTIDAD_ESTADOS; i++) {
            for (int j = 0; j < CANTIDAD_CARACTERES; j++) {
                System.out.print(accionesSemanticas[i][j] + " ");
            }
            System.out.println();
        }
        /*PalabrasReservadas.toString();*/
    /*	 try {
             // Inicializa el Reader con el archivo "Codigo.txt"     
             int token;
             // Continuar leyendo tokens hasta que se termine el archivo
             while ((token = getToken()) != 0) {
            	 if(Lexema!=null) {
            		 System.out.println("Linea " + saltoDeLinea  +" Token encontrado: "+ "[" + token +" | " +TablaDeSimbolos.get(AnalizadorLexico.Lexema).toString()+" ]");
            	 }else {
                     System.out.println("Linea " + saltoDeLinea  +" Token encontrado: [" + token +"]" ); 
            	 }

             }
     
             System.out.println("Fin del archivo.");
             
             // Cerrar el archivo al finalizar
             archivo_original.close();
     
         } catch (FileNotFoundException e) {
             System.out.println("Archivo no encontrado: " + e.getMessage());
         } catch (IOException e) {
             e.printStackTrace();
         }
    }*/
	
	
}
