package main.java.Compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GeneradorCodigoIntermedio {
	public static Map<String, ArrayList<String>> polacaFuncional = new HashMap<>();
	public static Map<String,Stack<Integer>> Pilas = new HashMap<>();
	public static Map<String,Integer> pos = new HashMap<>();
    public static Map<String, Integer> BaulDeGoto = new HashMap<>();  
    static {
        polacaFuncional.put(Parser.AMBITO.toString(), new ArrayList<String>());
        Pilas.put(Parser.AMBITO.toString(), new Stack<Integer>());
        pos.put(Parser.AMBITO.toString(), 0);
    }

	public static ArrayList<String> getPolaca() {
		return polacaFuncional.get(Parser.AMBITO.toString());
	}
	public static ArrayList<String> getPolacaConAmbito(String amb) {
		return polacaFuncional.get(amb);
	}


	public static void addNuevaPolaca() {
		System.out.println(" > Se creo una nueva polaca llamada "+Parser.AMBITO.toString());
		polacaFuncional.put(Parser.AMBITO.toString(), new ArrayList<String>());
		Pilas.put(Parser.AMBITO.toString(),new Stack<Integer>());
		pos.put(Parser.AMBITO.toString(), 0);
	}
	public static void imprimirPolaca() {
		    for (String key : polacaFuncional.keySet()) {
		        ArrayList<String> polaca = polacaFuncional.get(key);

		        // Imprimir el nombre de la función
		        System.out.println("Polaca: " + key);

		        // Imprimir los elementos de la lista `polaca` con formato de 2 dígitos
		        for (String elemento : polaca) {
		            System.out.print(String.format("%2s", elemento) + " ");
		        }
		        System.out.println();

		        // Imprimir los índices correspondientes con formato de 2 dígitos
		        for (int i = 0; i < polaca.size(); i++) {
		            System.out.print(String.format("%02d", i) + " ");
		        }
		        System.out.println("\n"); // Espacio entre funciones
		    }
		}
	public static void desapilar() {
		System.out.println(Pilas.get(Parser.AMBITO.toString()).toString());
		Pilas.get(Parser.AMBITO.toString()).pop();
		System.out.println(Pilas.get(Parser.AMBITO.toString()).toString());
	}
	public static void apilar(int elm){
		System.out.println(Pilas.get(Parser.AMBITO.toString()).toString());
		Pilas.get(Parser.AMBITO.toString()).push(elm);
		System.out.println(Pilas.get(Parser.AMBITO.toString()).toString());
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
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1);//pos++
		polacaFuncional.get(Parser.AMBITO.toString()).add("BF");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1);//pos++
	}
	public static void bifurcarAlInicio() {
		System.out.println("BIFURCACION AL INICIO");
		String aux =String.valueOf(getPila());
		polacaFuncional.get(Parser.AMBITO.toString()).add(aux);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++
	}
	public static void invocar(String id) {
		System.out.println("invocacion A FUNCION");
		polacaFuncional.get(Parser.AMBITO.toString()).add(id);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("CALL");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	public static void bifurcarI() {
		apilar(pos.get(Parser.AMBITO.toString()));
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	public static void BifurcarAGoto(String id) {
		System.out.println("BIFURCACION A TO GO");		
		addGoto(id);
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
		polacaFuncional.get(Parser.AMBITO.toString()).add("BI");
		pos.put(Parser.AMBITO.toString(),pos.get(Parser.AMBITO.toString())+1); //pos++;
	}
	public static void addGoto(String id) {
		BaulDeGoto.put(id, pos.get(Parser.AMBITO.toString()));
		System.out.println(BaulDeGoto.toString());
	}
	public static int getGoto(String id) {
		System.out.println(BaulDeGoto.toString());
		return BaulDeGoto.get(id);
	}
	
	//esta funcion saca de la polaca los operandos y los pega en un arreglo auxiliar. En esta se cargan ordenado
	// y se vuelven a cargar en la polaca. 
	public static void addOperadorEnPattMatch(String operador,int cantOP) {
		System.out.println();
		int n = polacaFuncional.get(Parser.AMBITO.toString()).size()-1; // Tamaño inicial del arreglo
		ArrayList<String> resultado = new ArrayList<>(); 
        // Agregar bloques reordenados desde el final hacia el inicio
		int posI=n-5;
		int posD=n-2;
        // Operandos en el bloque actual
        String operando1 = polacaFuncional.get(Parser.AMBITO.toString()).get(posI);
        String operando2 = polacaFuncional.get(Parser.AMBITO.toString()).get(posD);
        // Agregar al resultado en el orden correcto
        resultado.add(operando1);               // Segundo operando
        resultado.add(operando2);               // Primer operando
        resultado.add(">=");      // Operador relacional '>='
        while (posD < n) {
        	posI++;
        	posD++;
            // Operandos en el bloque actual
            operando1 = polacaFuncional.get(Parser.AMBITO.toString()).get(posI);
            operando2 = polacaFuncional.get(Parser.AMBITO.toString()).get(posD);

            // Agregar al resultado en el orden correcto
            resultado.add(operando1);               // Segundo operando
            resultado.add(operando2);               // Primer operando
            resultado.add(">=");      // Operador relacional '>='
            resultado.add("&");          // Operador lógico '&'
            bifurcarF();
        }
        int it=polacaFuncional.get(Parser.AMBITO.toString()).size()-cantOP*2;
        int it2=0;
        while(it2<resultado.size()) {
        	System.out.println("EL IT "+ it + " EL IT2 "+it2);
            try {
            	reemplazarElm(resultado.get(it2),it);
            }catch(IndexOutOfBoundsException e){
            	addElemento(resultado.get(it2));
            }
        	it2++;
        	it++;
        }

	}
}
