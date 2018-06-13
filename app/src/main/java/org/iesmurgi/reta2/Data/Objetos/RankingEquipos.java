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

package org.iesmurgi.reta2.Data.Objetos;


import org.iesmurgi.reta2.Data.BasedeDatosApp;
import org.iesmurgi.reta2.UI.usuario.RankingActivity;

/**
 * Clase del ranking de los equipos
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see RankingActivity
 */

public class RankingEquipos {

    private String nombreEquipo;
    private int puntosTotales;

    /**
     * Constructor de la clase RankingEquipos
     * @param nombreEquipo nombre del equipo
     * @param puntosTotales puntos totales del equipo
     */
    public RankingEquipos(String nombreEquipo, int puntosTotales) {
        this.nombreEquipo = nombreEquipo;
        this.puntosTotales = puntosTotales;
    }

    /**
     * Método que devuelve el nombre del equipo
     * @return nombre del equipo
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * Método que asigna un valor al nombre del equipo
     * @param nombreEquipo valor que se asigna al nombre del equipo
     */
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    /**
     * Método que devuelve los puntos totales del equipo
     * @return puntos totales del equipo
     */
    public int getPuntosTotales() {
        return puntosTotales;
    }

    /**
     * Método que asigna los puntos totales del equipo
     * @param puntosTotales puntos totales del equipo
     */
    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }
}
