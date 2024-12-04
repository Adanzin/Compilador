package main.java.Compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GeneradorCodigoIntermedio {
	public static Map<String, ArrayList<String>> polacaFuncional = new HashMap<>();
	public static Map<String,Stack<Integer>> Pilas = new HashMap<>();
	public static Map<String,Integer> pos = new HashMap<>();
    public static ArrayList<String> Etiquetas = new ArrayList<>();
    public static ArrayList<String[]> BaulDeGotos = new ArrayList<>();  


	static {
        polacaFuncional.put("$MAIN", new ArrayList<String>());
        Pilas.put("$MAIN", new Stack<Integer>());
        pos.put("$MAIN", 0);
    }

	public static ArrayList<String> getPolaca() {
		return polacaFuncional.get(Parser.AMBITO.toString());
	}
	public static ArrayList<String> getPolacaConAmbito(String amb) {
		return polacaFuncional.get(amb);
		
	}


	public static void addNuevaPolaca() {
		polacaFuncional.put(Parser.AMBITO.toString(), new ArrayList<String>());
		Pilas.put(Parser.AMBITO.toString(),new Stack<Integer>());
		pos.put(Parser.AMBITO.toString(), 0);
	}
	public static void imprimirPolaca() {
		    for (String key : polacaFuncional.keySet()) {
		        ArrayList<String> polaca = polacaFuncional.get(key);

		        // Imprimir el nombre de la funci�n
		        System.out.println("");
		        System.out.println("Polaca: " + key);
		        int a=0;
		        // Imprimir los elementos de la lista polaca con formato de 2 d�gitos
		        for (String elemento : polaca) {
		        	System.out.println("["+a+"|"+elemento);
		            a++;
		        }
		    }
		}
	public static void desapilar() {
		Pilas.get(Parser.AMBITO.toString()).pop();
	}
	public static void apilar(int elm){
		Pilas.get(Parser.AMBITO.toString()).push(elm);
	}
	public static int getPila() {
		int salida =Pilas.get(Parser.AMBITO.toString()).lastElement();
		desapilar();
		return salida; 
		
	}
	public static void addElemento(String elm) {
		polacaFuncional.get(Parser.AMBITO.toString()).add(elm);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1);
	}
	public static void addElemento(String elm, int posi) {
		polacaFuncional.get(Parser.AMBITO.toString()).add(posi, elm);
	}
	public static void reemplazarElm(String elm, int posi,String amb) {
		polacaFuncional.get(amb).set(posi, elm);
	}
	public static void reemplazarElm(String elm, int posi) {
		polacaFuncional.get(Parser.AMBITO.toString()).set(posi,elm);
	}

	public static int getPos() {
		return pos.get(Parser.AMBITO.toString());
	}

	public void setPos(int posi) {
		pos.put(Parser.AMBITO.toString(),posi);
	}
	public static void bifurcarF() {
		apilar(pos.get(Parser.AMBITO.toString()));
		polacaFuncional.get(Parser.AMBITO.toString()).add(" ");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1);//pos++
		polacaFuncional.get(Parser.AMBITO.toString()).add("BF");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1);//pos++
	}
	public static void bifurcarAlInicio() {
		String aux =String.valueOf(getPila());
		polacaFuncional.get(Parser.AMBITO.toString()).add(aux);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++
		polacaFuncional.get(Parser.AMBITO.toString()).add("LABEL"+pos.get(Parser.AMBITO.toString()));
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++

	}
	public static void invocar(String id) {
		polacaFuncional.get(Parser.AMBITO.toString()).add(id);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("CALL");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	
	public static void invocar(String id, String conv) {
		polacaFuncional.get(Parser.AMBITO.toString()).add(conv);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add(id);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("CALL");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		
	}
	
	public static void bifurcarI() {
		apilar(pos.get(Parser.AMBITO.toString()));
		polacaFuncional.get(Parser.AMBITO.toString()).add(" ");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	public static void BifurcarAGoto() {
		
		polacaFuncional.get(Parser.AMBITO.toString()).add(" ");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	public static void addEtiqueta(String id) {
		if(!Etiquetas.contains(id)) {	
			Etiquetas.add(id);
		}
	}
	public static ArrayList<String> getGoto(String id) {
		return Etiquetas;
	}
    
    public static ArrayList<String[]> getBaulDeGotos() {
		return BaulDeGotos;
	}
	public static void addBaulDeGotos(String got){
		String[] g = Parser.getVariables(got,"/");
		BaulDeGotos.add(g);
		polacaFuncional.get(Parser.AMBITO.toString()).add(" ");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	
	/*public static void bifurcarFaux(ArrayList<String> aux, int posAux) {
		apilar(posAux);
		aux.add(" ");
		aux.add("BF");
		
	};*/
	
	//esta funcion saca de la polaca los operandos y los pega en un arreglo auxiliar. En esta se cargan ordenado
	// y se vuelven a cargar en la polaca. 
	public static void addOperadorEnPattMatch(String operador,int cantOP) {
		int n = pos.get(Parser.AMBITO.toString())-1; // posicion en la que inicia el patter
		// Resto 1 a la posicion para pararme en el ultimo elemento cargado
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())-1); 
		//agarro la ultima expresion aritmetica cargada
		String expArit = polacaFuncional.get(Parser.AMBITO.toString()).get(n);
		Stack<String> pilaIzq= new Stack<String>();
		Stack<String> pilaDer = new Stack<String>();
		// en esta cuenta las ',', hay una por cada elemento. 
		int contadorComas=0;
		while (contadorComas<=cantOP*2-1) {
			//elimino el ultimo elemento cargado
			polacaFuncional.get(Parser.AMBITO.toString()).remove(n);
			if(expArit==",") {
				contadorComas++;
			}
			// Guardo las expresiones de la derecha en una pila y las de la izquierad en otra
			if(contadorComas<=cantOP) {
				pilaDer.add(expArit);
			}else {
				pilaIzq.add(expArit);
			}
			n--;
			//Tomo el siguiente elemento de la polaca
			pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())-1);
			expArit = polacaFuncional.get(Parser.AMBITO.toString()).get(n);
		}
		//incremento 1 la posicion para empezar a cargar en la posicion correcta.
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1);
		contadorComas = 0;
		String var;
		while(contadorComas<=cantOP*2-1) {
			// Saco de la pila izq primero y la coloco en la polaca
			var=pilaIzq.pop();
			while((var!=",")) {
				addElemento(var);
				var=pilaIzq.pop();
			}
			// Saco de la pila derecha para cargarlo en orden
			contadorComas++;
			var=pilaDer.pop();
			while((var!=",")) {
				addElemento(var);
				var=pilaDer.pop();
			}
			contadorComas++;
			addElemento(operador);
			// Agrego la Bifurcacion por Falso despues de cada comparacion
			apilar(pos.get(Parser.AMBITO.toString()));
			addElemento(" ");
			addElemento("BF");

		}
	}
}