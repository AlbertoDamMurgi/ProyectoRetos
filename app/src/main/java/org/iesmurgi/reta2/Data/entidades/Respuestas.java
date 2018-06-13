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
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Entidad de Respuestas
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see org.iesmurgi.reta2.Data.DAOS.RespuestasDao
 * @see org.iesmurgi.reta2.Data.BasedeDatosApp
 */
@Entity(tableName = "respuestas",foreignKeys = @ForeignKey(entity = Retos.class,
        parentColumns ="idReto" , childColumns ="idReto",onDelete = CASCADE,onUpdate = CASCADE))
public class Respuestas {
    @PrimaryKey(autoGenerate = true)
    private int idRespuesta;
    private int idReto;
    private String descripcion;
    private int verdadero;

    /**
     * Constructor de la entidad respuestas
     * @param idRespuesta id de la respuesta
     * @param idReto id del reto
     * @param descripcion decripción de la respuesta
     * @param verdadero si es verdadera o no
     */
    public Respuestas(int idRespuesta, int idReto, String descripcion, int verdadero) {
        this.idRespuesta = idRespuesta;
        this.idReto = idReto;
        this.descripcion = descripcion;
        this.verdadero = verdadero;
    }

    @Ignore
    public Respuestas(int idReto, String descripcion, int verdadero) {
        this.idReto = idReto;
        this.descripcion = descripcion;
        this.verdadero = verdadero;
    }

    /**
     * Método que devuelve el id de la respuesta
     * @return  id de la respuesta
     */
    public int getIdRespuesta() {
        return idRespuesta;
    }

    /**
     * Método que asigna un valor al id de la respuesta
     * @param idRespuesta id de la respuesta
     */
    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    /**
     * Método que devuelve el id de reto al que pertenece la respuesta
     * @return id del reto al que pertenece la respuesta
     */
    public int getIdReto() {
        return idReto;
    }

    /**
     * Método que asigna un valor al id del reto al que pertenece la respuesta
     * @param idReto al que pertenece la respuesta
     */
    public void setIdReto(int idReto) {
        this.idReto = idReto;
    }

    /**
     * Método que devuelve la descripción de la respuesta.
     * @return descripción de la respuesta
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método que asigna un valor a la descripción de la respuesta
     * @param descripcion de la respuesta
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método que devuelve el valor verdadero de la respuesta
     * @return valor verdadero de la respuesta
     */
    public int getVerdadero() {
        return verdadero;
    }

    /**
     * Método que asigna el valor verdadero de la respuesta
     * @param verdadero valor que se le asigna.
     */
    public void setVerdadero(int verdadero) {
        this.verdadero = verdadero;
    }
}
