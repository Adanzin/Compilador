package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Operador extends AccionSemantica{

	@Override
	public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
		String tokenString = token.toString();
		try {
			lector.reset(); //ESTO ES PARA Q VUELVA A LEER EL SIMBOLO.
		} catch (IOException e) {
			e.printStackTrace();
		}
	    AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
	    switch (tokenString) {
	    	case ">":
	    		return (int)tokenString.charAt(0);//Preguntar estos tokens individuales como buscarlos si los define el codigo ASCII
	    	case "<":
	    		return (int)tokenString.charAt(0);
	    	case "!=":
	    		return PalabrasReservadas.obtenerIdentificador("DISTINTO");
	    	case ":=":
	    		return PalabrasReservadas.obtenerIdentificador("ASIGNACION");	
	    	case ">=":
	    		return PalabrasReservadas.obtenerIdentificador("MAYORIGUAL");	
	    	case "<=":
	    		return PalabrasReservadas.obtenerIdentificador("MENORIGUAL");
	    	case "=":
	    		return (int)tokenString.charAt(0);
	    	case ")":
	    		return (int)tokenString.charAt(0);
	    	case "(":
	    		return (int)tokenString.charAt(0);
	    	case ",":
	    		return (int)tokenString.charAt(0);	
	    	case ".":
	    		return (int)tokenString.charAt(0);
	    	case ";":
	    		return (int)tokenString.charAt(0);
	    	case "+":
	    		return (int)tokenString.charAt(0); 
	    	case "-":
	    		return (int)tokenString.charAt(0);
	    	case "*":
	    		return (int)tokenString.charAt(0);
	    	case "/":
	    		return (int)tokenString.charAt(0);
	    	default:
	    		return ERROR;
	}
	}
}