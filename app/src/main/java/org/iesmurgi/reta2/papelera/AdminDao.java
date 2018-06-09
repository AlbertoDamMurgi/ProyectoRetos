package org.iesmurgi.reta2.papelera;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * DAO de Admin
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see Admin
 */

@Dao
public interface AdminDao {

    /**
     * Método para insertar uno o varios administradores en el room.
     * @param admin administrador a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarAdmins(Admin... admin);

    /**
     * Método que devuelve un administrador segun su nombre
     * @param nombre nombre del administrador que queremos que nos devuelva
     * @return Admin administrador devuelto por la base de datos
     */
    @Query("Select * from admin where user_name = :nombre")
    Admin getAdminActual(String nombre);

    /**
     * Método que devuelve todos los administradores de la tabla Admin.
     * @return List<Admin> lista de todos los administradores
     */
    @Query("Select * from admin")
   List<Admin> getAdmins();



}
