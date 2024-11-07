package Compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GeneradorCodigoIntermedio {
	private static int pos = 0;
	public static ArrayList<String> polaca = new ArrayList<String>();
	public static Stack<Integer> pila = new Stack<Integer>();
    public static Map<String, Integer> BaulDeGoto = new HashMap<>();  

	
	public GeneradorCodigoIntermedio() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getPolaca() {
		return polaca;
	}

	public void setPolaca(ArrayList<String> polaca) {
		this.polaca = polaca;
	}
	public static void imprimirPolaca() {
	    for (String elemento : polaca) {
	        System.out.print(String.format("%2s", elemento) + " ");
	    }
	    System.out.println();

	    // Imprimir los índices correspondientes con formato de 2 dígitos
	    for (int i = 0; i < polaca.size(); i++) {
	        System.out.print(String.format("%02d", i) + " ");
	    }
	    System.out.println();
	}
	public static void desapilar() {
		System.out.println(pila.toString());
		pila.pop();
		System.out.println(pila.toString());
	}
	public static void apilar(int elm) {
		System.out.println(pila.toString());
		pila.push(elm);
		System.out.println(pila.toString());
	}
	public static int getPila() {
		int salida =pila.lastElement();
		desapilar();
		return salida; 
		
	}
	public static void addElemento(String elm) {
		polaca.add(elm);
		pos++;
	}
	public static void addElemento(String elm, int posi) {
		polaca.add(posi, elm);
	}

	public static int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	public static void bifurcarF() {
		apilar(pos);
		pos++;
		polaca.add("BF");
		pos++;
	}
	public static void bifurcarAlInicio() {
		System.out.println("BIFURCACION AL INICIO");
		String aux =String.valueOf(getPila());
		polaca.add(aux);
		pos++;
		polaca.add("BI");
		pos++;
	}
	public static void bifurcarIporFuncion(String id) {
		System.out.println("BIFURCACION A FUNCION");
		String aux =String.valueOf(AnalizadorLexico.TablaDeSimbolos.get(id).getDirMEM());
		polaca.add(aux);
		pos++;
		polaca.add("BI");
		pos++;
	}
	public static void bifurcarI() {
		apilar(pos);
		pos++;
		polaca.add("BI");
		pos++;
	}
	public static void BifurcarAGoto(String id) {
		System.out.println("BIFURCACION A TO GO");		
		addGoto(id);
		pos++;
		polaca.add("BI");
		pos++;
	}
	public static void addGoto(String id) {
		BaulDeGoto.put(id, pos);
		System.out.println(BaulDeGoto.toString());
	}
	public static int getGoto(String id) {
		System.out.println(BaulDeGoto.toString());
		return BaulDeGoto.get(id);
	}
}
