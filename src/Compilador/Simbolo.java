package Compilador;

public class Simbolo {
    private int entero;       // Tipo de variable
    private double doub; 
    private String id;
    private int base;  // Base del n�mero (8 = octal, 10 = decimal, 16 = hexadecimal)
    private int contadorDeReferencias;
    private boolean esSubTipo;
    // Constructor
    public Simbolo() {
        this.entero=-1;
        this.doub=-1.0;
        this.id=null;
        this.base = 10;  // Por defecto, base decimal
        this.contadorDeReferencias = 0;
        this.setEsSubTipo(false);
    }
    public Simbolo(int e,double d, int b) {
        this.entero=e;
        this.doub=d;
        this.id=null;
        this.base = b;  // Por defecto, base decimal
        this.contadorDeReferencias=0;
        this.setEsSubTipo(false);
    }
    
    
    public Simbolo(int base) {
        this.entero=-1;
        this.doub=-1.0;
        this.id=null;
        this.base = base; 
    }
    
    public boolean esUltimo() { 
    	if(this.contadorDeReferencias==1) {
    		return true;
    	}else {
    		this.contadorDeReferencias--;
    		return false;
    	}
    }
    
    public boolean esEntero() {
    	if (this.entero == -1) {
    		return false;
    	}
    	return true;
    }
    
    public boolean enRangoPositivo(String id) {
    	int auxint;
    	double mayor = 32767;
    	if (this.base == 8) {
    		auxint = Integer.parseInt(id, 10); 
    	}else {auxint=this.entero;}
    	if(auxint<= mayor) {
    		return true;
    	}
    	return false;
    }
    public void decrementarContDeRef() {this.contadorDeReferencias--;}
    public void incrementarContDeRef() {this.contadorDeReferencias++;}
    
    public Simbolo getCopiaNeg() {
    	if(this.esEntero()) {
    		return new Simbolo(this.entero*-1,this.doub,this.base);
    	}
    	return new Simbolo(this.entero,this.doub*-1.0,this.base);
    }

    public double getDoub() {
        return doub;
    }
    public void setDoub(double doub) {
        this.doub = doub;
        this.contadorDeReferencias++;
    }
    public int getEntero() {
        return entero;
    }
    public void setEntero(int entero) {
        this.entero = entero;
        this.contadorDeReferencias++;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
        this.contadorDeReferencias++;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        // Chequear si el entero fue inicializado
        if (entero != -1) {
            sb.append(entero);
            sb.append(", ");
        }

        // Chequear si el double fue inicializado
        if (doub != -1.0) {
            sb.append(doub);
            sb.append(", ");
        }

        // Chequear si el id fue inicializado
        if (id != null) {
            sb.append(id);
            sb.append(", ");
        }
        sb.append(base);
        sb.append(", ");
        sb.append(contadorDeReferencias);
        sb.append(", ");
        sb.append(esSubTipo);

        // Si ning�n valor fue inicializado
        if (sb.length() == 0) {
            return "Esta vacio";
        }

        sb.append(">");
        return sb.toString();
    }
    public int getBase() {
        return base;
    }
    public void setBase(int base) {
        this.base = base;
    }
	public boolean isEsSubTipo() {
		return esSubTipo;
	}
	public void setEsSubTipo(boolean esSubTipo) {
		this.esSubTipo = esSubTipo;
	}
    
   

}