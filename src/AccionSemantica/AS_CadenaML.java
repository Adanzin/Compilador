package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

/*Acción Semantica 1:
Checkeo que no sea 0
Inicializar string para la constante 
Agregar dígito al string 
 */

public class AS_CadenaML extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {

		if (!TablaDeSimbolos.containsKey(token.toString())) {
			Simbolo simb = new Simbolo();
			simb.setId("CadenaMultiLinea");
			TablaDeSimbolos.put(token.toString(), simb);
			AnalizadorLexico.Lexema = token.toString();  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
		}
		System.out.println(">>>> la CADENAMULTILINEA ES :" + token.toString() + "El ultimo car es = "+ car);
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CADENAMULTILINEA"); //devolvemos el token correspondiente 
    }; 
}