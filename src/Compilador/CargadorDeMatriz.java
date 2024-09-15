package Compilador;
import AccionesSemanticas.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CargadorDeMatriz {
    public static AccionSemantica[][] CargarMatrizAS(String path, int rows, int columns){
        AccionSemantica[][] resultado = new AccionSemantica[rows][columns];
        try {
            File archivo = new File(path);
            Scanner scanner = new Scanner(archivo);
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < columns; ++j) {
                    resultado[i][j] = createAction(scanner.nextLine());
                }
            }
            scanner.close();
        } catch (FileNotFoundException excepcion) {
            System.out.println("No se pudo leer el archivo " + path);
            excepcion.printStackTrace();
        } 
        return resultado;
    }

    private static AccionSemantica createAction(String action_name) {
        switch (action_name) {
            case "AS1":
                return new AS1();
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