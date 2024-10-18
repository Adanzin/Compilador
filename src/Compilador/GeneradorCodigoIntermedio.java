package Compilador;

import java.util.ArrayList;

public class GeneradorCodigoIntermedio {

	private ArrayList<String> polaca;
	
	public GeneradorCodigoIntermedio() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getPolaca() {
		return polaca;
	}

	public void setPolaca(ArrayList<String> polaca) {
		this.polaca = polaca;
	}

	public void addElemento(String elm,String ambito) {
		polaca.add(elm);
	}
	
}
