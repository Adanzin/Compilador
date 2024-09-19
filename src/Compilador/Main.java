package Compilador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedWriter;

public class Main {
	public static void main(String[] args) {
		
		try (Reader reader = new FileReader("Codigo.txt")) {
            int caracter;
            int token;
            BufferedWriter salida = new BufferedWriter(new FileWriter("Salida.txt"));
            // Recorrer cada carácter del archivo
            while ((caracter = reader.read()) != -1) {
                // Convertimos el entero leído a char
                char letra = (char) caracter;
    			reader.mark(1);
    			token =AnalizadorLexico.siguienteEstado(reader, letra);
                // Llamar al método que procesa el carácter
                if (token==-2) {
                	System.out.println("ERROR EN LA LINEA " + AnalizadorLexico.saltoDeLinea );
                	}else if(token!=-1) { //Si no es un token activo, se carga en el archivo.
                		salida.write(token);
                	}
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
