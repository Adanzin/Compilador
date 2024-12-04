package main.java.Compilador;

import java.util.Stack;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GeneradorCodigoAssembler {
	
	private static Stack<String> pila = new Stack<String>();
	
	private static int numAuxiliares = 1;
	
	private static String ultimaFuncion = "";
	private static String ultimaOperacion = ""; //Se usa para debuggear nomas
	private static String ultimaTripla = "";
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
				case "BI":
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
						ultimaTripla = "";
					} else {
						operacionEntreTriplasFloat(operando1, operando2, operacion, codigo, tipoOperando1, tipoOperando2);
						codigo.append("FINIT \n \n"); //Siempre vacio la pila al finalizar para evitar errores de ejecucion
						ultimaTripla = "";
					}
				} 
				else {
					Parser.cargarErrorEImprimirlo("Error: Operacion incorrecta con triplas \n");
					try {
						AnalizadorLexico.sintactico.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(1); //Termino la ejecución del compilador por error en etapa de compilacion
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
					codigo.append("FINIT \n \n"); //Siempre vacio la pila al finalizar para evitar errores de ejecucion
					ultimaTripla = "";
				}
			}
			
			//CASO OPERACION TIPOS PRIMITIVOS / SUBTIPOS
			else {
				operando1 = comprobarOperandoLiteral(operando1);
				operando2 = comprobarOperandoLiteral(operando2);
				if (tipoOperando2.getType() == "INTEGER" || tipoOperando2.getType() == "OCTAL") {
					operacionEnteroOctal(operando1, operando2, operacion, codigo, tipoOperando2);
					ultimaTripla = "";
				} 
				else {
					operacionDouble(operando1, operando2, operacion, codigo, tipoOperando2);
					codigo.append("FINIT \n \n"); //Siempre vacio la pila al finalizar para evitar errores de ejecucion
					ultimaTripla = "";
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
		Simbolo simbOperando1 = Parser.getVariableFueraDeAmbito(operando1);
		if(simbOperando1.getTipoParFormal()==simbOperando2.getTipo().getType()){
			if(simbOperando2.getTipo().getType()=="INTEGER" || simbOperando2.getTipo().getType()=="OCTAL") {
				codigo.append("MOV AX, " + operando2 + "\n");
				codigo.append("MOV @ParametroRealInt, AX \n");
			}
			else if(simbOperando2.getTipo().getType()=="DOUBLE") {
				codigo.append("FLD " + operando2 + "\n"); //Apilo el parametro real
				codigo.append("FSTP @ParametroRealFloat \n");
				
				//-------------------------------------------------------------------
				codigo.append("FINIT \n");
				codigo.append("FLD @ParametroRealFloat \n"); //
			}
		}else {
			Parser.cargarErrorEImprimirlo("Error: El parámetro real y formal tienen tipos incompatiables en la funcion " + operando1 + "\n");
			try {
				AnalizadorLexico.sintactico.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(1); //Termino la ejecución del compilador por error en etapa de compilacion
		}
				
		codigo.append("CALL " + simbOperando1.getAmbitoVar() + "\n");
		ultimaFuncion = simbOperando1.getAmbitoVar();
		
		String key="@RET"+ultimaFuncion;
		if(!AnalizadorLexico.TablaDeSimbolos.containsKey("@RET"+ultimaFuncion)) {
			key = crearAuxiliarRetornoFuncion(simbOperando1.getTipo());
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
				codigo.append("JAE LABEL" + operador + "\n \n"); // Salto en el caso contrario al de la comparacion
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
		StringBuilder chequeoSubtipo = new StringBuilder();
		if(operacion!=":=") {
			codigo.append("MOV AX, " + operando1 + "\n"); 
		}
		if(tipoOperando.esSubTipo()) {
			crearAuxiliarSubtipoInferior(AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo(), AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo());
			crearAuxiliarSubtipoSuperior(AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo(), AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo());
			chequeoSubtipo.append("CMP AX, @auxSubtipoInferior" + AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo() + "\n"); 
			chequeoSubtipo.append("JL Subtipo_inferior \n");
			chequeoSubtipo.append("CMP AX, @auxSubtipoSuperior" + AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo() + "\n"); 
			chequeoSubtipo.append("JG Subtipo_superior \n");
		}
		
		switch (operacion) {
			case "+":
				codigo.append("ADD AX," + operando2 + "\n");
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequeoSubtipo);
				}
				break;
			case "-":
				codigo.append("SUB AX," + operando2 + "\n"); 
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequeoSubtipo);
				}
				break;
			case "*":
				codigo.append("IMUL " + operando2 + "\n");
				codigo.append("JO Overflow \n"); //Salto si se activo el flag de overflow
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequeoSubtipo);
				}
				break;
			case "/":
				codigo.append("MOV BX," + operando2 + "\n"); //Uso BX para no pisar AX con el operando1
				codigo.append("CMP BX" + ",0" + "\n");
				codigo.append("JE Divison_Por_Cero \n"); //JZ salta si la comparacion del operando2 con el cero es TRUE
				//Continua el flujo normal en caso de no saltar
				
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequeoSubtipo);
				}
				codigo.append("IDIV " + operando2 + "\n");
				break;
			case ":=":
				String auxOp = operando1;
				operando1 = operando2;
				operando2 = auxOp;
				//No andaba bien asi que los intercambie para que se haga correctamente
				codigo.append("MOV AX, " + operando2 + "\n");
				
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequeoSubtipo);
				}
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
		String aux = crearAuxiliar(tipoOperando);
		
		//Ejecucion normal
		codigo.append("FLD " + operando1 + "\n"); //Apilo el operando1
		codigo.append("FLD " + operando2 + "\n"); //Apilo el operando2
		switch(operacion) {
			case "+":
				codigo.append("FADD" + "\n"); //ST(0) = ST(1) + ST(0)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequearRangosSubtipoDouble(aux, operando1));
				}
				pila.push(aux);
				break;
			case "-":
				codigo.append("FSUB" + "\n"); //ST(0) = ST(1) - ST(0)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequearRangosSubtipoDouble(operando1, aux));
				}
				pila.push(aux);
				break;
			case "*":
				codigo.append("FMUL" + "\n"); //ST(0) = ST(1) * ST(0)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequearRangosSubtipoDouble(aux, operando1));
				}
				pila.push(aux);
				break;
			case "/":
				codigo.append("FTST" + "\n"); //Comparo ST (operando2) con el cero
				codigo.append("FSTSW AX"  + "\n"); 
				codigo.append("SAHF \n");
				codigo.append("JZ Divison_Por_Cero \n");
				//-----------------------------------------------------------------
				codigo.append("FDIV" + "\n"); //ST(0) = ST(1) / ST(0)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequearRangosSubtipoDouble(aux, operando1));
				}
				pila.push(aux);
				break;
			case ":=":
				codigo.append("FXCH \n"); //Intercambio así realizo correctamente la operacion
				codigo.append("FSTP " + operando2 + "\n"); //Guardo el valor de operando2 en operando1 pero por cuestiones de como esta hecha la polaca lo escribo al reves
				
				if(tipoOperando.esSubTipo()) {
					codigo.append(chequearRangosSubtipoDouble(operando1, operando2)); //Los cargo al reves por ser asignacion
				}
				
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
		switch (elemento) {
			case "INTEGER", "OCTAL", "DOUBLE":
				operadorConversion(operando, elemento, codigo, tipoOperando);
				break;
			case "RET":
				//Lo siguiente que hago es invertir nombrePolaca que en realidad es el ambito para que 
				//pase de ser, por ejemplo, $MAIN$nombrefun a nombrefun$MAIN$ y luego uso el metodo que lo busca en la TS por ambito
				Simbolo retorno = Parser.getVariableFueraDeAmbito(invertirAmbito(nombrePolaca));
				
				if(!AnalizadorLexico.TablaDeSimbolos.containsKey("@RET"+nombrePolaca)) {
					Simbolo simb = new Simbolo();
					simb.setId("@RET"+nombrePolaca);
					simb.setUso("Var Aux Retorno Funcion");
					simb.setTipoVar(simbOperando.getTipo());
					String key = "@RET"+nombrePolaca;
					AnalizadorLexico.TablaDeSimbolos.put(key,simb);
				}
				
				System.out.println(simbOperando);
				
				if(retorno.sonCompatibles(simbOperando)) {
					if(retorno.getTipo().getType().contains("INTEGER")||retorno.getTipo().getType().contains("OCTAL")) {
						codigo.append("MOV AX, " + operando + "\n"); //Guardo la variable que quiero retornar en AX
						codigo.append("MOV @RET" + nombrePolaca + ", AX" + "\n");
					}else {
						codigo.append("FLD operando \n");
						codigo.append("FSTP @RET" + nombrePolaca + "\n");
					}
					
					codigo.append("POP ESI \n");
					codigo.append("POP EDI \n");
					codigo.append("MOV ESP, EBP \n");
					codigo.append("POP EBP \n");
					codigo.append("RET" + "\n \n");
				}
				else {
					Parser.cargarErrorEImprimirlo("La variable del retorno de funcion debe ser " + retorno.getTipo());
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
				if (tipoOperando.getType().contains("INTEGER")||tipoOperando.getType().contains("OCTAL")) {
					//Conversion de Entero/Octal a Double
					String aux = crearAuxiliar(Parser.tipos.get("DOUBLE"));
					codigo.append("MOVZX EAX, WORD PTR " + operando + " \n");
					codigo.append("PUSH EAX \n");
					codigo.append("FILD DWORD PTR [ESP] \n"); //Cargo el entero como double
					codigo.append("ADD ESP, 4 \n");
					codigo.append("FSTP QWORD PTR [@aux" + numAuxiliares + "] \n \n"); //Almaceno el resultado en @aux
					pila.push(crearAuxiliar(Parser.tipos.get("DOUBLE")));
				}
				break;
			case "INTEGER":
				if (tipoOperando.getType().contains("DOUBLE")) {
					//Conversion de Double a Entero
					codigo.append("FLD QWORD PTR " + operando + " \n"); //Apilo en ST
					codigo.append("FISTP WORD PTR @aux" + numAuxiliares + " \n \n");
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
				codigo.append("SAHF \n ");
				codigo.append("FINIT \n \n"); //Siempre vacio la pila al finalizar para evitar errores de ejecucion
			}
		}
	}
	
	public static void operadorInicioFuncion(StringBuilder codigo) {
		String operando = pila.pop(); //Es el parametro formal
		codigo.append("PUSH EBP \n");
		codigo.append("MOV EBP, ESP \n");
		codigo.append("SUB ESP, 4 \n");
		codigo.append("PUSH EDI \n");
		codigo.append("PUSH ESI \n");
		//ParametroRealInt
		//ParametroRealFloat
		if (AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().getType()=="INTEGER" || 
				AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().getType()=="OCTAL") {
			codigo.append("MOV AX, @ParametroRealInt \n");
			codigo.append("MOV " + operando + ", AX \n \n"); //Primero asigno el valor del ParametroRealInt cuando hago el CALL
		}
		else if (AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().getType()=="DOUBLE") {
			codigo.append("FSTP " + operando + "\n \n"); //Primero apilo el valor del ParametroRealFloat en ST cuando hago el CALL
		}
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
	
	public static void operacionEntreTriplasFloat(String operando1, String operando2, String operacion, StringBuilder codigo, Tipo tipoOperando1, Tipo tipoOperando2) {
		codigo.append("MOV ECX, OFFSET " + operando1 + "\n"); //Guardo la posicion inicial del primer elemento del operando1 (valor)
		codigo.append("MOV EDX, OFFSET " + operando2 + "\n"); //Guardo la posicion inicial del primer elemento del operando2 (valor)
		
		codigo.append("FLD QWORD PTR [ECX] \n"); //Cargo en la pila el primer valor del operando a asignar
		codigo.append("FSTP QWORD PTR [EDX] \n"); //Guardo en el primer valor del asignado
		
		codigo.append("FLD QWORD PTR [ECX + 8] \n"); //Cargo en la pila el segundo valor del operando a asignar
		codigo.append("FSTP QWORD PTR [EDX + 8] \n"); //Guardo en el segundo valor del asignado
		
		codigo.append("FLD QWORD PTR [ECX + 16] \n"); //Cargo en la pila el tercer valor del operando a asignar
		codigo.append("FSTP QWORD PTR [EDX + 16] \n"); //Guardo en el tercer valor del asignado
		
		codigo.append("\n");
	}
	
	public static void operadorIndiceTripla(StringBuilder codigo) {
		String operando1 = pila.pop(); //Es el indice a acceder
		String operando2 = pila.pop(); //Es la variable
		
		
		Simbolo simbOperando2 = Parser.getVariableFueraDeAmbito(operando2);
		operando2 = simbOperando2.getId();
		int indice = 0;
		
		if(simbOperando2.getTipo().getType().equals("INTEGER")||simbOperando2.getTipo().getType().equals("OCTAL")) {
			if(operando1.equals("1")) {
				indice = 0;
			} else if (operando1.equals("2")) {
				indice = 2;
			}else if (operando1.equals("3")) {
				indice = 4;
			}else {
				Parser.cargarErrorEImprimirlo("SE INTENTO ACCEDER A UNA POSICION INCORRECTA DE LA TRIPLA");
				try {
					AnalizadorLexico.sintactico.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(1); //Termino la ejecución del compilador por error en etapa de compilacion
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
		codigo.append("FSTP QWORD PTR [ECX + " + ultimoIndice + "] \n");
	}
	
	

	
	
	
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------
	//--------------------------------------------METODOS COMPLEMENTARIOS---------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------
	
	public static void imprimirPorPantalla(StringBuilder codigo) {
		String operando = pila.pop();
		Simbolo simbOperando = Parser.getVariableFueraDeAmbito(operando);
		
		if (simbOperando.getTipo().esTripla()) {
			operando = simbOperando.getId();
			if (simbOperando.getTipo().toString().contains("INTEGER") ||simbOperando.getTipo().toString().contains("OCTAL")) {
				codigo.append("MOVZX EAX, WORD PTR [" + operando + " + 4] \n");
				codigo.append("PUSH EAX \n");
				codigo.append("MOVZX EAX, WORD PTR [" + operando + " + 2] \n");
				codigo.append("PUSH EAX \n");
				codigo.append("MOVZX EAX, WORD PTR [" + operando + " + 0] \n");
				codigo.append("PUSH EAX \n");
				codigo.append("PUSH OFFSET $_$mensajeEntero$_$ \n");
				codigo.append("CALL printf \n");
				codigo.append("ADD ESP, 16 \n \n");
				//codigo.append("invoke printf, addr $_$mensajeEntero$_$, DWORD PTR [" + operando + "], DWORD PTR ["+ operando + " + 2], DWORD PTR [" + operando + " + 4] \n \n");
			} 
			else if(simbOperando.getTipo().toString().contains("DOUBLE")) {
				/*
				codigo.append("FLD QWORD PTR [" + operando + " + 16] \n");
				codigo.append("SUB ESP, 8 \n");
				codigo.append("FSTP QWORD PTR [ESP] \n");
				codigo.append("FLD QWORD PTR [" + operando + " + 8] \n");
				codigo.append("SUB ESP, 8 \n");
				codigo.append("FSTP QWORD PTR [ESP] \n");
				codigo.append("FLD QWORD PTR [" + operando + " + 0] \n");
				codigo.append("SUB ESP, 8 \n");
				codigo.append("FSTP QWORD PTR [ESP] \n");
				codigo.append("PUSH OFFSET $_$mensajeFloat$_$ \n");
				codigo.append("CALL printf \n");
				codigo.append("ADD ESP, 24 \n \n");*/
				codigo.append("invoke printf, addr $_$mensajeFloat$_$, QWORD PTR [" + operando + "], QWORD PTR ["+ operando + " + 8], "
						+ "QWORD PTR [" + operando + " + 16] \n \n");

			}
		}
		else if (simbOperando.getTipo().toString().contains("INTEGER") ||simbOperando.getTipo().toString().contains("OCTAL")) { //Tengo en cuenta primitivos y subtipos
			operando = simbOperando.getId();
			codigo.append("invoke printf, cfm$(\"%hi\\n\"), " + operando + "\n \n" ); //Copio el invoke de _inti del archivo de moodle
		}
		else if(simbOperando.getTipo().toString().contains("DOUBLE")) { //Tengo en cuenta primitivos y subtipos
			operando = simbOperando.getId();
			codigo.append("invoke printf, cfm$(\"%.20Lf\\n\"), " + operando + "\n \n" ); //Copio el invoke de _floati del archivo de moodle
		}
		else if(simbOperando.getTipo().toString()==Parser.tipos.get("CADENAMULTILINEA").toString()) {
			operando = convertirLexemaCadena(operando);
			codigo.append("");
			codigo.append("invoke printf, addr " + operando + " \n \n" );
		}
	}
	
	public static StringBuilder crearErrorDivisionPorCero() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Divison_Por_Cero:" + "\n");
		codigo.append("invoke printf, addr Error_DivisionCero \n");
		codigo.append("JMP fin" + "\n");
		return codigo;
	}
	
	public static StringBuilder crearErrorOverflow() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Overflow:" + "\n");
		codigo.append("invoke printf, addr Error_Overflow \n");
		codigo.append("JMP fin" + "\n");
		return codigo;
	}
	
	public static StringBuilder crearErrorSubtipoInferior() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Subtipo_inferior:" + "\n");
		codigo.append("invoke printf, addr Error_Subtipo_inferior \n");
		codigo.append("JMP fin" + "\n");
		return codigo;
	}
	
	public static StringBuilder crearErrorSubtipoSuperior() {
		StringBuilder codigo = new StringBuilder();
		codigo.append("Subtipo_superior:" + "\n");
		codigo.append("invoke printf, addr Error_Subtipo_superior \n");
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
	    // Eliminar caracteres problemáticos y reemplazarlos por nombres descriptivos
	    operando = operando
	        .replace("\r", "")                  
	        .replace("\n", "_")                 
	        .replace(":", "DOSPUNTOS") 
	        .replace("\"", "COMILLA") 
	        .replace("<", "MENOR")              
	        .replace(">", "MAYOR")              
	        .replace("!", "DISTINTO")           
	        .replace("=", "IGUAL")              
	        .replace('.', '@')                  
	        .replace("+", "SUMA")               
	        .replace('-', '_')                  
	        .replace("*", "MULTIPLICACION")     
	        .replace("/", "DIVISION")           
	        .replace("%", "MODULO")             
	        .replace("[", "")                   
	        .replace("]", "")                   
	        .replace("(", "PARENIZQ")           
	        .replace(")", "PARENDER")           
	        .replace("{", "LLAVEIZQ")           
	        .replace("}", "LLAVEDER")           
	        .replaceAll("\\s+", "_");

	    // Agregar un guion bajo al inicio para evitar errores con nombres que inician con un número
	    return "_" + operando;
	}

	
	public static String invertirAmbito(String operando) {
	    // Divide la cadena por el símbolo '$'
	    String[] ambitos = operando.split("\\$");
	    // Si hay menos de dos elementos, devolver el operando sin cambios
	    if (ambitos.length < 2) {
	        return operando;
	    }
	    String ultimoElemento = ambitos[ambitos.length - 1];
	    StringBuilder resultado = new StringBuilder(ultimoElemento);
	    // Agrega el resto de los elementos, precedidos por '$'
	    for (int i = 0; i < ambitos.length - 1; i++) {
	        if (!ambitos[i].isEmpty()) { // Evitar dobles $
	            resultado.append("$").append(ambitos[i]);
	        }
	    }
	    return resultado.toString();
	}
	
	public static String crearAuxiliarSubtipoInferior(Tipo tipo, String operando) {
		Simbolo simb = new Simbolo();
		simb.setTipoVar(tipo);
		simb.setId("@auxSubtipoInferior" + operando);
		simb.setUso("Var Aux Subtipo Inferior");
		AnalizadorLexico.TablaDeSimbolos.put("@auxSubtipoInferior"+operando,simb); //Agrego la nueva auxiliar a la TS
		return("@auxSubtipoInferior"+operando);
	}
	public static String crearAuxiliarSubtipoSuperior(Tipo tipo, String operando) {
		Simbolo simb = new Simbolo();
		simb.setTipoVar(tipo);
		simb.setId("@auxSubtipoSuperior" + operando);
		simb.setUso("Var Aux Subtipo Superior");
		AnalizadorLexico.TablaDeSimbolos.put("@auxSubtipoSuperior"+operando,simb); //Agrego la nueva auxiliar a la TS
		return("@auxSubtipoSuperior"+operando);
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
	
	public static StringBuilder chequearRangosSubtipoDouble(String operando1, String operando2) {
		crearAuxiliarSubtipoInferior(AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo(), AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo()); 
		crearAuxiliarSubtipoSuperior(AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo(), AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo());
		
		StringBuilder chequeoSubtipo = new StringBuilder();
		
		//Chequeo rango inferior
		chequeoSubtipo.append("FLD @auxSubtipoInferior" + AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo() + "\n"); //Apilo el auxiliarSubtipoInferior
		chequeoSubtipo.append("FLD " + operando1 + "\n"); //Apilo el valor del operando a asignar
		chequeoSubtipo.append("FCOMPP \n"); //Compara los operandos extrayendo ambos operandos de la pila
		chequeoSubtipo.append("FSTSW AX \n");
		chequeoSubtipo.append("SAHF \n");
		chequeoSubtipo.append("JB Subtipo_inferior \n"); //Uso JB Jump Below porque JL no funcionaba bien
		
		//Chequeo rango superior
		chequeoSubtipo.append("FLD @auxSubtipoSuperior" + AnalizadorLexico.TablaDeSimbolos.get(operando2).getTipo().getNombreSubtipo() + "\n"); //Apilo el auxiliarSubtipoInferior
		chequeoSubtipo.append("FLD " + operando1 + "\n"); //Apilo el valor del operando a asignar
		chequeoSubtipo.append("FCOMPP \n"); //Compara los operandos extrayendo ambos operandos de la pila
		chequeoSubtipo.append("FSTSW AX \n");
		chequeoSubtipo.append("SAHF \n");
		chequeoSubtipo.append("JA Subtipo_superior \n"); //Uso JA Jump Above porque JG no funcionaba bien
		
		return chequeoSubtipo;
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
		codigo.append("$_FIN_DE_PROGRAMA_$ DB \"FIN DE LA EJECUCION DEL PROGRAMA \", 10, 0 \n");
		codigo.append("$_$mensajeEntero$_$ DB \"%d, %d, %d\", 10, 0 \n");
		codigo.append("$_$mensajeFloat$_$ DB \"%f, %f, %f\", 10, 0 \n");
		for (Map.Entry<String, Simbolo> iterador : AnalizadorLexico.TablaDeSimbolos.entrySet()) {
			String lexema = iterador.getKey();
			Simbolo simbolo = iterador.getValue();
			Tipo tipo = simbolo.getTipoVar();
			if (tipo != null) {
				String tipoString = tipo.toString();
				if (tipoString.contains("CADENAMULTILINEA")) {
					String lexemaConvertido = convertirLexemaCadena(lexema);
					if (codigo.indexOf(lexemaConvertido + " db") == -1) {
						codigo.append(lexemaConvertido).append(" db \"").append(
								//--------------------------------------------------------------------------------
								lexema.replace("\"", "\", 22h, \"").replace("\n", " ").replace("[", "").replace("]", "").replaceAll("\\s+", " ").trim())
								.append("\", 10, 0 \n"); // Lo hago de esta manera para poder guardar el lexema entre comillas
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
			
			//Me fijo si se trata de un auxiliar de subtipo inferior
			if(lexema.contains("@auxSubtipoInferior")) { //Me fijo si se trata de un auxiliar de subtipo inferior
				String variable = lexema.substring(19); //Recorto la longitud del prefijo asi me quedo con el nombre de la variable a la que corresponde el auxiliar
				Simbolo simbVariable = AnalizadorLexico.TablaDeSimbolos.get(variable); //Busco la variable en la TS así accedo al valor de los rangos
				if (simbVariable.getTipo().getType().contains("INTEGER")||simbVariable.getTipo().getType().contains("OCTAL")) {
					codigo.append(lexema + " " + tipoDatoAssembler + " " + simbVariable.getTipo().getRangInferiorInteger() + "\n");
				}else {
					codigo.append(lexema + " " + tipoDatoAssembler + " " + simbVariable.getTipo().getRangInferiorDouble() + "\n");
				}
			}
			
			//Me fijo si se trata de un auxiliar de subtipo superior
			else if(lexema.contains("@auxSubtipoSuperior")) {
				String variable = lexema.substring(19); //Recorto la longitud del prefijo asi me quedo con el nombre de la variable a la que corresponde el auxiliar
				Simbolo simbVariable = AnalizadorLexico.TablaDeSimbolos.get(variable); //Busco la variable en la TS así accedo al valor de los rangos
				if (simbVariable.getTipo().getType().contains("INTEGER")||simbVariable.getTipo().getType().contains("OCTAL")) {
					codigo.append(lexema + " " + tipoDatoAssembler + " " + simbVariable.getTipo().getRangSuperiorInteger() + "\n");
				}else {
					codigo.append(lexema + " " + tipoDatoAssembler + " " + simbVariable.getTipo().getRangSuperiorDouble() + "\n");
				}
			}
			
			//Si no es ninguna de las dos auxiliares entonces es un entero/double comun. Reviso si es literal o no.
			else if (simbolo.esLiteral()) { // Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
				if (codigo.indexOf(prefijoNombre + lexema + " " + tipoDatoAssembler) == -1) { // Me fijo si en el StringBuilder ya existe algun substring que sea intNumero DW. Si devuelve -1 entonces lo inserto
					if (tipoString.contains("DOUBLE")) {
						codigo.append(convertirLexemaFlotante(prefijoNombre + lexema) + " " + tipoDatoAssembler + " " + simbolo.getDoub() + "\n");
					}else {
						//Aunque no sea double convierto el lexema para el caso de numeros enteros con signo negativo
						codigo.append(convertirLexemaFlotante(prefijoNombre + lexema) + " " + tipoDatoAssembler + " " + simbolo.getEntero() + "\n");
					}
				}
			} 
			//Si no es literal entonces el nombre del lexema puede ir en .DATA
			else {
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
		codigo.append("invoke printf, addr $_FIN_DE_PROGRAMA_$ \n");
		codigo.append("invoke crt_getchar \n");
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
