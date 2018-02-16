package geogame.proyectoretos.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by usuario on 16/02/18.
 */
@Dao
public interface AdminDao {

    @Insert
    void insertarAdmins(Admin... admin);



}
