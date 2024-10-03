package Compilador;

import java.util.Map;

public class TablaPalabrasReservadas {
    public static Short PALABRA_NO_RESERVADA;

    private static String ARCHIVO_PALABRAS_RESERVADAS;

    private static Map<String, Short> palabras_reservadas;

    public TablaPalabrasReservadas(String ruta) {
    	PALABRA_NO_RESERVADA= -1;
    	ARCHIVO_PALABRAS_RESERVADAS=ruta;
    	palabras_reservadas=CargadorDeMatriz.CrearMapDeArch(ARCHIVO_PALABRAS_RESERVADAS);
    }

	public Short obtenerIdentificador(String palabra_reservada) {
		String claveNormalizada = palabra_reservada.toUpperCase();
        return palabras_reservadas.getOrDefault(claveNormalizada, PALABRA_NO_RESERVADA);
    }

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        // Recorrer el mapa e ir construyendo el string
        for (Map.Entry<String, Short> entry : palabras_reservadas.entrySet()) {
            sb.append(entry.getKey())  // Clave
              .append("=")
              .append(entry.getValue())  // Valor
              .append(", ");
            
        }

        // Eliminar la última coma y espacio si el mapa no está vacío
        if (!palabras_reservadas.isEmpty()) {
            sb.setLength(sb.length() - 2);  // Elimina la última ", "
        }

        sb.append("}");
        System.out.println(sb);
        return sb.toString();
	}
	
}
