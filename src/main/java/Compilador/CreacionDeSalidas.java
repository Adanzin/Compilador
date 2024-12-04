package main.java.Compilador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreacionDeSalidas {
    private static BufferedWriter Assembler;

    // Nombres de los archivos
    private static String lexico;
    private static String sintactico;
    private static String semantico;
    private static String assembler;

    // Archivos de salida
    private static File outputlexico;
    private static File outputsintactico;
    private static File outputsemantico;
    private static File outputassembler;

    public CreacionDeSalidas() {
        // Constructor vac�o
    }
    
    public BufferedWriter getBufferAssembler(){
    	return CreacionDeSalidas.Assembler;
    }
    
    public static void writeAssembler(String codigo) {
    	 try {
 			CreacionDeSalidas.Assembler.write(codigo.toString());
 			CreacionDeSalidas.Assembler.flush();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    }
    
    public static String getLexico() {
    	return CreacionDeSalidas.lexico;
    }
    
    public static String getSintactico() {
    	return CreacionDeSalidas.sintactico;
    }
    
    public static String getAssembler() {
    	return CreacionDeSalidas.assembler;
    }
    
    public static File getOutputLexico() {
    	return CreacionDeSalidas.outputlexico;
    }
    
    public static File getOutputSintactico() {
    	return CreacionDeSalidas.outputsintactico;
    }
    
    public static File getOutputAssembler() {
    	return CreacionDeSalidas.outputsintactico;
    }

    public static void creacionSalidas(String aux) {
        // Generamos los nombres de los archivos
        lexico = aux.replaceFirst("(\\.\\w+)$", "-lexico.txt");
        sintactico = aux.replaceFirst("(\\.\\w+)$", "-sintactico.txt");
        semantico = aux.replaceFirst("(\\.\\w+)$", "-semantico.txt");
        assembler = aux.replaceFirst("(\\.\\w+)$", "-assembler.txt");

        // Creamos los objetos File
        outputlexico = new File(lexico);
        outputsintactico = new File(sintactico);
        outputsemantico = new File(semantico);
        outputassembler = new File(assembler);

        try {
            // Inicializamos los BufferedWriter
            AnalizadorLexico.lexico = new BufferedWriter(new FileWriter(outputlexico));
            AnalizadorLexico.sintactico = new BufferedWriter(new FileWriter(outputsintactico));
            AnalizadorLexico.semantico = new BufferedWriter(new FileWriter(outputsemantico));
            CreacionDeSalidas.Assembler = new BufferedWriter(new FileWriter(outputassembler));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static File getOutputSemantico() {
		return CreacionDeSalidas.outputsemantico;
	}

	public static void setOutputsemantico(File outputsemantico) {
		CreacionDeSalidas.outputsemantico = outputsemantico;
	}

	public static String getSemantico() {
		return semantico;
	}

	public static void setSemantico(String semantico) {
		CreacionDeSalidas.semantico = semantico;
	}

}
