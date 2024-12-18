package main.java.Compilador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.io.File;
import java.util.Map.Entry;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
        // Verificar si se pasó la ruta del archivo por parámetro
        if (args.length == 0) {
            System.out.println("Debe especificar la ruta del archivo como parámetro.");
            return;
        }
        String archivoRuta = args[0];  // Obtener la ruta del archivo desde el argumento
        
        try {
            // Leer el contenido del archivo
            AnalizadorLexico.archivo_original = new BufferedReader(new FileReader(archivoRuta));
            CreacionDeSalidas.creacionSalidas(archivoRuta);
            //System.out.println("Se esta compilando");
            Parser par = new Parser();
            par.run();
            //System.out.println("Se compilo");
            AnalizadorLexico.lexico.flush();
            AnalizadorLexico.sintactico.flush();
            
            	
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("       ");
        System.out.println("       ");
        System.out.println("       >>>>>  TABLA DE SIMBOLOS <<<<<");
        getSimbolos();
		System.out.println("       >>>>>  TIPOS   <<<<<");
		System.out.println("       ");
		getTipos();
		System.out.println("       ");
		System.out.println("       >>>>>  POLACA   <<<<<");
		GeneradorCodigoIntermedio.imprimirPolaca();
        if (CreacionDeSalidas.getOutputLexico().length()==0 && CreacionDeSalidas.getOutputSintactico().length()==0 
        		&& CreacionDeSalidas.getOutputSemantico().length()==0) {
        			// Generamos el assembler
        			GeneradorCodigoAssembler.generarPrograma();
        			// Antes de imprimir polaca verificamos que no existan errores semanticos
        			if(CreacionDeSalidas.getOutputSemantico().length()==0) {
        				System.out.println("       ");
                		System.out.println(" > Se compilo correctamente < ");
        				System.out.println("       ");
        				
        			}else {
        				System.out.println("       ");
                		System.out.println(" > Se detectaron errores Semanticos < ");
                	}
           }else {
            	System.out.println("       ");
            	System.out.println(" > Se detectaron errores < ");
           }
        
    }
	
	public static void getSimbolos() {
        StringBuilder sb = new StringBuilder();

        // Recorrer el mapa e ir construyendo el string
        for (Entry<String, Simbolo> entry : AnalizadorLexico.TablaDeSimbolos.entrySet()) {
            sb.append("[")
              .append(entry.getKey())  // Clave
              .append(", ")
              .append(entry.getValue())  // Valor
              .append("] ")
              .append(AnalizadorLexico.SALTO_DE_LINEA);            
        }

        // Eliminar la última coma y espacio si el mapa no está vacío
        if (!AnalizadorLexico.TablaDeSimbolos.isEmpty()) {
            sb.setLength(sb.length() - 2);  // Elimina la última ", "
        }

        System.out.println(sb);        
	}
	public static void getTipos() {
        StringBuilder sb = new StringBuilder();

        // Recorrer el mapa e ir construyendo el string
        for (Entry<String, Tipo> entry : Parser.tipos.entrySet()) {
            sb.append("[")
              .append(entry.getKey())  // Clave
              .append(", ")
              .append(entry.getValue())  // Valor
              .append("] ")
              .append(AnalizadorLexico.SALTO_DE_LINEA);
            
        }

        // Eliminar la última coma y espacio si el mapa no está vacío
        if (!Parser.tipos.isEmpty()) {
            sb.setLength(sb.length() - 2);  // Elimina la última ", "
        }

        System.out.println(sb);
	}

}
