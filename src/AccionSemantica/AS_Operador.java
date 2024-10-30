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
  		cargarSalida(token.toString());
	    switch (token.toString()) {
	    	case "!=":
	    		System.out.println("		╠══ "+token.toString());
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN Y SE ESPERA LEER UNO NUEVO. 
	    		return PalabrasReservadas.obtenerIdentificador("DISTINTO");
	    	case ":=":
	    		System.out.println("		╠══ "+token.toString());
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN Y SE ESPERA LEER UNO NUEVO. 
	    		return PalabrasReservadas.obtenerIdentificador("ASIGNACION");	
	    	case ">=":
	    		System.out.println("		╠══ "+token.toString());
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN Y SE ESPERA LEER UNO NUEVO. 
	    		return PalabrasReservadas.obtenerIdentificador("MAYORIGUAL");	
	    	case "<=":
	    		System.out.println("		╠══ "+token.toString());
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN Y SE ESPERA LEER UNO NUEVO. 
	    		return PalabrasReservadas.obtenerIdentificador("MENORIGUAL");	 
	    	default:
	    		System.out.println("		╠══ "+token.toString());
	    		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN Y SE ESPERA LEER UNO NUEVO. 
	    		return (short)car;
	}
	}
}