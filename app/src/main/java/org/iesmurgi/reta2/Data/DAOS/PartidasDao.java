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
