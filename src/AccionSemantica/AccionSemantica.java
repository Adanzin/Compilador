package AccionSemantica;
import java.io.Reader;
import java.util.Map;

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
    public Short ERROR = -2;
    public abstract Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos); 
}
