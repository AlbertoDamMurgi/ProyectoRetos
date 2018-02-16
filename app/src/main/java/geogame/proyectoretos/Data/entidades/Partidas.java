package geogame.proyectoretos.Data.entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by usuario on 15/02/18.
 */
@Entity(tableName = "partidas")
public class Partidas {
    @PrimaryKey(autoGenerate = true)
    private int idPartida;
    private String nombre;
    private String passwd;
    private int maxDuracion;

    public Partidas(int idPartida, String nombre, String passwd, int maxDuracion) {
        this.idPartida = idPartida;
        this.nombre = nombre;
        this.passwd = passwd;
        this.maxDuracion = maxDuracion;
    }

    @Ignore
    public Partidas(String nombre, String passwd, int maxDuracion) {
        this.nombre = nombre;
        this.passwd = passwd;
        this.maxDuracion = maxDuracion;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getMaxDuracion() {
        return maxDuracion;
    }

    public void setMaxDuracion(int maxDuracion) {
        this.maxDuracion = maxDuracion;
    }
}
