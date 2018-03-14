package org.iesmurgi.reta2.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.iesmurgi.reta2.Data.entidades.Partidas;

/**
 * Created by usuario on 16/02/18.
 */
@Dao
public interface PartidasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void partidasInsert(Partidas... partida);


    @Query("Select * from partidas where nombre = :nombre")
    Partidas getPartidaActual(String nombre);



}