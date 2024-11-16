package main.java.Compilador;

import java.util.Stack;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GeneradorCodigoAssembler {
	
	public static Map<String, StringBuilder> codigoAssembler = new HashMap<>();
	public static Stack<String> pila = new Stack<String>();
	
	public static int numAuxiliares = 1;
	public static String ambitoActual = "";
	public static Integer numLabel = 1;
		
	public GeneradorCodigoAssembler() {
	}
	
	public static StringBuilder recorrerPolaca(ArrayList<String> polacaActual) {
		StringBuilder codigo = new StringBuilder();
		String elemento;
		for (int i=0; i < polacaActual.size();i++) {
			elemento = polacaActual.get(i);
			switch (elemento) {
				case "+","-","*","/",":=":
					operadorBinario(elemento, codigo);
					break;
				case "INTEGER", "DOUBLE", "OCTAL", "RET":
					operadorUnario(elemento, codigo);
					break;
				case "CALL":
					operadorFuncion(codigo);
					break;
				case "BF","BI":
					operadorSentenciasControl(elemento, codigo, polacaActual.get(i-1));
					break;
				case "<",">","<=",">=","==", "!=":
					operadorComparacion(elemento, codigo);
					break;
				case "OUTF":
					imprimirPorPantalla(elemento, codigo);
					break;
				default: //Entra si es un operando o si es un LABEL+N° que no puedo chequear en el case
					if(elemento.startsWith("LABEL")) { //Es una etiqueta
						codigo.append(elemento + ": \n");
					}
					else { //Es un operando sino
						pila.push(elemento);
					}
					break;
			}
		}
		return codigo;
	}
	
	public static void operadorBinario(String operacion, StringBuilder codigo) {
		String operando2 = pila.pop();
		//System.out.println("operando2: " + operando2 + "\n");
		String operando1 = pila.pop();
		//System.out.println("operando1: " + operando1 + "\n");
		if(	AnalizadorLexico.TablaDeSimbolos.get(operando1).sonCompatibles(AnalizadorLexico.TablaDeSimbolos.get(operando2))) {
			if (AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo()==Parser.tipos.get("INTEGER") || AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo()==Parser.tipos.get("OCTAL")) {
				operacionEnteroOctal(operando1, operando2, operacion, codigo);
			} else {
				operacionDouble(operando1, operando2, operacion, codigo);
			}
		}
	}
	
	public static void operadorFuncion(StringBuilder codigo) {
		String operando = pila.pop(); //Es el nombre de la funcion
		//Queda apilado el parametro real para que lo desapile la funcion
		codigo.append("CALL " + operando + "\n");
	}
	
	public static void operadorSentenciasControl(String elemento, StringBuilder codigo, String elementoAnterior) {
		String operador = pila.pop(); //Es la direccion a saltar
		switch(elemento) {
			case "BF":
				switch (elementoAnterior) {
				//Le paso el operador anterior al salto para saber que comparación era y así usar el jump adecuado
					case ">":
						codigo.append("JLE LABEL" + operador + "\n"); //Salto en el caso contrario al de la comparacion
						break;
					case "<":
						codigo.append("JGE LABEL" + operador + "\n"); //Salto en el caso contrario al de la comparacion
						break;
					case ">=":
						codigo.append("JL LABEL" + operador + "\n"); //Salto en el caso contrario al de la comparacion
						break;
					case "<=":
						codigo.append("JG LABEL" + operador + "\n"); //Salto en el caso contrario al de la comparacion
						break;
					case "==":
						codigo.append("JNE LABEL" + operador + "\n"); //Salto en el caso contrario al de la comparacion
						break;
					case "!=":
						codigo.append("JE LABEL" + operador + "\n"); //Salto en el caso contrario al de la comparacion
						break;
				}
				break;
			case "BI":
				codigo.append("JMP LABEL" + operador + "\n"); //Salto sí o sí a la etiqueta
				break;
		}
			
	}
	
	public static void operacionEnteroOctal(String operando1, String operando2, String operacion, StringBuilder codigo) {
		//Esta funcion usa el registro AX de 16 bits para enteros y octales.
		if(operacion!=":=") {
			codigo.append("MOV AX, " + operando1 + "\n"); 
		}
		switch (operacion) {
			case "+":
				codigo.append("ADD AX," + operando2 + "\n");
				break;
			case "-":
				//Revisar caso de la resta
				codigo.append("SUB AX," + operando2 + "\n"); 
				break;
			case "*":
				codigo.append("IMUL " + operando2 + "\n"); //Uso IMUL para cubrir el caso en que el operando sea una constante literal
				break;
			case "/":
				if(AnalizadorLexico.TablaDeSimbolos.get(operando1).esLiteral()) { 
					crearAuxiliar(AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo());
					operando1 = pila.pop();
				}
				if(AnalizadorLexico.TablaDeSimbolos.get(operando2).esLiteral()) { 
					crearAuxiliar(AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo());
					operando2 = pila.pop();
				}
				codigo.append("MOV BX," + operando2 + "\n"); //Uso BX para no pisar AX con el operando1
				codigo.append("CMP BX" + ",0" + "\n");
				codigo.append("JE DivisionPorCero"); //JZ salta si la comparacion del operando2 con el cero es TRUE
				//Continua el flujo normal en caso de no saltar
				codigo.append("IDIV " + operando2 + "\n");
				break;
			case ":=":
				String auxOp = operando1;
				operando1 = operando2;
				operando2 = auxOp;
				//No andaba bien así que los intercambié para que se haga correctamente
				codigo.append("MOV AX, " + operando2 + "\n");
				codigo.append("MOV " + operando1 + ", AX" + "\n");
				break;
			default:
				//Después veré pero acá no debería entrar nada
				break;
		}
		if(operacion!= ":=") {
			codigo.append("MOV @aux" + numAuxiliares + ",AX");
			pila.push(crearAuxiliar(AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo()));
		}
		codigo.append("\n");
	}
	
	public static void operacionDouble(String operando1, String operando2,String operacion, StringBuilder codigo) {
		codigo.append("FLD " + operando2 + "\n"); //Apilo el operando2
		codigo.append("FLD " + operando1 + "\n"); //Apilo el operando1
		String aux = crearAuxiliar(AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo());
		switch(operacion) {
			case "+":
				codigo.append("FADD" + "\n"); //ST(0) = ST(0) + ST(1)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar
				pila.push(aux);
				break;
			case "-":
				codigo.append("FSUB" + "\n"); //ST(0) = ST(0) - ST(1)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar
				pila.push(aux);
				break;
			case "*":
				codigo.append("FMUL" + "\n"); //ST(0) = ST(0) * ST(1)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar 
				break;
			case "/":
				codigo.append("FXCH"); //Intercambio operando1 y operando2 así queda operando2 en el tope y lo comparo despues
				codigo.append("FTST" + "\n"); //Comparo ST (operando2) con el cero
				codigo.append("FSTCW mem2byte" + "\n"); //Almaceno la palabra de estado en la memoria
				codigo.append("MOV AX mem2byte"); //Copio el contenido en AX del procesador principal
				codigo.append("SAHF"); //Preguntar que es esto
				// Preguntar lo anterior
				//-----------------------------------------------------------------
				codigo.append("FXCH"); //Vuelvo a intercambiar así realizo correctamente la operacion
				codigo.append("FDIV" + "\n"); //ST(0) = ST(0) / ST(1)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar 
				break;
			case ":=":
				codigo.append("FSTP " + operando2 + "\n"); //Guardo el valor de operando2 en operando1 pero por cuestiones de como esta hecha la polaca lo escribo al reves
				break;
			default:
				//Acá no debería entrar nada
				break;
		}
	}
	
	public static void operadorUnario(String elemento, StringBuilder codigo) {
		String operando = pila.pop();
		switch (elemento) {
			case "INTEGER", "OCTAL", "DOUBLE":
				operacionConversion(operando, elemento, codigo);
				break;
			case "RET":
				codigo.append("MOV AX, " + operando + "\n"); //Guardo la variable que quiero retornar en AX
				codigo.append("MOV @aux " + numAuxiliares + ", AX" + "\n");
				pila.push(crearAuxiliar(AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo()));
				codigo.append("POP ESI \n");
				codigo.append("POP EDI \n");
				codigo.append("MOV ESP, EBP \n");
				codigo.append("POP EBP \n");
				codigo.append("RET" + "\n");
				break;
		}
	}
	
	public static void operacionConversion(String operando, String elemento, StringBuilder codigo) {
		switch (elemento) {
			case "DOUBLE":
				if (AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo() == Parser.tipos.get("INTEGER")) {
					//Conversion de Entero a Double
					codigo.append("FILD " + operando + "\n"); //Cargo el entero como double
					codigo.append("FSTP @aux" + numAuxiliares + "\n"); //Almaceno el resultado en @aux
					pila.push(crearAuxiliar(Parser.tipos.get("DOUBLE")));
				}
				break;
			case "INTEGER":
				if (AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo() == Parser.tipos.get("DOUBLE")) {
					//Conversion de Double a Entero
					codigo.append("FLD " + operando + "\n"); //Apilo en ST
					codigo.append("FISTP @aux" + numAuxiliares + "\n");
					pila.push(crearAuxiliar(AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo()));
				}
				break;
			default:
				break;
			
		}
	}
	
	public static void operadorComparacion(String elemento, StringBuilder codigo) {
		String operando2 = pila.pop();
		String operando1 = pila.pop();
		codigo.append("MOV AX, " + operando1 + "\n");
		codigo.append("SUB AX, " + operando2 + "\n"); //Comparo los operandos y el resultado va a afectar a los flags para los saltos
	}
	
	public static void imprimirPorPantalla(String elemento, StringBuilder codigo) {
		//Guardar operando anterior en una aux
		//Imprimir en pantalla la aux
		String operando = pila.pop();
		Simbolo simbOperando = AnalizadorLexico.TablaDeSimbolos.get(operando);
		if (simbOperando.getTipo() == Parser.tipos.get("INTEGER") ||simbOperando.getTipo() == Parser.tipos.get("OCTAL")) {
			codigo.append("invoke printf, addr mensajeError_DivisionCero");
		}
	}
	
	
	public static void crearDivisionPorCero(StringBuilder codigo) {
		codigo.append("DivisonPorCero:" + "\n");
		codigo.append("invoke printf, addr mensajeError_DivisionCero"); //Debo cargar en la seccion de .data lo siguiente (mensaje_error_divisionCero DB "Error: Division por cero", 10, 0)
		codigo.append("JMP fin" + "\n");
	}
	/*
	public static boolean esConstanteLiteral(String operando) {
		Simbolo s = AnalizadorLexico.TablaDeSimbolos.get(operando);
		if (s.getTipo()==Parser.tipos.get("Entero") || s.getTipo()==Parser.tipos.get("Octal")) {
			return s.esEntero();
		}
		else if (s.getTipo()==Parser.tipos.get("Double")) {
			return s.esDouble();
		}
		return false;
	}*/
	
	public static String crearAuxiliar(Tipo tipo) {
		Simbolo simb = new Simbolo();
		simb.setTipoVar(tipo);
		simb.setUso("Var Aux");
		AnalizadorLexico.TablaDeSimbolos.put("@aux"+numAuxiliares,simb); //Agrego la nueva auxiliar a la TS
		numAuxiliares++;
		return("@aux"+(numAuxiliares-1));
	}
	
	
	
	public static StringBuilder generarData() {
		StringBuilder codigo = new StringBuilder();
		//System.out.println("Entro a GENERAR DATA \n");
		codigo.append(".data \n");
		for (Map.Entry<String, Simbolo> iterador : AnalizadorLexico.TablaDeSimbolos.entrySet()) {
			
			String lexema = iterador.getKey();
			//System.out.println("Lexema TS: " + lexema + "\n");
			Simbolo simbolo = iterador.getValue();
			//System.out.println("Simbolo TS: " + simbolo.toString() + "\n");
			//System.out.println("");
			Tipo tipo = simbolo.getTipoVar();
			if(tipo != null) {
				String tipoString = tipo.toString();
				switch (tipoString) {
					case "INTEGER":
						if (simbolo.esLiteral()) { //Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
							codigo.append(lexema + " DW " + simbolo.getEntero() + "\n");
						}
						else {
							codigo.append(lexema + " DW ?" + "\n");
						}
						break;
					case "DOUBLE":
						if (simbolo.esLiteral()) { //Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
							codigo.append(lexema + " DQ " + simbolo.getEntero() + "\n");
						}
						else {
							codigo.append(lexema + " DQ ?" + "\n");
						}
						break;
					case "OCTAL": //Me guio del octi DW 077o de test_print
						if (simbolo.esLiteral()) { //Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
							codigo.append(lexema + " DW " + simbolo.getEntero() + "\n");
						}
						else {
							codigo.append(lexema + " DW ?" + "\n");
						}
						break;
					case "STRING":
						codigo.append("");
						break;
					default: //Tipos no primitivos entrarían aquí, VER COMO PROCEDER
						break;
				}
			}
		}
		return codigo;
	}
	
	public static StringBuilder generarCode() {
		StringBuilder codigo = new StringBuilder();
		codigo.append(".code \n \n");
		//Key polaca main :MAIN
		//Cuando recorra el map de polacas en la parte de funciones tengo que agregar el sufijo:
		/*
		 * PUSH EBP
		 * MOV EBP, ESP
		 * SUB ESP, 4
		 * PUSH EDI
		 * PUSH ESI
		*/
		for (Map.Entry<String, ArrayList<String>> iterador : GeneradorCodigoIntermedio.polacaFuncional.entrySet()) {
			String ambito = iterador.getKey();
			ArrayList<String> polacaActual = iterador.getValue();
			
			if(ambito != ":MAIN") { //Genero el código de las polacas de funciones
				codigo.append(ambito + ": \n");
				codigo.append("PUSH EBP \n");
				codigo.append("MOV EBP, ESP \n");
				codigo.append("SUB ESP, 4 \n");
				codigo.append("PUSH EDI \n");
				codigo.append("PUSH ESI \n");
				codigo.append(recorrerPolaca(polacaActual) + "\n");
			}
		}
		//Luego de cargar las funciones en .code, recorro la polaca :MAIN
		codigo.append("start: \n");
		codigo.append(recorrerPolaca(GeneradorCodigoIntermedio.polacaFuncional.get(":MAIN")));
		codigo.append("fin: \n");
		codigo.append("invoke ExitProcess, 0 \n");
		codigo.append("end start");
		return codigo;
	}
	
	public static StringBuilder generarEncabezado() {
		StringBuilder codigo = new StringBuilder();
		//Copio el encabezado de funciones.asm dado por la catedra
		codigo.append(".586 \n");
		codigo.append(".model flat, stdcall \n \n");
		codigo.append("option casemap :none \n");
		codigo.append("include \\masm32\\include\\windows.inc \n");
		codigo.append("include \\masm32\\include\\kernel32.inc \n");
		codigo.append("include \\masm32\\include\\user32.inc \n");
		codigo.append("includelib \\masm32\\lib\\kernel32.lib \n");
		codigo.append("includelib \\masm32\\lib\\user32.lib \n \n");
		return codigo;
	}
	
	public static void generarPrograma() {
		StringBuilder codigo = new StringBuilder();
		StringBuilder seccionCode = new StringBuilder();
		seccionCode.append(generarCode()); //Genero primero el .code asi se carga correctamente la TS
		codigo.append(generarEncabezado());
		codigo.append(generarData() + "\n");
		codigo.append(seccionCode);

        try {
			CreacionDeSalidas.Assembler.write(codigo.toString());
			CreacionDeSalidas.Assembler.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}