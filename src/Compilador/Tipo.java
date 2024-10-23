package Compilador;

public class Tipo {
    public Object tipo=null;
    public String tipoDefPorUsuario=null;

	public Tipo(String t) {
        if(t=="INTEGER" || t=="integer" ) {
        	this.tipo=int.class;
        }else if(t=="DOUBLE" || t=="double" ) {
        	this.tipo=double.class;
        }else {
        	tipoDefPorUsuario =t;
        }
    }
    
    
    @Override
    public String toString() {
        if(tipo!=null) {
        	return tipo.toString();
        }
    	return tipoDefPorUsuario;
    }
}
