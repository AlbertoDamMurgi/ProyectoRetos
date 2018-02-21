package geogame.proyectoretos.Data.entidades;

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
    private int maxDuracion;
    private int tipo;
    private int puntuacion;
    private float localizacionLatitud;
    private float localizacionLongitud;
    private int idPartida;

    @Ignore
    public Retos(String nombre, String descripcion, int maxDuracion, int tipo, int puntuacion, float localizacionLatitud, float localizacionLongitud, int idPartida) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.maxDuracion = maxDuracion;
        this.tipo = tipo;
        this.puntuacion = puntuacion;
        this.localizacionLatitud = localizacionLatitud;
        this.localizacionLongitud = localizacionLongitud;
        this.idPartida = idPartida;
    }

    public Retos(int idReto, String nombre, String descripcion, int maxDuracion, int tipo, int puntuacion, float localizacionLatitud, float localizacionLongitud, int idPartida) {
        this.idReto = idReto;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public float getLocalizacionLatitud() {
        return localizacionLatitud;
    }

    public void setLocalizacionLatitud(float localizacionLatitud) {
        this.localizacionLatitud = localizacionLatitud;
    }

    public float getLocalizacionLongitud() {
        return localizacionLongitud;
    }

    public void setLocalizacionLongitud(float localizacionLongitud) {
        this.localizacionLongitud = localizacionLongitud;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }
}
