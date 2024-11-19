package main.java.Compilador;

public class Simbolo {
    private int entero;       // Tipo de variable
    private double doub; 
    private String id;
    private Tipo tipoVar;
    private String uso;
    private String tipoParFormal=" ";
    private String tipoRetorno=" ";
    private String ambitoVar="";
    private int dirMEM=-1;
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
        this.contadorDeReferencias = 1;
        this.defPorUser = false;
    }
    public Tipo getTipoVar() {
		return tipoVar;
	}
	public void setTipoVar(Tipo tipoVar) {
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
        this.contadorDeReferencias=1;
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
        Simbolo simb = new Simbolo(this.id+amb,this.entero, this.doub,this.tipoVar,this.uso,this.base,this.contadorDeReferencias,
        		this.defPorUser,this.seDeclaro,amb);
        AnalizadorLexico.TablaDeSimbolos.put(this.id+amb, simb);
    }
    public String getAmbito() {
        return this.ambitoVar;
    }
    
    
    public String getAmbitoVar() {
		return ambitoVar;
	}
	public void setAmbitoVar(String ambitoVar) {
		this.ambitoVar = ambitoVar;
	}
	
	public Simbolo(String id, int entero, double doub, Tipo tipoVar, String uso, int base, int contadorDeReferencias,
			boolean esSubTipo, boolean seDeclaro, String amb) {
		super();
		this.id=id;
		this.entero = entero;
		this.doub = doub;
		this.tipoVar = tipoVar;
		this.uso = uso;
		this.base = base;
		this.contadorDeReferencias = contadorDeReferencias;
		this.defPorUser = esSubTipo;
		this.seDeclaro = seDeclaro;
		this.ambitoVar=amb;

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
            sb.append("Es un" +uso);
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
            sb.append(" - ");
        }
        if(this.tipoParFormal!=" ") {
            sb.append(" El parametro formal es: "+tipoParFormal);
            sb.append(" - ");
        }
        if(this.tipoRetorno!=" ") {
            sb.append(" El Retorno es : "+tipoParFormal);
            sb.append(" - ");
        }
        if(this.dirMEM!=-1) {
            sb.append(" Esta ubicado Polaca["+dirMEM+"]");
            sb.append(" - ");
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

	public Tipo getTipo() {
	    if (entero != -1) {
	        if (base == 8) {
	            return Parser.tipos.get("OCTAL");
	        } else {
	            return Parser.tipos.get("INTEGER");
	        }
	    }
	    
	    if (doub != -1.0) {
	        return Parser.tipos.get("DOUBLE");
	    }
	    
	    if (tipoVar != null) {
	        return tipoVar;
	    }

	    return null;
	}
	public boolean esLiteral() {
	    if (entero != -1) {
	        return true;
	    }
	    
	    if (doub != -1.0) {
	        return true;
	    }

	    return false;
	}

	public boolean sonCompatibles(Simbolo simb2) {
		//System.out.println("Se estan comparando "+ this + " con un "+ simb2);
		if(this.tipoVar.sonCompatibles(simb2.getTipo())) {
			return true;
		}
		System.out.println("Tipos incompatibles, se intento operar un "+ this.getTipo() + " con un "+ simb2.getTipo());
		return false;
	}
	
	public boolean esSubTipo() {
		return this.tipoVar.esSubTipo();
	}
	public boolean esTripla() {
		return this.tipoVar.esTripla();
	}
	
	public String getUso() {
		return uso;
	}
	public void setUso(String uso) {
		this.uso = uso;
	}
	
	public boolean estaDeclarada() {
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
	public String getTipoParFormal() {
		return tipoParFormal;
	}
	public void setTipoParFormal(String tipoParFormal) {
		this.tipoParFormal = tipoParFormal;
	}
	public String getTipoRetorno() {
		return tipoRetorno;
	}
	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}
	public int getDirMEM() {
		return dirMEM;
	}
	public void setDirMEM(int dirMEM) {
		this.dirMEM = dirMEM;
	}
	public boolean esDouble() {
    	if (this.doub == -1.0) {
    		return false;
    	}
    	return true;
	}
	
}