package org.iesmurgi.reta2.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.iesmurgi.reta2.Data.entidades.Partidas;


/**
 * DAO de partidas
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see Partidas
 * @see org.iesmurgi.reta2.Data.BasedeDatosApp
 */
@Dao
public interface PartidasDao {

    /**
     * Método para insertar una o varias partidas en el room
     * @param partida que se va a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void partidasInsert(Partidas... partida);



}
