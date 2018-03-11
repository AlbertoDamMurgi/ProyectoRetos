package geogame.proyectoretos.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import geogame.proyectoretos.Data.entidades.Respuestas;

/**
 * Created by Farra on 08/03/2018.
 */
@Dao
public interface RespuestasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarRespuestas(Respuestas... respuestas);

    @Query("select * from respuestas where idReto=:idreto")
    List<Respuestas> getRespuestas(int idreto);


}
