package Compilador;

import java.util.Map;

public class TablaPalabrasReservadas {
    public static int PALABRA_NO_RESERVADA;

    private static String ARCHIVO_PALABRAS_RESERVADAS;

    private static Map<String, Integer> palabras_reservadas;

    public TablaPalabrasReservadas(String ruta) {
    	PALABRA_NO_RESERVADA=-1;
    	ARCHIVO_PALABRAS_RESERVADAS=ruta;
    	palabras_reservadas=CargadorDeMatriz.CrearMapDeArch(ARCHIVO_PALABRAS_RESERVADAS);
    }

	public int obtenerIdentificador(String palabra_reservada) {
        return palabras_reservadas.getOrDefault(palabra_reservada, PALABRA_NO_RESERVADA);
    }
}
