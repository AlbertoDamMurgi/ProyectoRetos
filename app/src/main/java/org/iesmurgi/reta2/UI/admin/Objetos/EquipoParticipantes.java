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

package org.iesmurgi.reta2.UI.admin.Objetos;

/**
 * Clase que relaciona los equipos y los participantes
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */

public class EquipoParticipantes
{
    private String equipo;
    private String participantes;

    /**
     * Constructor de la clase EquipoParticipantes
     * @param equipo
     * @param participantes
     */
    public EquipoParticipantes(String equipo, String participantes) {
        this.equipo = equipo;
        this.participantes = participantes;
    }

    /**
     * Método que devuelve el nombre del equipo
     * @return nombre del equipo
     */
    public String getEquipo() {
        return equipo;
    }

    /**
     * Método que asigna el nombre del equipo
     * @param equipo nombre del equipo
     */
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    /**
     * Método que devuelve el nombre de los participantes que compoenen el equipo
     * @return nombre de los participantes del equipo
     */
    public String getParticipantes() {
        return participantes;
    }

    /**
     * Método que asigna los participantes que componen el equipo
     * @param participantes participantes que componen el equipo
     */
    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }
}
