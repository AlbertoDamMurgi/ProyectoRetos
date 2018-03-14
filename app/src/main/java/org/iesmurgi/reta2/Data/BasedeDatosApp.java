package org.iesmurgi.reta2.Data;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import org.iesmurgi.reta2.Data.DAOS.AdminDao;
import org.iesmurgi.reta2.Data.DAOS.LocalizacionesDao;
import org.iesmurgi.reta2.Data.DAOS.PartidasDao;
import org.iesmurgi.reta2.Data.DAOS.RespuestasDao;
import org.iesmurgi.reta2.Data.DAOS.RetosDao;
import org.iesmurgi.reta2.Data.DAOS.UsuariosDao;
import org.iesmurgi.reta2.Data.entidades.Admin;
import org.iesmurgi.reta2.Data.entidades.Localizaciones;
import org.iesmurgi.reta2.Data.entidades.Partidas;
import org.iesmurgi.reta2.Data.entidades.Reglas;
import org.iesmurgi.reta2.Data.entidades.Respuestas;
import org.iesmurgi.reta2.Data.entidades.Retos;
import org.iesmurgi.reta2.Data.entidades.Usuarios;

/**
 * Created by usuario on 15/02/18.
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






