package Compilador;

import java.util.ArrayList;
import java.util.Stack;

public class GeneradorCodigoIntermedio {
	private static int pos = 0;
	public static ArrayList<String> polaca = new ArrayList<String>();
	public static Stack<Integer> pila = new Stack<Integer>();
	
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
		System.out.println(polaca.toString());		
	}
	public static void desapilar() {
		pila.pop();
	}
	public static void apilar(int elm) {
		pila.push(elm);
	}
	public static int getPila() {
		int salida =pila.firstElement();
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
		pila.push(pos);
		pos++;
		polaca.add("BF");
		pos++;
	}
	public static void bifurcarAlInicio() {
		polaca.add(String.valueOf(getPila()));
		pos++;
		polaca.add("BI");
		pos++;
	}
	public static void bifurcarI() {
		pila.push(pos);
		pos++;
		polaca.add("BI");
		pos++;
	}

}
