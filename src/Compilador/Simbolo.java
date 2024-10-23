package Compilador;

public class Simbolo {
    private int entero;       // Tipo de variable
    private double doub; 
    private String id;
    private Object tipoVar;
    private String uso;
    private int base;  // Base del n�mero (8 = octal, 10 = decimal, 16 = hexadecimal)
    private int contadorDeReferencias;
    private boolean defPorUser;
    private boolean seDeclaro;
    // Constructor
    public Simbolo() {
        this.entero=-1;
        this.doub=-1.0;
        this.tipoVar=null;
        this.id=null;
        this.uso=null;
        this.seDeclaro=false;
        this.base = 10;  // Por defecto, base decimal
        this.contadorDeReferencias = 0;
        this.defPorUser = false;
    }
    public Object getTipoVar() {
		return tipoVar;
	}
	public void setTipoVar(Object tipoVar) {
		this.tipoVar = tipoVar;
	}
	public Simbolo(int e,double d, int b) {
        this.entero=e;
        this.doub=d;
        this.id=null;
        this.tipoVar=null;
        this.uso=null;
        this.seDeclaro=false;
        this.base = b;  // Por defecto, base decimal
        this.contadorDeReferencias=0;
        this.defPorUser = false;
    }
    
    
    public Simbolo(int base) {
        this.entero=-1;
        this.doub=-1.0;
        this.id=null;
        this.tipoVar=null;
        this.uso=null;
        this.seDeclaro=false;
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

    //amb va a venir del siguiente formato ".A.B"
    public void agregarAmbito(String amb) {
        Simbolo simb = new Simbolo(this.entero, this.doub,this.tipoVar,this.uso,this.base,this.contadorDeReferencias,
        		this.defPorUser,this.seDeclaro);
        AnalizadorLexico.TablaDeSimbolos.put(id+amb, simb);
        AnalizadorLexico.TablaDeSimbolos.remove(id);
    }
    
    
    public Simbolo(int entero, double doub, Object tipoVar, String uso, int base, int contadorDeReferencias,
			boolean esSubTipo, boolean seDeclaro) {
		super();
		this.entero = entero;
		this.doub = doub;
		this.tipoVar = tipoVar;
		this.uso = uso;
		this.base = base;
		this.contadorDeReferencias = contadorDeReferencias;
		this.defPorUser = esSubTipo;
		this.seDeclaro = seDeclaro;
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        if (entero != -1) {
            sb.append("Es entero: " +entero);            
        }

        if (doub != -1.0) {
            sb.append("Es double: "+doub);
            sb.append(" - ");
        }
         
        if(this.base != 10) {
            sb.append(" Es Octal");
            sb.append(" - ");
        }

        if(this.tipoVar!=null) {
        	sb.append("Su tipo es " +tipoVar);
            sb.append(" - ");
        }
        if(this.uso!=null) {
            sb.append("Su uso " +uso);
            sb.append(" - ");
        }
        if(this.contadorDeReferencias != 0) {
        	sb.append(" La referencian "+contadorDeReferencias);
            sb.append(" - ");	
        }
        if(this.defPorUser!=false) {
        	sb.append(" Es un Tipo ");
            sb.append(" - ");
        }
        if(this.seDeclaro!=false) {
            sb.append(" se declaro: "+seDeclaro);
        }


        // Si ningun valor fue inicializado
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
	public boolean isEsTipo() {
		return defPorUser;
	}
	public void setEsTipo(boolean es) {
		this.defPorUser = es;
	}

	public String getTipo() {
	    if (entero != -1) {
	        if (base == 8) {
	            return "Octal";
	        } else {
	            return "Entero";
	        }
	    }
	    
	    if (doub != -1.0) {
	        return "Double";
	    }
	    
	    if (tipoVar != null) {
	        return tipoVar.toString();
	    }

	    return null;
	}
	public String getUso() {
		return uso;
	}
	public void setUso(String uso) {
		this.uso = uso;
	}
	
	public boolean estaDeclarada(String amb) {
		return seDeclaro;
	}
	public void fueDeclarada() {
		this.seDeclaro=true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}