package main.java.Compilador;

import java.util.ArrayList;
import java.util.Arrays;

public class Tipo {
    private String type=null; 
    private boolean subTipo=false;
    private double rangInferior=0;
    private double rangSuperior=0;
    private boolean triple =false;
    private int[] valInt = new int[3];
    private double[] valDouble = new double[3];

	public Tipo(String t) {
		this.type=t;
    }
	public Tipo(String t,double rangInferior, double rangSuperior) {
        this.type=t;
        this.subTipo=true;
        this.rangInferior=rangInferior;
        this.rangSuperior=rangSuperior;
    }
	public Tipo(String t, boolean tri) {
        this.type=t;
        this.triple=tri;
    }
	
	public boolean esSubTipo() {
		return subTipo;
	}
	public boolean esTripla() {
		return triple;
	}
	
	@Override
	public String toString() {
		if((subTipo==false) && (triple==false)) {
			return type;
		}else{
			if(subTipo==true) {
				return  type + "[ " + rangInferior +", "+rangSuperior + " ]";
			}else {
				return type+"[3]";
			}
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean sonCompatibles(Tipo t) {
		return this.getType()==t.getType();
	}

}
