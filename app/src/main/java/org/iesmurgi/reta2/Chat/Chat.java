package org.iesmurgi.reta2.Chat;

/**
 * Esta es la clase base para los mensajes de los chats.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see ChatActivity
 * @see ChatAdapter
 * @see ChatAdminActivity
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
