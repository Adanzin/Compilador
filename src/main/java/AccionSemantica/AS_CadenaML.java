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

public class AS_CadenaML extends AccionSemantica {
    @Override
    public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
		Simbolo simb = new Simbolo();
		simb.setId("CADENAMULTILINEA");
		TablaDeSimbolos.put("["+token.toString()+"]", simb);
		AnalizadorLexico.Lexema = "["+token.toString()+"]";  //LE PASO EL ID A LA TABLA DE SIMBOLOS AL PARSER.
		System.out.println("	╔═ Cadena multilinea " + token.toString());
		cargarSalida("Cadena multilinea " + token.toString());
        AnalizadorLexico.token_actual.setLength(0); //VACIAMOS EL BUFFER YA QUE SE ESPERA UN NUEVO TOKEN
        return PalabrasReservadas.obtenerIdentificador("CADENAMULTILINEA"); //devolvemos el token correspondiente 
    }; 
}