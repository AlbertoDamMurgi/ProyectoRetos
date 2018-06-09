package org.iesmurgi.reta2.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import org.iesmurgi.reta2.Data.entidades.Respuestas;

/**
 * DAO de respuestas
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see Respuestas
 * @see org.iesmurgi.reta2.Data.BasedeDatosApp
 */
@Dao
public interface RespuestasDao {

    /**
     * Método para insertar una o varias respuestas en el room
     * @param respuestas
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarRespuestas(Respuestas... respuestas);

    /**
     * Método que devuelve las respuestas de un reto.
     * @param idreto id del reto del cual se quieren obtener las respuestas.
     * @return una lista de Respuestas.
     */
    @Query("select * from respuestas where idReto=:idreto")
    List<Respuestas> getRespuestas(int idreto);


}
