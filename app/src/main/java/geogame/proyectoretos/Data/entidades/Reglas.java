package geogame.proyectoretos.Data.entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by usuario on 16/02/18.
 */
@Entity(tableName = "reglas",
        foreignKeys =
        @ForeignKey(entity = Partidas.class,
                parentColumns ="idPartida" ,
                childColumns ="idPartida",onDelete = CASCADE,onUpdate = CASCADE))
public class Reglas {

    @PrimaryKey(autoGenerate = true)
    private int idRegla;
    private int idPartida;
    private String texto;

    public Reglas(int idRegla, int idPartida, String texto) {
        this.idRegla = idRegla;
        this.idPartida = idPartida;
        this.texto = texto;
    }

    @Ignore
    public Reglas(int idPartida, String texto) {
        this.idPartida = idPartida;
        this.texto = texto;
    }

    public int getIdRegla() {
        return idRegla;
    }

    public void setIdRegla(int idRegla) {
        this.idRegla = idRegla;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


}
