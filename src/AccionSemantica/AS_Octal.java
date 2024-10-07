package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_Octal extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        int tokenint = Integer.valueOf(token.toString(),8); //paso de octal a int
    	if(cumple(tokenint)) {
        	if(!TablaDeSimbolos.containsKey(token.toString())){
        		Simbolo simb = new Simbolo(8);
        		simb.setEntero(tokenint);
        		TablaDeSimbolos.put(token.toString(),simb);
        		AnalizadorLexico.Lexema = token.toString();//LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
        	}else {TablaDeSimbolos.get(token.toString()).incrementarContDeRef();}
        }else {
        	System.out.println("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante octal fuera de rango ");
    		cargarSalida("Error lexico en la linea " + AnalizadorLexico.saltoDeLinea+" : Constante octal fuera de rango ");
        	AnalizadorLexico.SEREPITE=true;
        	return ERROR;
        }
    	System.out.println("	╔═ Constante octal "+tokenint);
    	cargarSalida("Constante octal "+tokenint);
    	AnalizadorLexico.SEREPITE=true;
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