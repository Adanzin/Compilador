package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Parser;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Identificador implements AccionSemantica {
    @Override
    public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        String tokenString = truncar(token.toString());
        
        if(PalabrasReservadas.obtenerIdentificador(tokenString)== -1) { //Si es palabra reservada
        	if(!TablaDeSimbolos.containsKey(tokenString)){
        		Simbolo simb = new Simbolo();
        		TablaDeSimbolos.put(tokenString,simb);
        		Parser.yylval = new ParserVal(tokenString);  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
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