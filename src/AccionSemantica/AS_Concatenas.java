package AccionSemantica;

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

public class AS_Concatenas implements AccionSemantica {
    @Override
    public int ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
        token.append(car);     
        return TOKEN_ACTIVO;   
    }; 
}