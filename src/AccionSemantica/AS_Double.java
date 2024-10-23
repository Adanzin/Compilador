package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;
import Compilador.Tipo;

public class AS_Double extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos){    
    	//Cambio la 'd' por la 'E' para q se haga bien la conversion.
    	String aux = token.toString().replace("d", "E");
    	double tokenDouble = Double.parseDouble(aux);
        // Verificar si el valor cumple con la condición
    	if (cumple(aux)) {
            if(!TablaDeSimbolos.containsKey(aux)){            	
	        	Simbolo simb = new Simbolo();
	        	simb.setDoub(tokenDouble);
	        	simb.setTipoVar(new Tipo("double"));
	        	TablaDeSimbolos.put(aux,simb);
	        	AnalizadorLexico.Lexema = aux;  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
	        }else {TablaDeSimbolos.get(aux).incrementarContDeRef();}
        }else {
	        System.out.println("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango o mal escrita "+"\u001B[0m");
	    	cargarSalida("\u001B[31m"+"Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango o mal escrita"+"\u001B[0m");
        }
        System.out.println("	╔═ Constante double "+tokenDouble);
        cargarSalida("Constante double "+tokenDouble);
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
            return (tokenDouble >= min && tokenDouble <= max || tokenDouble==0.0);
        } catch (NumberFormatException e) {
            return false; // Si no puede convertirse a double
        }
    }
}