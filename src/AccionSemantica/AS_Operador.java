package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Operador extends AccionSemantica{

	@Override
	public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
		token.append(car);   
	    switch (token.toString()) {
	    	case "!=":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return PalabrasReservadas.obtenerIdentificador("DISTINTO");
	    	case ":=":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return PalabrasReservadas.obtenerIdentificador("ASIGNACION");	
	    	case ">=":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return PalabrasReservadas.obtenerIdentificador("MAYORIGUAL");	
	    	case "<=":
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return PalabrasReservadas.obtenerIdentificador("MENORIGUAL");	 
	    	default:
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
	    		return (short)car;
	}
	}
}