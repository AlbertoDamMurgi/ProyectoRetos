package geogame.proyectoretos.Data.DAOS;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import geogame.proyectoretos.Data.entidades.Partidas;

/**
 * Created by usuario on 16/02/18.
 */
@Dao
public interface PartidasDao {

    @Insert
    void partidasInsert(Partidas... partida);


    @Query("Select * from partidas where nombre = :nombre")
    Partidas getPartidaActual(String nombre);



}
