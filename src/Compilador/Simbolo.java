package Compilador;

public class Simbolo {
    private Class<?> tipo;       // Tipo de variable
    private int direccion;    // Dirección en memoria
    private boolean booleano;    // Atributo booleano

    // Constructor
    public Simbolo(Class<?> tipo, int direccion, boolean booleano) {
        this.tipo = tipo;
        this.direccion = direccion;
        this.booleano = booleano;
    }

    // Getters y setters
    public Class<?> getTipo() {
        return tipo;
    }

    public void setTipo(Class<?> tipo) {
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
