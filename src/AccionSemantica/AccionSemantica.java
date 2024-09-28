package AccionSemantica;
import java.io.Reader;
import java.util.Map;

import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;


public abstract class AccionSemantica {
    public AccionSemantica() {
		super();
	}
	public int TOKEN_ACTIVO = -1;
    public int ERROR = -2;
    public abstract int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos); 
}
