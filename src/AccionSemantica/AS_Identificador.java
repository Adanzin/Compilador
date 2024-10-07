package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Identificador extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        String tokenString = truncar(token.toString());
        short salida;
        if(PalabrasReservadas.obtenerIdentificador(tokenString)== -1){ //Si NO es palabra reservada
        	if(!TablaDeSimbolos.containsKey(tokenString)){
        		Simbolo simb = new Simbolo();
        		simb.setId(tokenString);
        		TablaDeSimbolos.put(tokenString,simb);
        		AnalizadorLexico.Lexema = tokenString;  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
        	}        	
        	salida=PalabrasReservadas.obtenerIdentificador("ID");
        }else {
        	salida=PalabrasReservadas.obtenerIdentificador(tokenString);
        }
        System.out.println("	╔═ Identificador "+tokenString);
        AnalizadorLexico.SEREPITE=true;
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return salida; //devolvemos el token correspondiente
    }

	private String truncar(String tokenString) {
		String token =tokenString;
		if(token.length() > 15) {
			token=token.substring(0, 15);
			System.out.println("\u001B[31m"+"Linea "+ AnalizadorLexico.saltoDeLinea +" WARNING: El identificador '" 
								+ tokenString + "' fue truncado a '"+token+ "' y este podria reconocerse como palabra reservada."+"\u001B[0m");
		}
		return token;
	}

    
}