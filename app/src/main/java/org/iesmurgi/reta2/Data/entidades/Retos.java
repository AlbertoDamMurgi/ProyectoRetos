package org.iesmurgi.reta2.Data.entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by usuario on 16/02/18.
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

    public int getIdReto() {
        return idReto;
    }

    public void setIdReto(int idReto) {
        this.idReto = idReto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMaxDuracion() {
        return maxDuracion;
    }

    public void setMaxDuracion(int maxDuracion) {
        this.maxDuracion = maxDuracion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public double getLocalizacionLatitud() {
        return localizacionLatitud;
    }

    public void setLocalizacionLatitud(double localizacionLatitud) {
        this.localizacionLatitud = localizacionLatitud;
    }

    public double getLocalizacionLongitud() {
        return localizacionLongitud;
    }

    public void setLocalizacionLongitud(double localizacionLongitud) {
        this.localizacionLongitud = localizacionLongitud;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
