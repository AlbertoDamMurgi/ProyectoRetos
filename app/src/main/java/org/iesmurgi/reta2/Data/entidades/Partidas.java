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

package org.iesmurgi.reta2.Data.entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Entidad de partidas
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see org.iesmurgi.reta2.Data.DAOS.PartidasDao
 * @see org.iesmurgi.reta2.Data.BasedeDatosApp
 */

@Entity(tableName = "partidas")
public class Partidas {
    @PrimaryKey(autoGenerate = true)
    private int idPartida;
    private String nombre;
    private String passwd;
    private int maxDuracion;

    /**
     * Constructor de la entidad Partida
     * @param idPartida id de la partida
     * @param nombre nombre de la partida
     * @param passwd contraseña de la partida
     * @param maxDuracion duracion maxima de la partida.
     */
    public Partidas(int idPartida, String nombre, String passwd, int maxDuracion) {
        this.idPartida = idPartida;
        this.nombre = nombre;
        this.passwd = passwd;
        this.maxDuracion = maxDuracion;
    }

    @Ignore
    public Partidas(String nombre, String passwd, int maxDuracion) {
        this.nombre = nombre;
        this.passwd = passwd;
        this.maxDuracion = maxDuracion;
    }

    /**
     * Método que devuelve el id de partida
     * @return idPartida
     */
    public int getIdPartida() {
        return idPartida;
    }

    /**
     * Método que asigna un valor a idPartida
     * @param idPartida valor que se va a asignar
     */
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }
    /**
     * Método que devuelve el nombre de la partida
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que asigna un valor al nombre de la partida
     * @param nombre valor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve la contraseña de la partida
     * @return passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Método que asinga un valor a la contraseña
     * @param passwd valor
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Método que devuelve la duración de una partida
     * @return duracion
     */
    public int getMaxDuracion() {
        return maxDuracion;
    }

    /**
     * Método que asigna la duracion de una partida
     * @param maxDuracion duración máxima de la partida
     */
    public void setMaxDuracion(int maxDuracion) {
        this.maxDuracion = maxDuracion;
    }
}
