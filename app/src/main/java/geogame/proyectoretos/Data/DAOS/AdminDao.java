package geogame.proyectoretos.Data.DAOS;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by usuario on 16/02/18.
 */
@Dao
public interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarAdmins(Admin... admin);

    @Query("Select * from admin where user_name = :nombre")
    Admin getAdminActual(String nombre);

    @Query("Select * from admin")
   List<Admin> getAdmins();



}
