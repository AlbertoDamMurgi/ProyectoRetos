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
