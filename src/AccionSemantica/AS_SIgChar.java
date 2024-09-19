package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_SIgChar implements AccionSemantica {
    @Override
    public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        //Esta accion semantica no hace nada mas que usarse para no cargar los caracteres q no se cargan al token.    
        return TOKEN_ACTIVO;   
    }; 
}