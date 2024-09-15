package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS2 implements AccionSemantica {
    @Override
    public int ejecutar(Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        if(true/*es octal*/){
        	if(true /*cumple rangos*/) {
        		//buscar en tabla de simbolos
        		//si no esta lo agregamos
        	}
        }
        try {
			lector.mark(1);
			lector.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return AnalizadorLexico.OCTAL;
    }; 
}