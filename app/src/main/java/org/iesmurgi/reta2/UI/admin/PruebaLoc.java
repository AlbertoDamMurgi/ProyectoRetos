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

package org.iesmurgi.reta2.UI.admin;
/**
 * Clase para almacenar la latitud y longitud en strings
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */

public class PruebaLoc {
    private String latitud;
    private String longitud;


    public PruebaLoc() {
    }

    /**
     * Constructor
     * @param latitud latitud de la coordenada
     * @param longitud longitud de la coordenada
     */
    public PruebaLoc(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Método que devuelve la latitud
     * @return string con la latitud
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     * Método que devuelve la longitud
     * @return string con la longitud
     */
    public String getLongitud() {
        return longitud;
    }


}
