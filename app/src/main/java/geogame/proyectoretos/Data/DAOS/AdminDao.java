package geogame.proyectoretos.Data.DAOS;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by usuario on 16/02/18.
 */
@Dao
public interface AdminDao {

    @Insert
    void insertarAdmins(Admin... admin);

    @Query("Select * from admin where nombre = :nombre")
    LiveData<Admin> getAdminActual(String nombre);



}