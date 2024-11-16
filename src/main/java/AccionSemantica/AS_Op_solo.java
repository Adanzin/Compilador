package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;

public class AS_Op_solo extends AccionSemantica{

	@Override
	public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
    	AnalizadorLexico.SEREPITE=true;
	    switch (token.toString()) {
	    	case ">":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		System.out.println("		╠══ >");
	       		cargarSalida(">");
	    		return (short)'>';
	    	case "<":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		System.out.println("		╠══ <");
	       		cargarSalida(" <");
	    		return (short)'<';		 
	    	default:
	    		System.out.println("		╠══ "+token.toString());
	       		cargarSalida(token.toString());
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return Short.valueOf(token.toString());

	}}
}
