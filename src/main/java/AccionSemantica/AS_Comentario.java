package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;
/*Acción Semantica 1:
Checkeo que no sea 0
Inicializar string para la constante 
Agregar dígito al string 
 */

public class AS_Comentario extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
    	AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE AL SER UN COM NO SE DEBE ALMACENAR 
        return TOKEN_ACTIVO;   
    }; 
}