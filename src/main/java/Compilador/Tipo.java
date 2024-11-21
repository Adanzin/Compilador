package main.java.Compilador;

public class Tipo {
    private String type=null; 
    private boolean subTipo=false;
    private double rangInferiorDouble=Double.MIN_VALUE;
    private double rangSuperiorDouble=Double.MAX_VALUE;
    private int rangInferiorInteger=Integer.MAX_VALUE;
    private int rangSuperiorInteger=Integer.MIN_VALUE;
    private boolean triple =false;

	public Tipo(String t) {
		this.type=t;
    }
	public Tipo(String t, double rangInferior, double rangSuperior) {
        this.type=t;
        this.subTipo=true;
        this.rangInferiorDouble=rangInferior;
        this.rangSuperiorDouble=rangSuperior;
    }
	public Tipo(String t, int rangInferior, int rangSuperior) {
        this.type=t;
        this.subTipo=true;
        this.rangInferiorInteger=rangInferior;
        this.rangSuperiorInteger=rangSuperior;
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
				if(this.type=="INTEGER") {
					return  type + "[ " + rangInferiorInteger +", "+rangSuperiorInteger + " ]";
				}
				return  type + "[ " + rangInferiorDouble +", "+rangSuperiorDouble + " ]";
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
		if(this.esSubTipo() && !t.esSubTipo()) {
			return this.getType().contains(t.getType());
		}else if(!this.esSubTipo() && t.esSubTipo()) {
			return t.getType().contains(this.getType());
		}
		return this.getType()==t.getType();
	}
	public double getRangSuperiorDouble() {
		return rangSuperiorDouble;
	}
	public void setRangSuperiorDouble(double rangSuperiorDouble) {
		this.rangSuperiorDouble = rangSuperiorDouble;
	}
	public double getRangInferiorDouble() {
		return rangInferiorDouble;
	}
	public void setRangInferiorDouble(double rangInferiorDouble) {
		this.rangInferiorDouble = rangInferiorDouble;
	}
	public int getRangInferiorInteger() {
		return rangInferiorInteger;
	}
	public void setRangInferiorInteger(int rangInferiorInteger) {
		this.rangInferiorInteger = rangInferiorInteger;
	}
	public int getRangSuperiorInteger() {
		return rangSuperiorInteger;
	}
	public void setRangSuperiorInteger(int rangSuperiorIngeter) {
		this.rangSuperiorInteger = rangSuperiorIngeter;
	}
	


}