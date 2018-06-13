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

import java.util.List;

import org.iesmurgi.reta2.Data.entidades.Retos;

/**
 * DAO de retos
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see Retos
 * @see org.iesmurgi.reta2.Data.BasedeDatosApp
 */
@Dao
public interface RetosDao {

    /**
     * Método para insertar uno o varios retos
     * @param retos a insertar en el room.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void retosInsert(Retos... retos);

    /**
     * Método que devuelve todos los datos de un reto
     * @return
     */
    @Query("Select * from retos  order by idReto")
    List<Retos> getRetos();

    /**
     * Método que devuelve todos los retos de una partida ordenadors por su id de reto.
     * @param idpartida de la que se quieren obtener los retos.
     * @return lista de retos de una partida.
     */
    @Query("select * from retos where idPartida = :idpartida order by idReto")
    List<Retos> getRetosPartida(int idpartida);

    /**
     * Método que devuelve un reto de una partida.
     * @param idpartida partida a la que pertenece el reto
     * @param idreto id del reto
     * @return
     */
    @Query("select * from retos where idPartida = :idpartida and idReto=:idreto")
     Retos getReto_Partida(int idpartida,int idreto);

    /**
     * Método que devuelve el nombre de un reto
     * @param idreto id del reto
     * @return nombre del reto
     */
    @Query("select nombre from retos where idReto=:idreto")
    String getNombreReto(int idreto);

}
