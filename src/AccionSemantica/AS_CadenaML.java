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

public class AS_CadenaML implements AccionSemantica {
    @Override
    public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {

		if (!TablaDeSimbolos.containsKey(token.toString())) {
			Simbolo simb = new Simbolo(PalabrasReservadas.obtenerIdentificador("CadenaMultilinea"), false);
			TablaDeSimbolos.put(token.toString(), simb);
		}
        try {
			lector.reset(); //ESTO ES PARA Q VUELVA A LEER EL SIMBOLO.
		} catch (IOException e) {
			e.printStackTrace();
		}
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CadenaMultilinea"); //devolvemos el token correspondiente 
    }; 
}