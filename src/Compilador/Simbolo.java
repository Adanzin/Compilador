package Compilador;

public class Simbolo {
    private int entero;       // Tipo de variable
    private double doub; 
    private String id;
    private int base;  // Base del número (8 = octal, 10 = decimal, 16 = hexadecimal)
    // Constructor
    public Simbolo() {
        this.entero=-1;
        this.doub=-1.0;
        this.id=null;
        this.base = 10;  // Por defecto, base decimal
    }
    
    public Simbolo(int base) {
        this.entero=-1;
        this.doub=-1.0;
        this.id=null;
        this.base = base; 
    }
    
    public boolean esEntero() {
    	if (this.entero == -1) {
    		return false;
    	}
    	return true;
    }
    
    public boolean esOctal() {
     	if (this.base == 8) {
    		return false;
    	}
    	return true;
    }
    
    public boolean enRango() {
    	if (this.base == 8) {
    		int tokenint = Integer.
    	}
    	return true;
    }
    
    public double getDoub() {
        return doub;
    }
    public void setDoub(double doub) {
        this.doub = doub;
    }
    public int getEntero() {
        return entero;
    }
    public void setEntero(int entero) {
        this.entero = entero;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Chequear si el entero fue inicializado
        if (entero != -1) {
            sb.append(entero);
        }

        // Chequear si el double fue inicializado
        if (doub != -1.0) {
            sb.append(doub);
        }

        // Chequear si el id fue inicializado
        if (id != null) {
            sb.append(id);
        }

        // Si ningún valor fue inicializado
        if (sb.length() == 0) {
            return "Esta vacio";
        }

        return sb.toString();
    }
    public int getBase() {
        return base;
    }
    public void setBase(int base) {
        this.base = base;
    }


}