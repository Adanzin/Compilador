package AccionSemantica;
import java.io.Reader;
import java.util.Map;

import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;


public interface AccionSemantica {
    int TOKEN_ACTIVO = -1;
    int ERROR = -2;
    int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos); 
}
