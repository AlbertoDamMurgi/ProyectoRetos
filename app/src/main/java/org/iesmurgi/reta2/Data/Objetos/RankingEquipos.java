package org.iesmurgi.reta2.Data.Objetos;

/**
 * Created by jota on 06/04/2018.
 */

public class RankingEquipos {

    private String nombreEquipo;
    private int puntosTotales;

    public RankingEquipos(String nombreEquipo, int puntosTotales) {
        this.nombreEquipo = nombreEquipo;
        this.puntosTotales = puntosTotales;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }
}
