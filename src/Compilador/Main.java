package Compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
            Parser par = new Parser();
            par.run();
            System.out.println("Se esta compilando");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
    }

}
