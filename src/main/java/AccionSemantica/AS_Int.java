package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;

public class AS_Int extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
    	try {
    		long tokenlong = Long.valueOf(token.toString());
	        if(cumple(tokenlong)) {
	        	if(!TablaDeSimbolos.containsKey(token.toString())){
	        		Simbolo simb = new Simbolo();
	        		int tokenaux = (int)tokenlong;
	        		simb.setId(token.toString());
	        		simb.setEntero(tokenaux);
	        		Parser.tipos.put("INTEGER", new Tipo("INTEGER"));
	        		simb.setTipoVar(Parser.tipos.get("INTEGER"));	     
	        		TablaDeSimbolos.put(token.toString(),simb);      
	        	}else {TablaDeSimbolos.get(token.toString()).incrementarContDeRef();}
	        	AnalizadorLexico.Lexema = token.toString();  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
	        }else {
	    		cargarSalida("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante entera fuera de rango");
	        }
    	} catch (NumberFormatException e) {
    		cargarSalida("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante entera fuera de rango");
        }
        AnalizadorLexico.SEREPITE=true;
        if(car==AnalizadorLexico.SALTO_DE_LINEA) {AnalizadorLexico.SEREPITE=false;}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        //retornar el key 
        return PalabrasReservadas.obtenerIdentificador("CTE");
    }; 
    
    public boolean cumple(double d) {
    	double min2 = 32768;
    	if(d<= min2) {
    		return true;
    	}
    	return false;

    }
}