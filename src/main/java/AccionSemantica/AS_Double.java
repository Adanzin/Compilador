package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;

public class AS_Double extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos){    
    	//Cambio la 'd' por la 'E' para q se haga bien la conversion.
    	try {
	    	String aux = token.toString().replace("d", "E");
	    	double tokenDouble = Double.parseDouble(aux);
	        // Verificar si el valor cumple con la condición
	    	if (cumple(aux)) {
	            if(!TablaDeSimbolos.containsKey(aux)){            	
		        	Simbolo simb = new Simbolo();
		        	simb.setDoub(tokenDouble);
		        	Parser.tipos.put("DOUBLE",new Tipo("DOUBLE"));
		        	simb.setTipoVar(Parser.tipos.get("DOUBLE"));
		        	TablaDeSimbolos.put(aux,simb);
		        }else {TablaDeSimbolos.get(aux).incrementarContDeRef();}
	        }
        	AnalizadorLexico.Lexema = aux;  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
	        System.out.println("	╔═ Constante double "+tokenDouble);
	        cargarSalida("Constante double "+tokenDouble);
    	} catch (NumberFormatException e) {
    		System.out.println("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango "+"\u001B[0m");
        	cargarSalida("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango"+"\u001B[0m");
        }
    	AnalizadorLexico.SEREPITE=true;
		if(car==AnalizadorLexico.SALTO_DE_LINEA) {AnalizadorLexico.SEREPITE=false;}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CTE");
    }; 
    
    public boolean cumple(String d) {
    	double min=2.2250738585072014E-308;
    	double max=1.7976931348623157E+308;
        try {
            double tokenDouble = Double.parseDouble(d);
            if(!(tokenDouble >= min && tokenDouble <= max || tokenDouble==0.0)) {
    	        System.out.println("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango "+"\u001B[0m");
    	    	cargarSalida("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango"+"\u001B[0m");
               return false;	
            }else {return true;}
        } catch (NumberFormatException e) {
	        System.out.println("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double mal escrita "+"\u001B[0m");
	    	cargarSalida("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double mal escrita"+"\u001B[0m");
            return false; // Si no puede convertirse a double
            
        }
    }
}