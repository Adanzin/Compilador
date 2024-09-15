package Compilador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {
	public static void main(String[] args) {
		try (Reader reader = new FileReader("Codigo.txt")) {
            int caracter;
            // Recorrer cada carácter del archivo
            while ((caracter = reader.read()) != -1) {
                // Convertimos el entero leído a char
                char letra = (char) caracter;
                
                // Llamar al método que procesa el carácter
                if (AnalizadorLexico.siguienteEstado(reader, letra)==-2) {
                	System.out.println("ERRORRRRRRRRRRR");
                	}
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
