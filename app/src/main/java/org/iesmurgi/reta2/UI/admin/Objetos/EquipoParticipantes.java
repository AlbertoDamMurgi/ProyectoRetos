package org.iesmurgi.reta2.UI.admin.Objetos;

/**
 * Created by Santi on 14/05/2018.
 */

public class EquipoParticipantes
{
    private String equipo;
    private String participantes;

    public EquipoParticipantes(String equipo, String participantes) {
        this.equipo = equipo;
        this.participantes = participantes;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }
}
