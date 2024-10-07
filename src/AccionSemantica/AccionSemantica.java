package AccionSemantica;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;


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
			AnalizadorLexico.salida.newLine();  // Agregar un salto de línea
			AnalizadorLexico.salida.write(" "+in+" ");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
