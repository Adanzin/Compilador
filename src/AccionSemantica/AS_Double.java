package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Double implements AccionSemantica {
    @Override
    public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        double tokenDouble = Double.valueOf(token.toString());
    	if(cumple(tokenDouble)) {
        	if(!TablaDeSimbolos.containsKey(token.toString())){
        		Simbolo simb = new Simbolo("octal",PalabrasReservadas.obtenerIdentificador("CTE"),false);
        		TablaDeSimbolos.put(token.toString(),simb);
        	}
        }else {
        	System.out.println("CTE FUERA DE RANGO EN LA LINEA " + AnalizadorLexico.saltoDeLinea);
        	return ERROR;
        }
        try {
			lector.reset(); //ESTO ES PARA Q VUELVA A LEER EL SIMBOLO.
		} catch (IOException e) {
			e.printStackTrace();
		}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CTE");
    }; 
    
    public boolean cumple(double d) {
    	double min1 = 2.2250738585072014d-308;
    	double min2 = 1.7976931348623157d+308;
    	double min3 = -1.7976931348623157d+308;
    	double min4 = -2.2250738585072014d-308;
    	double cero = 0.0;
    	if((min1 < d && d< min2) || (min3 < d && d < min4) || (d==cero)) {
    		return true;
    	}
    	return false;

    }
}