package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;

public class AS_Octal extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
	   try {
    		int tokenOct = Integer.valueOf(token.toString(),10); 
	        int tokenint = Integer.valueOf(token.toString(),8);//paso de octal a int
	        if(cumple(tokenint)) {
	        	if(!TablaDeSimbolos.containsKey(token.toString())){
	        		Simbolo simb = new Simbolo(8);
	        		simb.setEntero(tokenOct);
	        		simb.setTipoVar(new Tipo("integer"));
	        		TablaDeSimbolos.put(token.toString(),simb);
	        		AnalizadorLexico.Lexema = token.toString();//LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
	        	}else {TablaDeSimbolos.get(token.toString()).incrementarContDeRef();}
	        }else {
	        	System.out.println("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante octal fuera de rango o mal escrita "+"\u001B[0m");
	    		cargarSalida("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante octal fuera de rango o mal escrita ");
	        }
	    	System.out.println("	╔═ Constante octal "+tokenint);
	    	cargarSalida("Constante octal "+tokenint);
	   	} catch (NumberFormatException e) {
	   		System.out.println("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante octal fuera de rango o mal escrita "+"\u001B[0m");
    		cargarSalida("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante octal fuera de rango o mal escrita ");
	    }
    	AnalizadorLexico.SEREPITE=true;
    	if(car==AnalizadorLexico.SALTO_DE_LINEA) {AnalizadorLexico.SEREPITE=false;}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CTE");
    }; 
    public boolean cumple(double d) {
    	double min = 32768;
    	if(d<= min) {
    		return true;
    	}
    	return false;
    }
}