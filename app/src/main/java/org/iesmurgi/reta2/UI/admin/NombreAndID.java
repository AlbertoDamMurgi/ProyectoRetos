package org.iesmurgi.reta2.UI.admin;

public class NombreAndID {

    private String nombre;
    private int id;

    public NombreAndID(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
