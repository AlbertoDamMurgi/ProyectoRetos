package org.iesmurgi.reta2.Data.DAOS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import org.iesmurgi.reta2.Data.entidades.Usuarios;

/**
 * Created by Farra on 08/03/2018.
 */
@Dao
public interface UsuariosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarUsuario(Usuarios...usuarios);


}
