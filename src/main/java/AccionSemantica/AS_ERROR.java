package main.java.AccionSemantica;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import main.java.Compilador.*;

public class AS_ERROR extends AccionSemantica {

	public AS_ERROR()  {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Short ejecutar(char car, Reader lector, StringBuilder token, TablaPalabrasReservadas PalabrasReservadas, Map<String, Simbolo> TablaDeSimbolos) {
		cargarSalida("Error lexico en la linea" + AnalizadorLexico.saltoDeLinea +": Se leyo el siguiente caracter invalido "+ car);
		return TOKEN_ACTIVO;
	}

}