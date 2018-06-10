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
