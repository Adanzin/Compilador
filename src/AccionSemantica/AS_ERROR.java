package AccionSemantica;

import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_ERROR extends AccionSemantica {

	public AS_ERROR()  {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
		AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE LEYÓ UN TOKEN INVALIDO
		System.out.println("	╔═ Error lexico en la linea" + AnalizadorLexico.saltoDeLinea +": No se identifica el token "+ token.toString());
		return ERROR;
	}

}