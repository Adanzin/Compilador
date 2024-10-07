package AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.AnalizadorLexico;
import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

public class AS_ETIQUETA extends AccionSemantica {

	@Override
	public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas,
			Map<String, Simbolo> TablaDeSimbolos) {
		if (!TablaDeSimbolos.containsKey(token.toString())) {
			Simbolo simb = new Simbolo();
			simb.setId(token.toString());
			TablaDeSimbolos.put(token.toString(), simb);
			AnalizadorLexico.Lexema = token.toString();  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
		}
		System.out.println("	╔═ Etiqueta "+token.toString());
		cargarSalida("Etiqueta "+token.toString());
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("ETIQUETA"); //devolvemos el token correspondiente 
    };

}
