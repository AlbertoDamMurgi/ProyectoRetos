package org.iesmurgi.reta2.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import org.iesmurgi.reta2.Data.entidades.Retos;

/**
 * Created by Farra on 08/03/2018.
 */
@Dao
public interface RetosDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void retosInsert(Retos... retos);

    @Query("Select * from retos  order by idReto")
    List<Retos> getRetos();

    @Query("select * from retos where idPartida = :idpartida order by idReto")
    List<Retos> getRetosPartida(int idpartida);

    @Query("select * from retos where idPartida = :idpartida and idReto=:idreto")
     Retos getReto_Partida(int idpartida,int idreto);

    @Query("select nombre from retos where idReto=:idreto")
    String getNombreReto(int idreto);

}
