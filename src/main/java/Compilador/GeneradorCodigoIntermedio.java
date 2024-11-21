package main.java.Compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GeneradorCodigoIntermedio {
	public static Map<String, ArrayList<String>> polacaFuncional = new HashMap<>();
	public static Map<String,Stack<Integer>> Pilas = new HashMap<>();
	public static Map<String,Integer> pos = new HashMap<>();
    public static Map<String,Stack<Integer>> BaulDeGoto = new HashMap<>();  
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

		        // Imprimir el nombre de la función
		        System.out.println("Polaca: " + key);
		        int a=0;
		        // Imprimir los elementos de la lista polaca con formato de 2 dígitos
		        for (String elemento : polaca) {
		        	 System.out.println("["+a+" | " + elemento);
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
	public static void bifurcarI() {
		apilar(pos.get(Parser.AMBITO.toString()));
		polacaFuncional.get(Parser.AMBITO.toString()).add(" ");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	public static void BifurcarAGoto(String id) {
		addGoto(id);
		polacaFuncional.get(Parser.AMBITO.toString()).add(" ");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	public static void addGoto(String id) {
		if(BaulDeGoto.get(id)==null) {
			BaulDeGoto.put(id,new Stack<Integer>());	
		}
		BaulDeGoto.get(id).push(pos.get(Parser.AMBITO.toString()));
	}
	public static int getGoto(String id) {
		if(BaulDeGoto.get(id).isEmpty()) {
			return -1;
		}
		return BaulDeGoto.get(id).pop();
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
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())-1);
		String expArit = polacaFuncional.get(Parser.AMBITO.toString()).get(n);
		Stack<String> pilaIzq= new Stack<String>();
		Stack<String> pilaDer = new Stack<String>();
		int contadorComas=0;
		while (expArit!=":=" && !expArit.contains("LABEL")) {
			polacaFuncional.get(Parser.AMBITO.toString()).remove(n);
			if(expArit==",") {
				contadorComas++;
			}
			if(contadorComas<=cantOP) {
				pilaDer.add(expArit);
			}else {
				pilaIzq.add(expArit);
			}
			n--;
			pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())-1);
			expArit = polacaFuncional.get(Parser.AMBITO.toString()).get(n);
		}
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1);
		contadorComas = 0;
		String var;
		while(contadorComas<=cantOP*2-1) {
			var=pilaIzq.pop();
			while((var!=",")) {
				addElemento(var);
				var=pilaIzq.pop();
				System.out.println(var);
			}
			contadorComas++;
			var=pilaDer.pop();
			while((var!=",")) {
				addElemento(var);
				var=pilaDer.pop();
				System.out.println(var);
			}
			contadorComas++;
			addElemento(operador);
			apilar(pos.get(Parser.AMBITO.toString()));
			addElemento(" ");
			addElemento("BF");

		}
	}
}