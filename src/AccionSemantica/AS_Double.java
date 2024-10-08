package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Double extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos){
    	//No me tomaba el 'd' como exponente y segun chatGPT de esta manera me andaba y asi fue.
    	String tokenString = token.toString().replace('d', 'E');
        try {
            // Convertir el token a un valor double
            Double tokenDouble = Double.parseDouble(tokenString);

            // Definir el valor cero como constante
            double cero = 0.0;

            // Verificar si el valor cumple con la condición
            if ((tokenDouble < 1.7976931348623157E+308)
                || (tokenDouble > 2.2250738585072014E-308)
                || (tokenDouble == cero)) {
            	if(!TablaDeSimbolos.containsKey(token.toString())){
	        		Simbolo simb = new Simbolo();
	        		simb.setDoub(tokenDouble);
	        		TablaDeSimbolos.put(token.toString(),simb);
	        		AnalizadorLexico.Lexema = token.toString();  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
	        	}else {TablaDeSimbolos.get(token.toString()).incrementarContDeRef();}
            }else {
	        	System.out.println("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango "+"\u001B[0m");
	    		cargarSalida("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango "+"\u001B[0m");
	        	AnalizadorLexico.SEREPITE=true;
	        	return ERROR;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El token no es un número válido.");
    		cargarSalida("Error: El token no es un número válido.");
        }
    	System.out.println("	╔═ Constante double "+token.toString());
		cargarSalida("Constante double "+token.toString());
    	AnalizadorLexico.SEREPITE=true;
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CTE");
    }; 
    
    public boolean cumple(double d) {
    	double max = 1.7976931348623157E+308;
    	double min = 2.2250738585072014E-308;
    	if((d < max)
    			|| (d > max) 
    					|| (d ==0.0)) {
    		return true;
    	}
    	return false;
    }
}