package Compilador;
import AccionSemantica.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CargadorDeMatriz {
    public static AccionSemantica[][] CargarMatrizAS(String path, int filas, int columnas){
        AccionSemantica[][] matriz = new AccionSemantica[filas][columnas];
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String nombreAccion;
            int fila = 0, columna = 0;

            while ((nombreAccion = br.readLine()) != null) {
                AccionSemantica accion = crearAcSem(nombreAccion);
                matriz[fila][columna] = accion;  // Asigna la acción a la matriz
                columna++;
                if (columna == columnas) {
                    columna = 0;
                    fila++;
                }
                if (fila == filas) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matriz;
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
        case "AS_Op_solo":
            return new AS_Op_solo();
        default:
        	System.out.println("'" + accion_semantica + "'" + " da null ");
            return null;
    }
	}

    public static int[][] CargarMatrizEstados(String nombreArchivo, int filas, int columnas) {
        int[][] matriz = new int[filas][columnas];
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int fila = 0, columna = 0;

            while ((linea = br.readLine()) != null) {
                matriz[fila][columna] = Integer.parseInt(linea.trim());
                columna++;
                if (columna == columnas) {
                    columna = 0;
                    fila++;
                }
                // Si ya se han leído todas las filas y columnas, salir
                if (fila == filas) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matriz;
    }


    public static Map<String, Short> CrearMapDeArch(String path) {
    	Map<String, Short> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String key = linea.trim();
                short Num = Short.parseShort(br.readLine());
                map.put(key, Num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }


}