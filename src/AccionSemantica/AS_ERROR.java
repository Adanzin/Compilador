package AccionSemantica;

import java.io.IOException;
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
		System.out.println("\u001B[31m"+"Error lexico en la linea" + AnalizadorLexico.saltoDeLinea +": Se leyo el siguiente caracter invalido "+ car+"\u001B[0m");
		cargarSalida("Error lexico en la linea" + AnalizadorLexico.saltoDeLinea +": Se leyo el siguiente caracter invalido "+ token.toString());
		return TOKEN_ACTIVO;
	}

}