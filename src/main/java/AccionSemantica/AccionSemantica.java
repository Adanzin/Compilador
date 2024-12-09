package main.java.AccionSemantica;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;


public abstract class AccionSemantica {
    public AccionSemantica() {
		super();
	}
	@Override
	public String toString() {
		return "->" + getClass();
	}
	public Short TOKEN_ACTIVO = -1;
    public Short ERROR = 280;
    public abstract Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos); 
    public void cargarSalida(String in) {
		try {
			AnalizadorLexico.lexico.newLine();  // Agregar un salto de línea
			AnalizadorLexico.lexico.write(" "+in+" ");
			AnalizadorLexico.lexico.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
