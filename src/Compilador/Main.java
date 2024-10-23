package Compilador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
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
            AnalizadorLexico.salida = new BufferedWriter(new FileWriter("resources\\codigo-lexico.txt"));
            // Leer el contenido del archivo
            AnalizadorLexico.archivo_original = new BufferedReader(new FileReader(archivoRuta));
            //System.out.println("Se esta compilando");
            Parser par = new Parser();
            par.run();
            //System.out.println("Se compilo");
            AnalizadorLexico.salida.flush();
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
        System.out.println("       ");
        System.out.println("       ");
        System.out.println("       >>>>>  TABLA DE SIMBOLOS <<<<<");
        getSimbolos();
        
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

}
