package main.java.Compilador;

import java.util.Stack;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class GeneradorCodigoAssembler {
	
	public static Map<String, StringBuilder> codigoAssembler = new HashMap<>();
	public static Stack<String> pila = new Stack<String>();
	
	public static int numAuxiliares = 1;
	
	private static String ultimaFuncion = null;
	public static String ultimaOperacion = null; //Se usa para debuggear nomas
		
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
				case "BF","BI":
					ultimaOperacion = elemento;
					operadorSentenciasControl(elemento, codigo, polacaActual.get(i-2));
					break;
				case "<",">","<=",">=","==", "!=":	
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
		if(simbOperando1.sonCompatibles(simbOperando2)) {
			Tipo tipoOperando = simbOperando1.getTipo();
			if (tipoOperando.getType()=="INTEGER" || tipoOperando.getType()=="OCTAL") {
				operando1 = comprobarOperandoLiteral(operando1);
				operando2 = comprobarOperandoLiteral(operando2);
				operacionEnteroOctal(operando1, operando2, operacion, codigo, tipoOperando);
			} else {
				operando1 = comprobarOperandoLiteral(operando1);
				operando2 = comprobarOperandoLiteral(operando2);
				operacionDouble(operando1, operando2, operacion, codigo, tipoOperando);
			}
		}
		else {
			System.out.println("ERROR TIPOS INCOMPATIBLES \n");
			System.exit(1); //Termino la ejecución del compilador por error en etapa de compilacion
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
	
	public static void operadorSentenciasControl(String elemento, StringBuilder codigo, String comparadorAnterior) {
		String operador = pila.pop(); //Es la direccion a saltar
		switch(elemento) {
			case "BF":
				switch (comparadorAnterior) {
				//Le paso el operador anterior al salto para saber que comparación era y así usar el jump adecuado
					case ">":
						codigo.append("JLE LABEL" + operador + "\n \n"); //Salto en el caso contrario al de la comparacion
						break;
					case "<":
						codigo.append("JGE LABEL" + operador + "\n \n"); //Salto en el caso contrario al de la comparacion
						break;
					case ">=":
						codigo.append("JL LABEL" + operador + "\n \n"); //Salto en el caso contrario al de la comparacion
						break;
					case "<=":
						codigo.append("JG LABEL" + operador + "\n \n"); //Salto en el caso contrario al de la comparacion
						break;
					case "=":
						codigo.append("JNE LABEL" + operador + "\n \n"); //Salto en el caso contrario al de la comparacion
						break;
					case "!=":
						codigo.append("JE LABEL" + operador + "\n \n"); //Salto en el caso contrario al de la comparacion
						break;
				}
				break;
			case "BI":
				codigo.append("JMP LABEL" + operador + "\n \n"); //Salto sí o sí a la etiqueta
				break;
		}
		
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
		switch (elemento) {
			case "INTEGER", "OCTAL", "DOUBLE":
				operadorConversion(operando, elemento, codigo, tipoOperando);
				break;
			case "RET":
				codigo.append("MOV AX, " + operando + "\n"); //Guardo la variable que quiero retornar en AX
				codigo.append("MOV @RET" + nombrePolaca + ", AX" + "\n");
				codigo.append("POP ESI \n");
				codigo.append("POP EDI \n");
				codigo.append("MOV ESP, EBP \n");
				codigo.append("POP EBP \n");
				codigo.append("RET" + "\n \n");
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
				codigo.append("SUB AX, " + operando2 + "\n \n"); //Comparo los operandos y el resultado va a afectar a los flags para los saltos
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
			codigo.append("invoke printf, cfm$(\"%.20Lf\\n\"), " + operando + "\n \n" ); //Copio el invoke de _floati del archivo de moodle
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
		switch(AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo().toString()) {
		case "INTEGER", "integer", "Integer":
			operando="int"+operando;
			return operando;
		case "OCTAL", "octal", "Octal":
			operando="octi"+operando;
			return operando;
		case "DOUBLE", "double", "Double":
			operando="float"+operando;
			return operando;
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
	
	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------GENERACION TEMPLATE ASSEMBLER PENTIUM----------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------
	
	public static StringBuilder generarData() {
		StringBuilder codigo = new StringBuilder();
		codigo.append(".data \n");
		codigo.append("Error_DivisionCero DB \"Error: Division por cero\", 10, 0 \n");
		codigo.append("Error_Overflow DB \"Error: Overflow en producto entre Enteros\", 10, 0 \n");
		codigo.append("ESPERAR_ACCION_USUARIO DB \"Haga click en ACEPTAR para cerrar el programa y la consola\", 10, 0 \n");
		for (Map.Entry<String, Simbolo> iterador : AnalizadorLexico.TablaDeSimbolos.entrySet()) {
			
			String lexema = iterador.getKey();
			Simbolo simbolo = iterador.getValue();
			Tipo tipo = simbolo.getTipoVar();
			if(tipo != null) {
				String tipoString = tipo.toString();

				switch (tipoString) {
					case "INTEGER":
						if (simbolo.esLiteral()) { //Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
							if(codigo.indexOf("int" + lexema + " DW")==-1) { //Me fijo si en el StringBuilder ya existe algun substring que sea intNumero DW. Si devuelve -1 entonces lo inserto
								codigo.append("int" + lexema + " DW " + simbolo.getEntero() + "\n");
							}
						}
						else {
							codigo.append(lexema + " DW ?" + "\n");
						}
						break;
					case "DOUBLE":
						if (simbolo.esLiteral()) { //Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
							lexema = convertirLexemaFlotante(lexema);
							if(codigo.indexOf("float" + lexema + " DQ")==-1) { //Me fijo si en el StringBuilder ya existe algun substring que sea intNumero DQ. Si devuelve -1 entonces lo inserto
								codigo.append("float" + lexema + " DQ " + simbolo.getDoub() + "\n");
							}
						}
						else {
							codigo.append(lexema + " DQ ?" + "\n");
						}
						break;
					case "OCTAL": //Me guio del octi DW 077o de test_print
						if (simbolo.esLiteral()) { //Verifico primero si es una constante literal para saber si le cargo el valor o va el '?'
							if(codigo.indexOf("octi" + lexema + " DW")==-1) { //Me fijo si en el StringBuilder ya existe algun substring que sea intNumero DW. Si devuelve -1 entonces lo inserto
								codigo.append("octi" + lexema + " DW " + simbolo.getEntero() + "o \n");
							}
						}
						else {
							codigo.append(lexema + " DW ?" + "\n");
						}
						break;
					case "CADENAMULTILINEA":
						String lexemaConvertido = convertirLexemaCadena(lexema);
						if(codigo.indexOf(lexemaConvertido)==-1) {
							codigo.append(lexemaConvertido).append(" db \"").append(lexema.replace("\n", " ").replace("[", "").replace("]", "").replaceAll("\\s+", " ").trim()).append("\", 0 \n"); //Lo hago de esta manera para poder guardar el lexema entre comillas
						}
						break;
					default: //Tipos no primitivos entrarían aquí, VER COMO PROCEDER
						break;
				}
			}
		}
		return codigo;
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
