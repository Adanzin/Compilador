package Compilador;

public class Simbolo {
    private String tipo;       // Tipo de variable
    

    // Constructor
    public Simbolo(String tipo) {
        this.tipo = tipo;
    }
    
    public Simbolo() {
    }


    // Getters y setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
