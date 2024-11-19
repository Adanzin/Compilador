package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;

public class AS_Identificador extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        // En caso de superar los 15 caracteres se trunca
    	String tokenString = truncar(token.toString());
        short salida;
        // verifica que no sea una palabra reservada
        if(PalabrasReservadas.obtenerIdentificador(tokenString)== -1){ //Si NO es palabra reservada
        	//Si no lo es ve si ya existe en la tabla de simbolos
        	if(!TablaDeSimbolos.containsKey(tokenString)){
        		Simbolo simb = new Simbolo();
        		simb.setId(tokenString);
        		TablaDeSimbolos.put(tokenString,simb);
        	}        	
   			AnalizadorLexico.Lexema = tokenString;  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
        	//si ya existe solo la devuelve
        	salida=PalabrasReservadas.obtenerIdentificador("ID");
        }else {
        	salida=PalabrasReservadas.obtenerIdentificador(tokenString);
        }
		//Se repite el ultimo caracter leido
        AnalizadorLexico.SEREPITE=true;
		if(car==AnalizadorLexico.SALTO_DE_LINEA) {
			AnalizadorLexico.SEREPITE=false;}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return salida; //devolvemos el token correspondiente
    }

	private String truncar(String tokenString) {
		String token =tokenString;
		if(token.length() > 15) {
			token=token.substring(0, 15);
			System.out.println("Linea "+ AnalizadorLexico.saltoDeLinea +" WARNING: El identificador '" 
								+ tokenString + "' fue truncado a '"+token+ "' y este podria reconocerse como palabra reservada.");
		}
		return token;
	}

    
}