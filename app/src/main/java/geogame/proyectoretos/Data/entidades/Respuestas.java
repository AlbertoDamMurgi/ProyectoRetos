package geogame.proyectoretos.Data.entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by usuario on 16/02/18.
 */
@Entity(tableName = "respuestas",foreignKeys = @ForeignKey(entity = Retos.class,
        parentColumns ="idReto" , childColumns ="idReto",onDelete = CASCADE,onUpdate = CASCADE))
public class Respuestas {
    @PrimaryKey(autoGenerate = true)
    private int idRespuesta;
    private int idReto;
    private String descripcion;
    private int verdadero;

    public Respuestas(int idRespuesta, int idReto, String descripcion, int verdadero) {
        this.idRespuesta = idRespuesta;
        this.idReto = idReto;
        this.descripcion = descripcion;
        this.verdadero = verdadero;
    }

    @Ignore
    public Respuestas(int idReto, String descripcion, int verdadero) {
        this.idReto = idReto;
        this.descripcion = descripcion;
        this.verdadero = verdadero;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public int getIdReto() {
        return idReto;
    }

    public void setIdReto(int idReto) {
        this.idReto = idReto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getVerdadero() {
        return verdadero;
    }

    public void setVerdadero(int verdadero) {
        this.verdadero = verdadero;
    }
}
