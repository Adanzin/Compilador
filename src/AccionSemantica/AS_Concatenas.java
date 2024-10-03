package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Concatenas extends AccionSemantica {
	public AS_Concatenas() {
	}
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        token.append(car);
        return TOKEN_ACTIVO;
    }; 
}