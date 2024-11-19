package main.java.Compilador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreacionDeSalidas {
    public static BufferedWriter Assembler;

    // Nombres de los archivos
    public static String lexico;
    public static String sintactico;
    public static String assembler;

    // Archivos de salida
    public static File outputlexico;
    public static File outputsintactico;
    public static File outputassembler;

    public CreacionDeSalidas() {
        // Constructor vacío
    }

    public static void creacionSalidas(String aux) {
        // Generamos los nombres de los archivos
        lexico = aux.replaceFirst("(\\.\\w+)$", "-lexico.txt");
        sintactico = aux.replaceFirst("(\\.\\w+)$", "-sintactico.txt");
        assembler = aux.replaceFirst("(\\.\\w+)$", "-assembler.txt");

        // Creamos los objetos File
        outputlexico = new File(lexico);
        outputsintactico = new File(sintactico);
        outputassembler = new File(assembler);

        try {
            // Inicializamos los BufferedWriter
            AnalizadorLexico.lexico = new BufferedWriter(new FileWriter(outputlexico));
            AnalizadorLexico.sintactico = new BufferedWriter(new FileWriter(outputsintactico));
            CreacionDeSalidas.Assembler = new BufferedWriter(new FileWriter(outputassembler));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
