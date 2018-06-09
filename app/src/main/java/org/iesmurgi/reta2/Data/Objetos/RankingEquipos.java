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
