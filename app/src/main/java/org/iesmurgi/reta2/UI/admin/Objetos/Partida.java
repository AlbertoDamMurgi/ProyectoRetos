package org.iesmurgi.reta2.UI.admin.Objetos;


public class Partida {

    private String nombre;
    private int id;
    private String codeqr;

    public Partida(String nombre, int id, String codeqr) {
        this.nombre = nombre;
        this.id = id;
        this.codeqr = codeqr;
    }

    public String getCodeqr() {
        return codeqr;
    }

    public void setCodeqr(String codeqr) {
        this.codeqr = codeqr;
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
