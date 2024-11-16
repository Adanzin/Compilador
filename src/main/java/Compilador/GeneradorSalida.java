package main.java.Compilador;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class GeneradorSalida {
	
	public static Map<String, StringBuilder> codigoAssembler = new HashMap<>();
	public static Stack<String> pila = new Stack<String>();
	
	public static int numAuxiliares = 1;
	public static String ambitoActual = "";
	
	public GeneradorSalida() {
		// TODO Auto-generated constructor stub
	}
	
	public static void recorrerPolaca(String ambitoPolaca) {
		ambitoActual = ambitoPolaca;
		StringBuilder codigo = new StringBuilder();
		ArrayList<String> polacaActual = GeneradorCodigoIntermedio.getPolacaConAmbito(ambitoPolaca);
		String elemento;
		for (int i=0; i < polacaActual.size();i++) {
			elemento = polacaActual.get(i);
			switch (elemento) {
				case "+","-","*","/",":=":
					operadorBinario(elemento, codigo);
					break;
				case "":
					operadorUnario(elemento, codigo);
					break;
				case "CALL":
					System.out.println("Se llama a la funcion " + polacaActual.get(i-1));
					recorrerPolaca(polacaActual.get(i-1));
					break;
				default: //La idea es que entre por default cuando sea operando
					pila.push(elemento);
					break;
			}
		}
		codigoAssembler.put(ambitoPolaca, codigo); //Si usara el ambitoActual entonces al volver del llamado recursivo ya habría quedado pisado por el ultimo ambito interior
	}
	
	public static void operadorBinario(String operacion, StringBuilder codigo) {
		String operando2 = pila.pop();
		String operando1 = pila.pop();
		if(	AnalizadorLexico.TablaDeSimbolos.get(operando1).sonCompatibles(AnalizadorLexico.TablaDeSimbolos.get(operando2))) {
			if (AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo()==Parser.tipos.get("INTEGER") || AnalizadorLexico.TablaDeSimbolos.get(operando1).getTipo()==Parser.tipos.get("OCTAL")) {
				operacionEnteroOctal(operando1, operando2, operacion, codigo);
			} else {
				operacionDouble(operando1, operando2, operacion, codigo);
			}
		}
	}
	
	public static void operacionEnteroOctal(String operando1, String operando2, String operacion, StringBuilder codigo) {
		//Esta funcion usa el registro AX de 16 bits para enteros y octales.
		codigo.append("MOV AX," + "_" + operando1 + "\n"); 
		switch (operacion) {
			case "+":
				codigo.append("ADD AX," + "_" + operando2 + "\n");
				break;
			case "-":
				//Revisar caso de la resta
				codigo.append("SUB AX," + "_" + operando2 + "\n"); 
				break;
			case "*":
				codigo.append("IMUL AX," + "_" + operando2 + "\n"); //Uso IMUL para cubrir el caso en que el operando sea una constante literal //Habría que cambiarlo para ingresar al map
				break;
			case "/":
				if(AnalizadorLexico.TablaDeSimbolos.get(operando1).esLiteral()) { 
					crearAuxiliar(operando1);
					operando1 = pila.pop();
				}
				if(AnalizadorLexico.TablaDeSimbolos.get(operando2).esLiteral()) { 
					crearAuxiliar(operando2);
					operando2 = pila.pop();
				}
				codigo.append("MOV BX," + "_" + operando2 + "\n"); //Uso BX para no pisar AX con el operando1
				codigo.append("CMP BX" + ",0" + "\n");
				codigo.append("JZ DivisionPorCero"); //JZ salta si el flag ZF=1 (es cero la comparacion anterior)
				//Continua el flujo normal en caso de no saltar
				codigo.append("IDIV AX," + "_" + operando2 + "\n");
				break;
			case ":=":
				codigo.append("");
				break;
			default:
				//Después veré pero acá no debería entrar nada
				break;
		}
		codigo.append("MOV @aux" + numAuxiliares + ",AX");
		pila.push(crearAuxiliar(operando1));
		codigo.append("\n");
	}
	
	public void crearDivisionPorCero(StringBuilder codigo) {
		codigo.append("DivisonPorCero:" + "\n");
		codigo.append("invoke printf, addr mensaje_error_divisionCero"); //Debo cargar en la seccion de .data lo siguiente (mensaje_error_divisionCero DB "Error: Division por cero", 10, 0)
		codigo.append("invoke ExitProcess, 0" + "\n"); //Debería saltar a la etiqueta END START?
	}

	
	public static String crearAuxiliar(String operando) {
		Simbolo simb = new Simbolo();
		simb.setBase(AnalizadorLexico.TablaDeSimbolos.get(operando).getBase());
		simb.setTipoVar(Parser.tipos.get(AnalizadorLexico.TablaDeSimbolos.get(operando).getTipo()));
		simb.setUso("Var Aux");
		AnalizadorLexico.TablaDeSimbolos.put("@aux"+numAuxiliares,simb); //Agrego la nueva auxiliar a la TS
		numAuxiliares++;
		return("@aux"+(numAuxiliares-1));
	}
	
	public static void operacionDouble(String operando1, String operando2,String operacion, StringBuilder codigo) {
		codigo.append("FLD _" + operando2 + "\n"); //Apilo el operando2
		codigo.append("FLD _" + operando1 + "\n"); //Apilo el operando1
		String aux = crearAuxiliar(operando1);
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
				codigo.append("FTST" + "\n"); //Comparo ST (operando2) con el cero
				codigo.append("FSTCW mem2byte" + "\n"); //Almaceno la palabra de estado en la memoria
				codigo.append("MOV AX mem2byte"); //Copio el contenido en AX del procesador principal
				codigo.append("SAHF"); //Preguntar que es esto
				// Preguntar lo anterior
				//-----------------------------------------------------------------
				
				codigo.append("FDIV" + "\n"); //ST(0) = ST(0) * ST(1)
				codigo.append("FSTP " + aux + "\n"); //Guardo el resultado en una auxiliar 
				break;
			default:
				//Acá no debería entrar nada
				break;
		}
		
	}
	
	public static void operadorUnario(String elemento, StringBuilder codigo) {
		
	};
	
	
}