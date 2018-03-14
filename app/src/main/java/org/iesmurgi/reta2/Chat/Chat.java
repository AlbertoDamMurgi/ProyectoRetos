package org.iesmurgi.reta2.Chat;

/**
 * Created by usuario on 21/02/18.
 */

public class Chat {
    private String mensaje;
    private String autor;

    public Chat(String mensaje, String autor) {
        this.mensaje = mensaje;
        this.autor = autor;
    }

    public Chat(String mensaje) {
        this.mensaje = mensaje;
    }

    public Chat() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}