package Compilador;

public class Simbolo {
    private String tipo;       // Tipo de variable
    private int direccion;    // Dirección en memoria
    private boolean booleano;    // Atributo booleano

    // Constructor
    public Simbolo(String tipo, int direccion, boolean booleano) {
        this.tipo = tipo;
        this.direccion = direccion;
        this.booleano = booleano;
    }
    
    public Simbolo(int direccion, boolean booleano) { //si es palabra reservada
        this.direccion = direccion;
        this.booleano = booleano;
    }

    // Getters y setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public boolean isBooleano() {
        return booleano;
    }

    public void setBooleano(boolean booleano) {
        this.booleano = booleano;
    }
}
