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

package org.iesmurgi.reta2.Data;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import org.iesmurgi.reta2.Data.DAOS.PartidasDao;
import org.iesmurgi.reta2.Data.DAOS.RespuestasDao;
import org.iesmurgi.reta2.Data.DAOS.RetosDao;
import org.iesmurgi.reta2.Data.entidades.Partidas;
import org.iesmurgi.reta2.Data.entidades.Respuestas;
import org.iesmurgi.reta2.Data.entidades.Retos;
import org.iesmurgi.reta2.papelera.Admin;
import org.iesmurgi.reta2.papelera.AdminDao;
import org.iesmurgi.reta2.papelera.Localizaciones;
import org.iesmurgi.reta2.papelera.LocalizacionesDao;
import org.iesmurgi.reta2.papelera.Reglas;
import org.iesmurgi.reta2.papelera.Usuarios;
import org.iesmurgi.reta2.papelera.UsuariosDao;


/**
 * Database para el Room
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see RetosDao
 * @see RespuestasDao
 * @see PartidasDao
 * @see android.arch.persistence.room.RoomDatabase
 * @see Retos
 * @see Respuestas
 */


@Database(entities = {Partidas.class,Localizaciones.class,Usuarios.class,Admin.class, Retos.class,Reglas.class, Respuestas.class},version = 1)
@TypeConverters(DateConverter.class)
public abstract class BasedeDatosApp extends RoomDatabase {

    private static final String DATABASE_NAME = "ProyectoRetos";
    private static BasedeDatosApp INSTANCE;



    public abstract AdminDao adminDao();
    public abstract LocalizacionesDao localizacionesDao();
    public abstract PartidasDao partidasDao();
    public abstract RespuestasDao respuestasDao();
    public abstract RetosDao retosDao();
    public abstract UsuariosDao usuariosDao();


    public static BasedeDatosApp getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BasedeDatosApp.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BasedeDatosApp.class, DATABASE_NAME)
                            .build();
                }
            }

        }
        return INSTANCE;
    }




    }






