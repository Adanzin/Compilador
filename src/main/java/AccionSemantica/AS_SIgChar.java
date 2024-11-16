package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;

public class AS_SIgChar extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        //Esta accion semantica no hace nada mas que usarse para no cargar los caracteres q no se cargan al token.    
    	return TOKEN_ACTIVO;   
    }; 
}