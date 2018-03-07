package geogame.proyectoretos.Data.entidades;

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
    private String correo;

    public Usuarios(int idUsuario, String username, String passwd,String correo ) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.passwd = passwd;
        this.correo = correo;
    }

    @Ignore
    public Usuarios(String username, String passwd, String correo) {
        this.username = username;
        this.passwd = passwd;
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
}
