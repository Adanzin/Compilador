package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Identificador implements AccionSemantica {
    @Override
    public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        String tokenString = truncar(token.toString());
        
        if(PalabrasReservadas.obtenerIdentificador(tokenString)!= -1) { //Si es palabra reservada
        	if(!TablaDeSimbolos.containsKey(tokenString)){ //Si no esta en la TS loa grego
        		Simbolo simb = new Simbolo(PalabrasReservadas.obtenerIdentificador(tokenString),true);
        		TablaDeSimbolos.put(tokenString,simb);
        	}
        }else { // Si no es PR
        	if(!TablaDeSimbolos.containsKey(tokenString)){
        		Simbolo simb = new Simbolo(PalabrasReservadas.obtenerIdentificador("ID"),false);
        		TablaDeSimbolos.put(tokenString,simb);
        	}
        }
        try {
			lector.reset(); //ESTO ES PARA Q VUELVA A LEER EL SIMBOLO.
		} catch (IOException e) {
			e.printStackTrace();
		}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador(tokenString); //devolvemos el token correspondiente
    }

	private String truncar(String tokenString) {
		String token =tokenString;
		if(token.length() > 15) {
			token=token.substring(0, 15);
			System.out.println("WARNING, EN LA LINEA" + AnalizadorLexico.saltoDeLinea + " SE TRUNCO EL IDENTIFICADOR " 
								+ tokenString + " Y ESTE PUEDE SER CONSIDERADO PALABRA RESERVADA.");
		}
		return token;
	}

    
}