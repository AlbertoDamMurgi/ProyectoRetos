/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/

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
