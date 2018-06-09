package org.iesmurgi.reta2.papelera;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by usuario on 15/02/18.
 */
@Entity(tableName = "usuarios")
public class Usuarios {

    @PrimaryKey(autoGenerate = true)
    private int idUsuario;
    private String username;
    private String passwd;

    private String participantes;


    public Usuarios(int idUsuario, String username, String passwd, String participantes) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.passwd = passwd;
        this.participantes = participantes;
    }

    @Ignore
    public Usuarios(String username, String passwd,String participantes) {
        this.username = username;
        this.passwd = passwd;
        this.participantes=participantes;

   

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }
}
