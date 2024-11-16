package main.java.Compilador;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class CreacionDeSalidas {
	public static BufferedWriter Assembler; 
	public CreacionDeSalidas() {
		// TODO Auto-generated constructor stub
	}
	public static void creacionSalidas(String aux) {
        // Generamos el nombre del archivo del lexico
        String lexico = aux.replaceFirst("(\\.\\w+)$", "-lexico.txt");
        // Generamos el nombre del archivo del sintactico
        String sintactico = aux.replaceFirst("(\\.\\w+)$", "-sintactico.txt");
        // Generamos el nombre del archivo del assembler
        String assembler = aux.replaceFirst("(\\.\\w+)$", "-assembler.txt");
        
        // Creamos el archivo de salida
        File outputlexico = new File(lexico);
        // Creamos el archivo de salida
        File outputsintactico = new File(sintactico);
        // Creamos el archivo de salida
        File outputassembler = new File(assembler);
        
        try {
			AnalizadorLexico.lexico = new BufferedWriter(new FileWriter(outputlexico));
			AnalizadorLexico.sintactico = new BufferedWriter(new FileWriter(outputsintactico));
			CreacionDeSalidas.Assembler = new BufferedWriter(new FileWriter(outputassembler));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}