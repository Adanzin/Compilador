package main.java.Compilador;

import java.util.Stack;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GeneradorCodigoAssembler {
	
	private static Stack<String> pila = new Stack<String>();
	
	private static int numAuxiliares = 1;
	
	private static String ultimaFuncion = null;
	private static String ultimaOperacion = null; //Se usa para debuggear nomas
	private static String ultimaTripla = null;
	private static int ultimoIndice = 0;
		
	public GeneradorCodigoAssembler() {
	}
	
	public static StringBuilder recorrerPolaca(ArrayList<String> polacaActual, String nombrePolaca) {
		StringBuilder codigo = new StringBuilder();
		String elemento;
		for (int i=0; i < polacaActual.size();i++) {
			elemento = polacaActual.get(i);
			switch (elemento) {
				case "+","-","*","/",":=":
					ultimaOperacion = elemento;
					operadorBinario(elemento, codigo);
					break;
				case "INTEGER", "DOUBLE", "OCTAL", "RET":
					ultimaOperacion = elemento;
					operadorUnario(elemento, codigo, nombrePolaca);
					break;
				case "CALL":
					ultimaOperacion = elemento;
					operadorFuncion(codigo);
					break;
				case "BF":
					ultimaOperacion = elemento;
					operadorSaltoCondicional(elemento, codigo, polacaActual.get(i-2));
					break;
				case "BI:":
					ultimaOperacion = elemento;
					operadorSaltoIncondicional(codigo);
					break;
				case "<",">","<=",">=","=", "!=":	
					ultimaOperacion = elemento;
					operadorComparacion(elemento, codigo);
					break;
				case "OUTF":
					ultimaOperacion = elemento;
					imprimirPorPantalla(codigo);
					break;
				case "PF":
					ultimaOperacion = elemento;
					operadorInicioFuncion(codigo);
					break;
				case "INDEX":
					ultimaOperacion = elemento;
					operadorIndiceTripla(codigo);
					break;
				default: //Entra si es un operando o si es un LABEL+N° que no puedo chequear en el CASE
					if(elemento.startsWith("LABEL")) { //Es una etiqueta
						ultimaOperacion = elemento;
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

	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------OPERACIONES-----------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------
	
	public static void operadorBinario(String operacion, StringBuilder codigo) {
		String operando2 = pila.pop();
		String operando1 = pila.pop();

		Simbolo simbOperando1 = Parser.getVariableFueraDeAmbito(operando1);
		Simbolo simbOperando2 = Parser.getVariableFueraDeAmbito(operando2);
		operando1 = simbOperando1.getId();
		operando2 = simbOperando2.getId();
		if (simbOperando1.sonCompatibles(simbOperando2)) {
			Tipo tipoOperando2 = simbOperando2.getTipo();
			Tipo tipoOperando1 = simbOperando1.getTipo();
			
			//CASO ASIGNACION ENTRE TRIPLAS
			if (tipoOperando2.esTripla()) {
				if (operacion == ":=") {
					if (tipoOperando2.getType().toString().contains("INTEGER") || tipoOperando2.getType().toString().contains("OCTAL")) {
						// Solo debe permitirse la asignacion y llamo al metodo para asignar a cada elemento el del indice correspondiente
						operacionEntreTriplasInteger(operando1, operando2, operacion, codigo, tipoOperando1,tipoOperando2);
					} else {
						operacionEntreTriplasFloat(operando1, operando2, operacion, codigo, tipoOperando1, tipoOperando2);
					}
				} 
				else {
					Parser.cargarErrorEImprimirlo("Error: Operacion incorrecta con triplas \n");
				}
			}
			
			//CASO ASIGNACION DE TIPO PRIMITIVO A UNA POSICION DE LA TRIPLA
			else if(!ultimaTripla.isBlank() && operacion==":=") { //Reviso que lo ultimo que se haya operado haya sido una tripla para ser asignada y que se trate de una asignacion
				operando1 = comprobarOperandoLiteral(operando1);
				if (tipoOperando2.getType().toString().contains("INTEGER") || tipoOperando2.getType().toString().contains("OCTAL")) {
					operacionAsignacionElementoTriplaInteger(operando1, codigo);
					ultimaTripla = "";
				}else {
					operacionAsignacionElementoTriplaFloat(operando1, codigo);
					ultimaTripla = "";
				}
			}
			
			//CASO OPERACION TIPOS PRIMITIVOS / SUBTIPOS
			else {
				operando1 = comprobarOperandoLiteral(operando1);
				operando2 = comprobarOperandoLiteral(operando2);
				if (tipoOperando2.getType() == "INTEGER" || tipoOperando2.getType() == "OCTAL") {
					operacionEnteroOctal(operando1, operando2, operacion, codigo, tipoOperando2);
				} 
				else {
					operacionDouble(operando1, operando2, operacion, codigo, tipoOperando2);
				}
			}
		} 
		else {
			try {
				AnalizadorLexico.sintactico.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(1); // Termino la ejecución del compilador por error en etapa de compilacion
		}
	}
	
	public static void operadorFuncion(StringBuilder codigo) {
		String operando1 = pila.pop(); //Es el nombre de la funcion
		String operando2 = pila.pop(); //Es el parametro real
		Simbolo simbOperando2 = Parser.getVariableFueraDeAmbito(operando2);
		if(simbOperando2.getTipo().getType()=="INTEGER" || simbOperando2.getTipo().getType()=="OCTAL") {
			codigo.append("MOV AX, " + operando2 + "\n");
			codigo.append("MOV @ParametroRealInt, AX \n");
		}
		else if(simbOperando2.getTipo().getType()=="DOUBLE") {
			codigo.append("FLD " + operando2 + "\n"); //Apilo el parametro real
			codigo.append("FSTP @ParametroRealFloat \n");
		}
		codigo.append("CALL " + AnalizadorLexico.TablaDeSimbolos.get(operando1).getAmbitoVar() + "\n");
		ultimaFuncion = AnalizadorLexico.TablaDeSimbolos.get(operando1).getAmbitoVar();
		
		String key="@RET"+ultimaFuncion;
		if(!AnalizadorLexico.TablaDeSimbolos.containsKey("@RET"+ultimaFuncion)) {
			key = crearAuxiliarRetornoFuncion(AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo());
		}
		pila.push(key); //Apilo el nombre de la variable que debera retornar la funcion		
	}
	
	public static void operadorSaltoCondicional(String elemento, StringBuilder codigo, String comparadorAnterior) {
		String operador = pila.pop(); // Es la direccion a saltar
		switch (comparadorAnterior) {
		// Le paso el operador anterior al salto para saber que comparación era y así usar el jump adecuado
			case ">":
				codigo.append("JLE LABEL" + operador + "\n \n"); // Salto en el caso contrario al de la comparacion
				break;
			case "<":
				codigo.append("JGE LABEL" + operador + "\n \n"); // Salto en el caso contrario al de la comparacion
				break;
			case ">=":
				codigo.append("JL LABEL" + operador + "\n \n"); // Salto en el caso contrario al de la comparacion
				break;
			case "<=":
				codigo.append("JG LABEL" + operador + "\n \n"); // Salto en el caso contrario al de la comparacion
				break;
			case "=":
				codigo.append("JNE LABEL" + operador + "\n \n"); // Salto en el caso contrario al de la comparacion
				break;
			case "!=":
				codigo.append("JE LABEL" + operador + "\n \n"); // Salto en el caso contrario al de la comparacion
				break;
			}
	}
	
	public static void operadorSaltoIncondicional(StringBuilder codigo) {
		String operador = pila.pop(); //Es la direccion a saltar
		codigo.append("JMP LABEL" + operador + "\n \n"); //Salto sí o sí a la etiqueta
	}
	
	public static void operacionEnteroOctal(String operando1, String operando2, String operacion, StringBuilder codigo, Tipo tipoOperando) {
		//Esta funcion usa el registro AX de 16 bits para enteros y octales.
		if(operacion!=":=") {
			codigo.append("MOV AX, " + operando1 + "\n"); 
		}
		switch (operacion) {
			case "+":
				codigo.append("ADD AX," + operando2 + "\n");
				
				break;
			case "-":
				codigo.append("SUB AX," + operando2 + "\n"); 
				break;
			case "*":
				codigo.append("IMUL " + operando2 + "\n");
				codigo.append("JO Overflow \n"); //Salto si se activo el flag de overflow
				break;
			case "/":
				codigo.append("MOV BX," + operando2 + "\n"); //Uso BX para no pisar AX con el operando1
				codigo.append("CMP BX" + ",0" + "\n");
				codigo.append("JE Divison_Por_Cero \n"); //JZ salta si la comparacion del operando2 con el cero es TRUE
				//Continua el flujo normal en caso de no saltar
				codigo.append("IDIV " + operando2 + "\n");
				break;
			case ":=":
				String auxOp = operando1;
				operando1 = operando2;
				operando2 = auxOp;
				//No andaba bien asi que los intercambie para que se haga correctamente
				codigo.append("MOV AX, " + operando2 + "\n");
				codigo.append("MOV " + operando1 + ", AX" + "\n");
				break;
			default:
				//Después veré pero acá no debería entrar nada
				break;
		}
		if(operacion!= ":=") {
			codigo.append("MOV @aux" + numAuxiliares + ",AX");
			pila.push(crearAuxiliar(tipoOperando));
		}
		codigo.append("\n \n");
	}
	
	public static void operacionDouble(String operando1, String operando2, String operacion, StringBuilder codigo, Tipo tipoOperando) {
		operando1 = convertirLexemaFlotante(operando1);
		operando2 = convertirLexemaFlotante(operando2);
		
		codigo.append("FLD " + operando2 + "\n"); //Apilo el operando2
		codigo.append("FLD " + operando1 + "\n"); //Apilo el operando1
		String aux = crearAuxiliar(tipoOperando);
		switch(operacion) {
			case "+":
				codigo.append("FADD" + "\n"); //ST(0) = ST(0) + ST(1)
				codigo.append("FSTP " + aux + "\n \n"); //Guardo el resultado en una auxiliar
				pila.push(aux);
				break;
			case "-":
				codigo.append("FSUB" + "\n"); //ST(0) = ST(0) - ST(1)
				codigo.append("FSTP " + aux + "\n \n"); //Guardo el resultado en una auxiliar
				pila.push(aux);
				break;
			case "*":
				codigo.append("FMUL" + "\n"); //ST(0) = ST(0) * ST(1)
				codigo.append("FSTP " + aux + "\n \n"); //Guardo el resultado en una auxiliar
				pila.push(aux);
				break;
			case "/":
				codigo.append("FXCH " + "\n"); //Intercambio operando1 y operando2 así queda operando2 en el tope y lo comparo despues
				codigo.append("FTST" + "\n"); //Comparo ST (operando2) con el cero
				codigo.append("FSTSW AX"  + "\n"); 
				codigo.append("SAHF \n");
				codigo.append("JZ Divison_Por_Cero \n");
				//-----------------------------------------------------------------
				codigo.append("FXCH \n"); //Vuelvo a intercambiar así realizo correctamente la operacion
				codigo.append("FDIV" + "\n"); //ST(0) = ST(0) / ST(1)
				codigo.append("FSTP " + aux + "\n \n"); //Guardo el resultado en una auxiliar
				pila.push(aux);
				break;
			case ":=":
				codigo.append("FSTP " + operando2 + "\n \n"); //Guardo el valor de operando2 en operando1 pero por cuestiones de como esta hecha la polaca lo escribo al reves
				break;
			default:
				//Acá no debería entrar nada
				break;
		}
	}
	
	public static void operadorUnario(String elemento, StringBuilder codigo, String nombrePolaca) {
		String operando = pila.pop();
		Simbolo simbOperando = Parser.getVariableFueraDeAmbito(operando);
		operando = simbOperando.getId();
		Tipo tipoOperando = AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo();
		operando = comprobarOperandoLiteral(operando);
		//Lo siguiente que hago es invertir nombrePolaca que en realidad es el ambito para que 
		//pase de ser, por ejemplo, $MAIN$nombrefun a nombrefun$MAIN$ y luego uso el metodo que lo busca en la TS por ambito
		Simbolo retorno = Parser.getVariableFueraDeAmbito(invertirAmbito(nombrePolaca));
		switch (elemento) {
			case "INTEGER", "OCTAL", "DOUBLE":
				operadorConversion(operando, elemento, codigo, tipoOperando);
				break;
			case "RET":
				if(retorno.sonCompatibles(simbOperando)) {
					System.out.println("nombre polaca: " + nombrePolaca);
					codigo.append("MOV AX, " + operando + "\n"); //Guardo la variable que quiero retornar en AX
					codigo.append("MOV @RET" + nombrePolaca + ", AX" + "\n");
					codigo.append("POP ESI \n");
					codigo.append("POP EDI \n");
					codigo.append("MOV ESP, EBP \n");
					codigo.append("POP EBP \n");
					codigo.append("RET" + "\n \n");
				}
				else {
					Parser.cargarErrorEImprimirlo("La variable del retorno de funcion debe ser " + retorno.getTipo().getType());
					try {
						AnalizadorLexico.sintactico.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(1); //Termino la ejecución del compilador por error en etapa de compilacion
				}
				break;
		}
	}
	
	public static void operadorConversion(String operando, String elemento, StringBuilder codigo, Tipo tipoOperando) {
		operando = comprobarOperandoLiteral(operando);
		switch (elemento) {
			case "DOUBLE":
				if (tipoOperando == Parser.tipos.get("INTEGER")||tipoOperando == Parser.tipos.get("OCTAL")) {
					//Conversion de Entero/Octal a Double
					codigo.append("FILD " + operando + "\n"); //Cargo el entero como double
					codigo.append("FSTP @aux" + numAuxiliares + "\n \n"); //Almaceno el resultado en @aux
					pila.push(crearAuxiliar(Parser.tipos.get("DOUBLE")));
				}
				break;
			case "INTEGER":
				if (tipoOperando == Parser.tipos.get("DOUBLE")) {
					//Conversion de Double a Entero
					codigo.append("FLD " + operando + "\n"); //Apilo en ST
					codigo.append("FISTP @aux" + numAuxiliares + "\n \n");
					pila.push(crearAuxiliar(Parser.tipos.get("INTEGER")));
				}
				break;
			default:
				break;	
		}
	}
	
	public static void operadorComparacion(String elemento, StringBuilder codigo) {
		String operando2 = pila.pop();
		String operando1 = pila.pop();
		Simbolo simbOperando1 = Parser.getVariableFueraDeAmbito(operando1);
		operando1 = simbOperando1.getId();
		Simbolo simbOperando2 = Parser.getVariableFueraDeAmbito(operando2);
		operando2 = simbOperando2.getId();
		if(	AnalizadorLexico.TablaDeSimbolos.get(operando1).sonCompatibles(AnalizadorLexico.TablaDeSimbolos.get(operando2))) {
			Tipo tipoOperando = AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo();
			if (tipoOperando.getType()=="INTEGER" || tipoOperando.getType()=="OCTAL") {
				operando1 = comprobarOperandoLiteral(operando1);
				operando2 = comprobarOperandoLiteral(operando2);
				codigo.append("MOV AX, " + operando1 + "\n");
				codigo.append("MOV BX, " + operando2 + "\n");
				codigo.append("CMP AX" + ",BX" + "\n \n"); //Comparo los operandos y el resultado va a afectar a los flags para los saltos
			} else if(tipoOperando.getType()=="DOUBLE") {
				operando1 = comprobarOperandoLiteral(operando1);
				operando2 = comprobarOperandoLiteral(operando2);
				operando1 = convertirLexemaFlotante(operando1); //Convierto a los lexema flotantes
				operando2 = convertirLexemaFlotante(operando2); //Convierto a los lexema flotantes
				codigo.append("FLD " + operando2 + "\n"); //Apilo el operando2
				codigo.append("FLD " + operando1 + "\n"); //Apilo el operando1
				codigo.append("FCOMPP"  + "\n"); //Compara los operandos extrayendo ambos operandos de la pila
				codigo.append("FSTSW AX \n");
				codigo.append("SAHF \n \n");
			}
		}
	}
	
	public static void operadorInicioFuncion(StringBuilder codigo) {
		String operando = pila.pop();
		codigo.append("PUSH EBP \n");
		codigo.append("MOV EBP, ESP \n");
		codigo.append("SUB ESP, 4 \n");
		codigo.append("PUSH EDI \n");
		codigo.append("PUSH ESI \n");
		if (AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().getType()=="INTEGER" || 
				AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().getType()=="OCTAL") {
			codigo.append("MOV AX, @ParametroRealInt \n");
			codigo.append("MOV " + operando + ", AX \n \n"); //Primero asigno el valor del ParametroRealInt cuando hago el CALL
		}
		else if (AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().getType()=="DOUBLE") {
			codigo.append("FSTP " + operando + "\n \n"); //Primero apilo el valor del ParametroRealFloat en ST cuando hago el CALL
		}
	}
	
	public static void operacionSubtipoIntegerOctal(String operando1, String operando2, String operacion, StringBuilder codigo, Tipo tipoOperando) {
		codigo.append("MOV ECX, OFFSET " + operando1 + "\n"); //Guardo la posicion inicial del primer elemento del operando1 (valor) 
		codigo.append("MOV EDX, OFFSET " + operando2 + "\n"); //Guardo la posicion inicial del primer elemento del operando1 (valor)
		codigo.append("MOV AX, [ECX]  \n"); //Guardo en AX el valor del primer elemento del operando1
		codigo.append("MOV BX, [EDX] \n"); //Guardo en BX el valor del primer elemento del operando2
		switch (operacion) {
			case "+":
				codigo.append("ADD AX, BX \n");
				codigo.append("CMP AX, [ECX + 2] \n"); //Me desplazo al segundo elemento (rango inferior) y comparo con el resultado
				codigo.append("JL Subtipo_inferior \n"); //Si es menor al mínimo entonces salta a la etiqueta correspondiente
				codigo.append("CMP AX, [ECX + 4] \n"); //Me desplazo al tercero elemento (rango superior) y comparo con el resultado
				codigo.append("JG Subtipo_superior \n"); //Si es mayor al máximo entonces salta a la etiqueta correspondiente
				break;
			case "-":
				codigo.append("SUB AX, BX \n"); 
				codigo.append("CMP AX, [ECX + 2] \n"); //Me desplazo al segundo elemento (rango inferior) y comparo con el resultado
				codigo.append("JL Subtipo_inferior \n"); //Si es menor al mínimo entonces salta a la etiqueta correspondiente
				codigo.append("CMP AX, [ECX + 4] \n"); //Me desplazo al tercero elemento (rango superior) y comparo con el resultado
				codigo.append("JG Subtipo_superior \n"); //Si es mayor al máximo entonces salta a la etiqueta correspondiente
				break;
			case "*":
				codigo.append("IMUL BX \n");
				codigo.append("CMP AX, [ECX + 2] \n"); //Me desplazo al segundo elemento (rango inferior) y comparo con el resultado
				codigo.append("JL Subtipo_inferior \n"); //Si es menor al mínimo entonces salta a la etiqueta correspondiente
				codigo.append("CMP AX, [ECX + 4] \n"); //Me desplazo al tercero elemento (rango superior) y comparo con el resultado
				codigo.append("JG Subtipo_superior \n"); //Si es mayor al máximo entonces salta a la etiqueta correspondiente
				break;
			case "/":
				codigo.append("MOV DX, 0 \n"); //Reseteo el valor de DX para evitar conflictos al hacer IDIV (DX:AX)
				codigo.append("CMP BX" + ",0" + "\n");
				codigo.append("JE Divison_Por_Cero \n"); //JZ salta si la comparacion del operando2 con el cero es TRUE
				//Continua el flujo normal en caso de no saltar
				codigo.append("IDIV BX \n");
				
				//Creo que en la division nunca va a existir el caso que por operar te vayas de rango en enteros
				/*
				codigo.append("CMP AX, [ECX + 2] \n"); //Me desplazo al segundo elemento (rango inferior) y comparo con el resultado
				codigo.append("JL Subtipo_inferior \n"); //Si es menor al mínimo entonces salta a la etiqueta correspondiente
				codigo.append("CMP AX, [ECX + 4] \n"); //Me desplazo al tercero elemento (rango superior) y comparo con el resultado
				codigo.append("JG Subtipo_superior \n"); //Si es mayor al máximo entonces salta a la etiqueta correspondiente
				*/
				break;
			case ":=":
				//Uso los registros al reves por la particularidad de los operandos en la asignación
				codigo.append("MOV [" + operando2 + "], AX \n"); //Guardo en la primera posicion (valor) el valor del operando
				break;
			default:
				//Después veré pero acá no debería entrar nada
				break;
		}
		if(operacion!= ":=") {
			codigo.append("MOV [@aux" + numAuxiliares + "], AX");
			pila.push(crearAuxiliarSubtipo(tipoOperando));
		}
		codigo.append("\n \n");
	}
	
	public static void operacionEntreTriplasInteger(String operando1, String operando2, String operacion, StringBuilder codigo, Tipo tipoOperando1, Tipo tipoOperando2) {
		codigo.append("MOV ECX, OFFSET " + operando1 + "\n"); //Guardo la posicion inicial del primer elemento del operando1 (valor) 
		codigo.append("MOV EDX, OFFSET " + operando2 + "\n"); //Guardo la posicion inicial del primer elemento del operando2 (valor)
		codigo.append("MOV AX, [ECX]  \n"); //Guardo en AX el valor del primer elemento del operando1
		codigo.append("MOV BX, [EDX] \n"); //Guardo en BX el valor del primer elemento del operando2
		
		codigo.append("MOV [" + operando2 + "], AX \n"); //Guardo la primera pos de operando1 en operando2
		
		codigo.append("MOV AX, [ECX + 2]  \n"); //Desplazo a la segunda pos
		codigo.append("MOV [" + operando2 + "+ 2], AX \n"); //Guardo en la segunda pos
		
		codigo.append("MOV AX, [ECX + 4]  \n"); //Desplazo a la tercera pos
		codigo.append("MOV [" + operando2 + "+ 4], AX \n"); //Guardo en la tercera pos
		
		codigo.append("\n \n");
	}

	
	public static void operacionSubtipoFloat(String operando1, String operando2, String operacion, StringBuilder codigo, Tipo tipoOperando) {
		
	}
	
	public static void operacionEntreTriplasFloat(String operando1, String operando2, String operacion, StringBuilder codigo, Tipo tipoOperando1, Tipo tipoOperando2) {
		codigo.append("MOV ECX, OFFSET " + operando1 + "\n"); //Guardo la posicion inicial del primer elemento del operando1 (valor)
		codigo.append("MOV EDX, OFFSET " + operando2 + "\n"); //Guardo la posicion inicial del primer elemento del operando2 (valor)
		
		codigo.append("FLD QWORD PTR [ECX] \n"); //Cargo en la pila el primer valor del operando a asignar
		codigo.append("FSTP QWORD PTR [EDX] \n"); //Guardo en el primer valor del asignado
		
		codigo.append("FLD QWORD PTR [ECX + 8] \n"); //Cargo en la pila el segundo valor del operando a asignar
		codigo.append("FSTP QWORD PTR [EDX + 8] \n"); //Guardo en el segundo valor del asignado
		
		codigo.append("FLD QWORD PTR [ECX + 16] \n"); //Cargo en la pila el tercer valor del operando a asignar
		codigo.append("FSTP QWORD PTR [EDX + 16] \n"); //Guardo en el tercer valor del asignado
		
		codigo.append("\n \n");
	}
	
	public static void operadorIndiceTripla(StringBuilder codigo) {
		String operando1 = pila.pop(); //Es el indice a acceder
		String operando2 = pila.pop(); //Es la variable
		
		Simbolo simbOperando2 = Parser.getVariableFueraDeAmbito(operando2);
		operando2 = simbOperando2.getId();
		int indice = 0;
		
		if(simbOperando2.getTipo().getType().equals("INTEGER")||simbOperando2.getTipo().getType().equals("OCTAL")) {
			if (operando1.equals("2")) {
				indice = 2;
			}
			if (operando1.equals("3")) {
				indice = 4;
			}
			codigo.append("MOV ECX, OFFSET " + operando2 + "\n");
			codigo.append("MOV AX, [ECX + " + indice + "] \n");
			codigo.append("MOV @aux" + numAuxiliares + ", AX \n \n");
		}else {
			operando2 = convertirLexemaFlotante(operando2);
			if (operando1.equals("2")) {
				indice = 8;
			}
			if (operando1.equals("3")) {
				indice = 16;
			}
			codigo.append("MOV ECX, OFFSET " + operando2 + "\n");
			codigo.append("FLD QWORD PTR [ECX + " + indice + "] \n");
			codigo.append("FSTP QWORD PTR @aux" + numAuxiliares +  "\n \n");
			
		}
		pila.push(crearAuxiliar(Parser.tipos.get(simbOperando2.getTipo().getType())));
		ultimaTripla = operando2; //Guardo el nombre de la variable triple para el caso de asignaciones futuras
		ultimoIndice = indice; //Guardo el indice para lo mismo
	}
	
	public static void operacionAsignacionElementoTriplaInteger(String operando1, StringBuilder codigo) {
		codigo.append("MOV ECX, OFFSET " + ultimaTripla + "\n"); //Guardo la posicion de inicio de la tripla a ser asignada
		codigo.append("MOV AX, " + operando1 + "\n");
		codigo.append("MOV [ECX + " + ultimoIndice + "], AX \n \n"); //Almaceno el valor del operando1 en la posicion adecuada
	}
	
	public static void operacionAsignacionElementoTriplaFloat(String operando1, StringBuilder codigo) {
		operando1 = convertirLexemaFlotante(operando1);
		codigo.append("MOV ECX, OFFSET " + ultimaTripla + "\n");
		codigo.append("FLD QWORD PTR " + operando1 + "\n");
		codigo.append("FSTP QWORD PTR [ECX + " + ultimoIndice + "] \n \n");
	}
	
	

	
	
	
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------
	//--------------------------------------------METODOS COMPLEMENTARIOS---------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------
	
	public static void imprimirPorPantalla(StringBuilder codigo) {
		String operando = pila.pop();
		Simbolo simbOperando = Parser.getVariableFueraDeAmbito(operando);
		
		if (simbOperando.getTipo().toString().contains("INTEGER") ||simbOperando.getTipo().toString().contains("OCTAL")) { //Tengo en cuenta primitivos y subtipos
			operando = simbOperando.getId();
			codigo.append("invoke printf, cfm$(\"%hi\\n\"), " + operando + "\n \n" ); //Copio el invoke de _inti del archivo de moodle
		}
		else if(simbOperando.getTipo().toString().contains("DOUBLE")) { //Tengo en cuenta primitivos y subtipos
			operando = simbOperando.getId();
			codigo.append("invoke printf, cfm$(\"%f\\n\"), " + operando + "\n \n" ); //Copio el invoke de _floati del archivo de moodle
		}
		else if(simbOperando.getTipo().toString()==Parser.tipos.get("CADENAMULTILINEA").toString()) {
			operando = convertirLexemaCadena(operando);
			codigo.append("");
			codigo.append("invoke MessageBox, NULL, addr " + operando + ", addr " + operando + ", MB_OK \n \n" );
		}
	}
	
	public static StringBuilder crearErrorDivisionPorCero() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Divison_Por_Cero:" + "\n");
		codigo.append("invoke MessageBox, NULL, addr Error_DivisionCero, addr Error_DivisionCero, MB_OK \n");
		codigo.append("JMP fin" + "\n");
		return codigo;
	}
	
	public static StringBuilder crearErrorOverflow() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Overflow:" + "\n");
		codigo.append("invoke MessageBox, NULL, addr Error_Overflow, addr Error_Overflow, MB_OK \n");
		codigo.append("JMP fin" + "\n");
		return codigo;
	}
	
	public static StringBuilder crearErrorSubtipoInferior() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Subtipo_inferior:" + "\n");
		codigo.append("invoke MessageBox, NULL, addr Error_Subtipo_inferior, addr Error_Subtipo_inferior, MB_OK \n");
		codigo.append("JMP fin" + "\n");
		return codigo;
	}
	
	public static StringBuilder crearErrorSubtipoSuperior() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Subtipo_superior:" + "\n");
		codigo.append("invoke MessageBox, NULL, addr Error_Subtipo_superior, addr Error_Subtipo_superior, MB_OK \n");
		codigo.append("JMP fin" + "\n");
		return codigo;
	}

	public static String crearAuxiliar(Tipo tipo) {
		Simbolo simb = new Simbolo();
		simb.setTipoVar(tipo);
		simb.setId("@aux" + numAuxiliares);
		simb.setUso("Var Aux");
		AnalizadorLexico.TablaDeSimbolos.put("@aux"+numAuxiliares,simb); //Agrego la nueva auxiliar a la TS
		numAuxiliares++;
		return("@aux"+(numAuxiliares-1));
	}
	
	public static void crearAuxiliarParametroReal(Tipo tipo) {
		Simbolo simb = new Simbolo();
		simb.setTipoVar(tipo);
		if(tipo.getType()=="INTEGER" || (tipo.getType()=="OCTAL")){
			simb.setId("@ParametroRealInt");
			simb.setUso("Var Aux ParametroRealInt");
			AnalizadorLexico.TablaDeSimbolos.put("@ParametroRealInt",simb);
		}
		else if(tipo.getType()=="DOUBLE") {
			simb.setId("@ParametroRealFloat");
			simb.setUso("Var Aux ParametroRealFloat");
			AnalizadorLexico.TablaDeSimbolos.put("@ParametroRealFloat",simb);
		}
	}
	
	public static String crearAuxiliarRetornoFuncion(Tipo tipo) {
		Simbolo simb = new Simbolo();
		simb.setId("@RET"+ultimaFuncion);
		simb.setUso("Var Aux Retorno Funcion");
		simb.setTipoVar(tipo);
		String key = "@RET"+ultimaFuncion;
		AnalizadorLexico.TablaDeSimbolos.put(key,simb);
		return key;
	}
	
	public static String comprobarOperandoLiteral(String operando) {
		if(AnalizadorLexico.TablaDeSimbolos.get(operando).esLiteral()) { 
			operando = convertirOperandoLiteral(operando);
		}
		return operando;
	}
	
	public static String convertirOperandoLiteral(String operando) {
		String tipoString = AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().toString();
		if (tipoString.contains("INTEGER")) {
			operando = "integer" + operando;
		}
		if (tipoString.contains("OCTAL")) {
			operando = "octi" + operando;
		}
		if (tipoString.contains("DOUBLE")) {
			operando = "float" + operando;
		}
		return operando;
	}
	
	public static String convertirLexemaFlotante(String operando) {
		//Convierto los '.' en @ para que el assembler me los reconozca y también elimino el simbolo + que podria darme problemas y es redundante y reemplazo el '-' por el '_'
		return operando.replace('.', '@').replace("+", "").replace('-', '_');
	}
	
	public static String convertirLexemaCadena(String operando) {
		//Convierto los '.' en @ para que el assembler me los reconozca y también elimino el simbolo + que podria darme problemas y es redundante y reemplazo el '-' por el '_'
		return operando.replace("\r", "").replace("\n", "_").replace('.', '@').replace("+", "").replace('-', '_').replace("[", "").replace("]", "").replaceAll("\\s+", "_");
	}
	
	public static String invertirAmbito(String operando) {
		String[] ambitos = operando.split("\\$"); //Recorto el operando y cargo el arreglo de String con los recortes
		
		StringBuilder resultado = new StringBuilder();
		for (int i=ambitos.length-1;i>=0;i--) {
			resultado.append(ambitos[i]); //Primero cargo el elemento del arreglo
			if (i>0) {
				resultado.append("$"); //Luego de haber cargado el elemento, concateno el $
			}
		}
		return resultado.toString(); //Devuelvo el toString.
	}
	
	public static String crearAuxiliarSubtipo(Tipo tipo) {
		Simbolo simb = new Simbolo();
		simb.setTipoVar(tipo);
		simb.setId("@aux" + numAuxiliares);
		simb.setUso("Var Aux Subtipo");
		AnalizadorLexico.TablaDeSimbolos.put("@auxSubtipo"+numAuxiliares,simb); //Agrego la nueva auxiliar a la TS
		numAuxiliares++;
		return("@auxSubtipo"+(numAuxiliares-1));
	}
	
	public static String crearAuxiliarTripla(Tipo tipo) {
		Simbolo simb = new Simbolo();
		simb.setTipoVar(tipo);
		simb.setId("@auxTripla" + numAuxiliares);
		simb.setUso("Var Aux Tripla");
		AnalizadorLexico.TablaDeSimbolos.put("@auxTripla"+numAuxiliares,simb); //Agrego la nueva auxiliar a la TS
		numAuxiliares++;
		return("@auxTripla"+(numAuxiliares-1));
	}
	
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------GENERACION TEMPLATE ASSEMBLER PENTIUM----------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------
	
	public static StringBuilder generarData() {
		StringBuilder codigo = new StringBuilder();
		codigo.append(".data \n");
		codigo.append("Error_DivisionCero DB \"Error: Division por cero\", 10, 0 \n");
		codigo.append("Error_Overflow DB \"Error: Overflow en producto entre Enteros\", 10, 0 \n");
		codigo.append("Error_Subtipo_inferior DB \"Error: Valor menor al rango inferior del subtipo \", 10, 0 \n");
		codigo.append("Error_Subtipo_superior DB \"Error: Valor mayor al limite superior del subtipo \", 10, 0 \n");
		codigo.append("ESPERAR_ACCION_USUARIO DB \"Haga click en ACEPTAR para cerrar el programa y la consola\", 10, 0 \n");
		for (Map.Entry<String, Simbolo> iterador : AnalizadorLexico.TablaDeSimbolos.entrySet()) {
			String lexema = iterador.getKey();
			Simbolo simbolo = iterador.getValue();
			Tipo tipo = simbolo.getTipoVar();
			if (tipo != null) {
				String tipoString = tipo.toString();
				if (tipoString.contains("CADENAMULTILINEA")) {
					String lexemaConvertido = convertirLexemaCadena(lexema);
					if (codigo.indexOf(lexemaConvertido) == -1) {
						codigo.append(lexemaConvertido).append(" db \"").append(
								lexema.replace("\n", " ").replace("[", "").replace("]", "").replaceAll("\\s+", " ").trim())
								.append("\", 0 \n"); // Lo hago de esta manera para poder guardar el lexema entre comillas
					}
				}else if (!tipoString.contains("ETIQUETA")) { //Si no es una cadena multilinea entonces modularizo
					generarDataTipos(codigo, tipo, lexema, simbolo);
				}		
			}
		}
		return codigo;
	}
	
	public static void generarDataTipos(StringBuilder codigo, Tipo tipo, String lexema, Simbolo simbolo) {
		String tipoString = tipo.toString();
		String tipoDatoAssembler = null;
		String prefijoNombre = null;
		if (tipoString.contains("INTEGER")) {
			prefijoNombre = "integer";
			tipoDatoAssembler = "DW";
		} else if (tipoString.contains("OCTAL")) {
			prefijoNombre = "octi";
			tipoDatoAssembler = "DW";
		} else if (tipoString.contains("DOUBLE")) {
			prefijoNombre = "float";
			tipoDatoAssembler = "DQ";
		}
		if (!tipo.esTripla()) { // Las triplas no pueden ser literales
			if (simbolo.esLiteral()) { // Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
				if (codigo.indexOf(prefijoNombre + lexema + " " + tipoDatoAssembler) == -1) { // Me fijo si en el StringBuilder ya existe algun substring que sea intNumero DW. Si devuelve -1 entonces lo inserto
					
					if (tipoString.contains("DOUBLE")) {
						codigo.append(convertirLexemaFlotante(prefijoNombre + lexema) + " " + tipoDatoAssembler + " " + simbolo.getDoub() + "\n");
					}else {
						codigo.append(prefijoNombre + lexema + " " + tipoDatoAssembler + " " + simbolo.getEntero() + "\n");
					}
				}
			} else {
				codigo.append(lexema + " " + tipoDatoAssembler + " ?" + "\n");
			}
		} else {
			codigo.append(lexema + " " + tipoDatoAssembler + " ?, ?, ?" + "\n");
		}
	}
	
	
	public static StringBuilder generarCode() {
		Tipo tipoINTEGER = new Tipo("INTEGER");
		Tipo tipoDOUBLE = new Tipo("DOUBLE");
		crearAuxiliarParametroReal(tipoINTEGER);
		crearAuxiliarParametroReal(tipoDOUBLE);
		StringBuilder codigo = new StringBuilder();
		codigo.append(".code \n \n");
		//Key polaca main $MAIN
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
			
			if(ambito != "$MAIN") { //Genero el código de las polacas de funciones
				codigo.append(ambito + ": \n");
				
				codigo.append(recorrerPolaca(polacaActual, ambito) + "\n");
			}
		}
		//Luego de cargar las funciones en .code, recorro la polaca $MAIN
		codigo.append("start: \n");
		codigo.append(recorrerPolaca(GeneradorCodigoIntermedio.polacaFuncional.get("$MAIN"),"$MAIN"));
		codigo.append("JMP fin \n \n");
		codigo.append(crearErrorDivisionPorCero() + "\n");
		codigo.append(crearErrorOverflow() + "\n");
		codigo.append(crearErrorSubtipoInferior() + "\n");
		codigo.append(crearErrorSubtipoSuperior() + "\n");
		codigo.append("fin: \n");
		codigo.append("invoke MessageBox, NULL, addr ESPERAR_ACCION_USUARIO, addr ESPERAR_ACCION_USUARIO, MB_OK \n");
		codigo.append("invoke ExitProcess, 0 \n");
		codigo.append("end start");
		return codigo;
	}
	
	public static StringBuilder generarEncabezado() {
		StringBuilder codigo = new StringBuilder();
		//Copio el encabezado de funciones.asm dado por la catedra
		codigo.append(".586 \n");
		//codigo.append(".model flat, stdcall \n \n"); //masm32rt.inc lo hace automaticamente
		codigo.append("option casemap :none \n");
        codigo.append("include \\masm32\\include\\masm32rt.inc \n");
        codigo.append("includelib \\masm32\\lib\\kernel32.lib \n");
        codigo.append("includelib \\masm32\\lib\\user32.lib \n");
        codigo.append("includelib \\masm32\\lib\\masm32.lib \n");
		codigo.append("\ndll_dllcrt0 PROTO C" + "\n");
		codigo.append("printf PROTO C : VARARG \n");
		codigo.append("\n");
		return codigo;
		
		
	}
	
	public static void generarPrograma() {
		StringBuilder codigo = new StringBuilder();
		StringBuilder seccionCode = new StringBuilder();
		seccionCode.append(generarCode()); //Genero primero el .code asi se carga correctamente la TS
		codigo.append(generarEncabezado());
		codigo.append(generarData() + "\n");
		codigo.append(seccionCode);
		
		CreacionDeSalidas.writeAssembler(codigo.toString());
		
	}
}
