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
 * Entidad de Retos
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see org.iesmurgi.reta2.Data.DAOS.RetosDao
 * @see org.iesmurgi.reta2.Data.BasedeDatosApp
 */
@Entity(tableName = "retos",foreignKeys = @ForeignKey(entity = Partidas.class,
        parentColumns ="idPartida" , childColumns ="idPartida",onDelete = CASCADE,onUpdate = CASCADE))
public class Retos {
    @PrimaryKey(autoGenerate = true)
    private int idReto;
    private String nombre;
    private String descripcion;
    private String video;
    private int maxDuracion;
    private int tipo;
    private int puntuacion;
    private double localizacionLatitud;
    private double localizacionLongitud;
    private int idPartida;

    @Ignore
    public Retos(String nombre, String descripcion,String video, int maxDuracion, int tipo, int puntuacion, double localizacionLatitud, double localizacionLongitud, int idPartida) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.video = video;
        this.maxDuracion = maxDuracion;
        this.tipo = tipo;
        this.puntuacion = puntuacion;
        this.localizacionLatitud = localizacionLatitud;
        this.localizacionLongitud = localizacionLongitud;
        this.idPartida = idPartida;
    }

    /**
     * Constructor de la entidad Retos
     * @param idReto id del reto
     * @param nombre nombre del reto
     * @param descripcion descripcion del reto
     * @param video video opcional del reto
     * @param maxDuracion duración del reto
     * @param tipo tipo del reto
     * @param puntuacion puntuación del reto
     * @param localizacionLatitud latitud de la coordenada del reto
     * @param localizacionLongitud longitud de la coordenada del reto
     * @param idPartida id de la partida a la que pertenece el reto
     */
    public Retos(int idReto, String nombre, String descripcion,String video, int maxDuracion, int tipo, int puntuacion, double localizacionLatitud, double localizacionLongitud, int idPartida) {
        this.idReto = idReto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.video = video;
        this.maxDuracion = maxDuracion;
        this.tipo = tipo;
        this.puntuacion = puntuacion;
        this.localizacionLatitud = localizacionLatitud;
        this.localizacionLongitud = localizacionLongitud;
        this.idPartida = idPartida;
    }

    /**
     * Método que devuelve el id del reto
     * @return id del reto
     */
    public int getIdReto() {
        return idReto;
    }

    /**
     * Método que asigna un valor al id reto
     * @param idReto id del reto
     */
    public void setIdReto(int idReto) {
        this.idReto = idReto;
    }

    /**
     * Método que devuelve el nombre del reto
     * @return nombre del reto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que asigna un valor al nombre del reto
     * @param nombre nombre del reto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve la descripción del reto
     * @return descripción del reto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método que asigna la descripción del reto
     * @param descripcion descripción del reto
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método que devuelve la duración del reto
     * @return duración del reto
     */
    public int getMaxDuracion() {
        return maxDuracion;
    }

    /**
     * Método que asigna la duración del reto
     * @param maxDuracion duración del reto
     */
    public void setMaxDuracion(int maxDuracion) {
        this.maxDuracion = maxDuracion;
    }

    /**
     * Método que devuelve el tipo del reto
     * @return tipo del reto
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Método que asigna el tipo del reto
     * @param tipo tipo del reto
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Método que devuelve la puntuación del reto
     * @return puntuación del reto
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Método que asigna la puntación del reto
     * @param puntuacion puntuación del reto
     */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Método que devuelve la latitud de la localización del reto
     * @return latitud de la localización del reto
     */
    public double getLocalizacionLatitud() {
        return localizacionLatitud;
    }

    /**
     * Método que asigna la latitud de la localización del reto
     * @param localizacionLatitud latitud de la localización del reto
     */
    public void setLocalizacionLatitud(double localizacionLatitud) {
        this.localizacionLatitud = localizacionLatitud;
    }

    /**
     * Método que devuelve la longitud de la localización del reto
     * @return longitud de la localización del reto
     */
    public double getLocalizacionLongitud() {
        return localizacionLongitud;
    }

    /**
     * Método que asigna la longitud de la localización del reto
     * @param localizacionLongitud longitud de la localización del reto
     */
    public void setLocalizacionLongitud(double localizacionLongitud) {
        this.localizacionLongitud = localizacionLongitud;
    }

    /**
     * Método que devuelve el id de la partida a la que pertenece el reto
     * @return id de la partida a la que pertenece el reto
     */
    public int getIdPartida() {
        return idPartida;
    }

    /**
     * Método que asigna el id de la partida a la que pertenece el reto
     * @param idPartida id de la partida a la que pertenece el reto
     */
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    /**
     * Método que devuelve la url del video del reto
     * @return la url del video del reto en forma de string
     */
    public String getVideo() {
        return video;
    }

    /**
     * Método que asigna el video del reto
     * @param video video del reto
     */
    public void setVideo(String video) {
        this.video = video;
    }
}
