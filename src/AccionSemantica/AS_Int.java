package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Int extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        long tokenlong = Long.valueOf(token.toString());
        if(cumple(tokenlong)) {
        	if(!TablaDeSimbolos.containsKey(token.toString())){
        		Simbolo simb = new Simbolo();
        		int tokenaux = (int)tokenlong;
        		simb.setEntero(tokenaux);
        		TablaDeSimbolos.put(token.toString(),simb);        		
        	}else {TablaDeSimbolos.get(token.toString()).incrementarContDeRef();}
        	AnalizadorLexico.Lexema = token.toString();  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
        }else {
        	System.out.println("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante entera fuera de rango ");
    		cargarSalida("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante entera fuera de rango ");
        	AnalizadorLexico.SEREPITE=true;
            return ERROR;
        }
        System.out.println("	╔═ Constante entera "+tokenlong);
        cargarSalida("Constante entera "+tokenlong);
        AnalizadorLexico.SEREPITE=true;
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