package AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import Compilador.Simbolo;
import Compilador.TablaPalabrasReservadas;

/*Acción Semantica 1:
Checkeo que no sea 0
Inicializar string para la constante 
Agregar dígito al string 
 */

public class AS1 implements AccionSemantica {
    @Override
    public int ejecutar(Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
            try {
            	char caracter = (char) lector.read(); // Lee el siguiente caracter
                token.append(caracter); //Si no es 0 lo concatenamos
        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
        
        return TOKEN_ACTIVO;   
    }; 
}