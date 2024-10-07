package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Double extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos){
    	double tokenDouble = Double.valueOf(token.toString().replace('d', 'E'));
    	if(cumple(tokenDouble)) {
        	if(!TablaDeSimbolos.containsKey(token.toString())){
        		Simbolo simb = new Simbolo();
        		simb.setDoub(tokenDouble);
        		TablaDeSimbolos.put(token.toString(),simb);
        		AnalizadorLexico.Lexema = token.toString();  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
        	}else {TablaDeSimbolos.get(token.toString()).incrementarContDeRef();}
        }else {
        	System.out.println("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante double fuera de rango ");
        	return ERROR;
        }
    	System.out.println("	╔═ Constante double "+tokenDouble);
    	AnalizadorLexico.SEREPITE=true;
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CTE");
    }; 
    
    public boolean cumple(double d) {
    	double cero = 0.0;
    	if((d < 1.7976931348623157d+308) || (d > 2.2250738585072014d-308) || (d==cero)) {
    		return true;
    	}
    	return false;
    }
}