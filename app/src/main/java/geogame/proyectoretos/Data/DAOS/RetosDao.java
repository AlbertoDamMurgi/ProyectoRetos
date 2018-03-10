package geogame.proyectoretos.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import geogame.proyectoretos.Data.entidades.Admin;
import geogame.proyectoretos.Data.entidades.Retos;

/**
 * Created by Farra on 08/03/2018.
 */
@Dao
public interface RetosDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void retosInsert(Retos... retos);

    @Query("Select * from retos")
    List<Retos> getRetos();



}
