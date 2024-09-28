package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

/*Accion Semantica 1:
Checkeo que no sea 0
Inicializar string para la constante 
Agregar digito al string 
 */

public class AS_CadenaML extends AccionSemantica {
    @Override
    public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {

		if (!TablaDeSimbolos.containsKey(token.toString())) {
			Simbolo simb = new Simbolo("CadenaMultiLinea");
			TablaDeSimbolos.put(token.toString(), simb);
			AnalizadorLexico.Lexema = token.toString();  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
		}
        try {
			lector.reset(); //ESTO ES PARA Q VUELVA A LEER EL SIMBOLO.
		} catch (IOException e) {
			e.printStackTrace();
		}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CADENAMULTILINEA"); //devolvemos el token correspondiente 
    }; 
}