package AccionSemantica;

import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Op_solo extends AccionSemantica{

	@Override
	public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
    	AnalizadorLexico.SEREPITE=true;
	    switch (token.toString()) {
	    	case ">":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return (short)'>';
	    	case "<":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return (short)'<';		 
	    	default:
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return Short.valueOf(token.toString());

	}}
}
