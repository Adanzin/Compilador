package Compilador;
import AccionSemantica.*;
import accion_semantica.AS0;
import accion_semantica.AS1;
import accion_semantica.AS2;
import accion_semantica.AS3;
import accion_semantica.AS4;
import accion_semantica.AS5;
import accion_semantica.AS6;
import accion_semantica.AS7;
import accion_semantica.AS8;
import accion_semantica.AS9;
import accion_semantica.ASE;
import accion_semantica.ASa;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CargadorDeMatriz {
    public static AccionSemantica[][] CargarMatrizAS(String path, int rows, int columns){
        AccionSemantica[][] resultado = new AccionSemantica[rows][columns];
        try {
        	BufferedReader archivo = new BufferedReader(new FileReader(path));
            System.out.println("ENTRO AL LOOP");
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    AccionSemantica as = crearAcSem(archivo.readLine());
                    if(as == null){
                        System.out.println("Accion semantica no reconocida.");
                    } else{
                        resultado[i][j] = as;
                    }
                }
            }
            archivo.close();
        } catch (IOException excepcion) {
            System.out.println("No se pudo leer el archivo " + path);
            excepcion.printStackTrace();
        } 
        return resultado;
    }

    private static AccionSemantica crearAcSem(String accion_semantica) {
    	switch (accion_semantica) {
        case "AS_CadenaML":
            return new AS_CadenaML();
        case "AS_Comentario":
            return new AS_Comentario();
        case "AS_Concatenas":
            return new AS_Concatenas();
        case "AS_ConcatenasSinSaltoDeLinea":
            return new AS_ConcatenasSinSaltoDeLinea();
        case "AS_Double":
            return new AS_Double();
        case "AS_ERROR":
            return new AS_ERROR();
        case "AS_ETIQUETA":
            return new AS_ETIQUETA();
        case "AS_Identificador":
            return new AS_Identificador();
        case "AS_Int":
            return new AS_Int();
        case "AS_Octal":
            return new AS_Octal();
        case "AS_Operador":
            return new AS_Operador();
        case "AS_SIgChar":
            return new AS_SIgChar();
        default:
            return null;
    }
	}

	public static int[][] CargarMatrizEstados(String path, int rows, int columns) {
        int[][] int_matrix = new int[rows][columns];

        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    int_matrix[i][j] =  Integer.parseInt(scanner.nextLine());
                }
            }

            scanner.close();
        } catch (FileNotFoundException excepcion) {
            System.out.println("No se pudo leer el archivo " + path);  
            excepcion.printStackTrace();
        }

        return int_matrix;
    }


    public static Map<String, Integer> CrearMapDeArch(String path) {
        Map<String, Integer> map = new HashMap<>();

        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNext()) {
                String palabra_reservada = scanner.next();
                int identificador = scanner.nextInt();
                map.put(palabra_reservada, identificador);
            }

            scanner.close();
        } catch (FileNotFoundException excepcion) {
            System.out.println("No se pudo leer el archivo " + path);
            excepcion.printStackTrace();
        }

        return map;
    }


}