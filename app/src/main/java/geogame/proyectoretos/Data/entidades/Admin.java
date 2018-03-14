package geogame.proyectoretos.Data.entidades;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by usuario on 15/02/18.
 */
@Entity(tableName = "admin")
public class Admin  {
    @PrimaryKey(autoGenerate = true)
    private int id_admin;
    private String user_name;
    private String passwd;
    private int superAdmin;

    @Ignore
    public Admin(String user_name, String pass, int superAdmin) {
        this.user_name = user_name;
        this.passwd = pass;
        this.superAdmin = superAdmin;
    }

    public Admin(int id_admin, String user_name, String passwd, int superAdmin) {
        this.id_admin = id_admin;
        this.user_name = user_name;
        this.passwd = passwd;
        this.superAdmin = superAdmin;
    }

    public int getId_admin() {
        return id_admin;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(int superAdmin) {
        this.superAdmin = superAdmin;
    }
}
