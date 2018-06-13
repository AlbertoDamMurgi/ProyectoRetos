/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/
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

    /**
     * Constructor
     * @param mensaje contenido del mensaje
     * @param autor autor del mensaje
     */
    public Chat(String mensaje, String autor) {
        this.mensaje = mensaje;
        this.autor = autor;
    }

    /**
     * Constructor para mensajes sin autor
     * @param mensaje contenido del mensaje
     */
    public Chat(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Constructor por defecto
     */
    public Chat() {
    }

    /**
     * Método que devuelve el mensaje
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Método que asigna el mensaje
     * @param mensaje que se asigna
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Método que devuelve el autor
     * @return autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Método que asigna el autor
     * @param autor que se almacena en el objeto
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }
}
