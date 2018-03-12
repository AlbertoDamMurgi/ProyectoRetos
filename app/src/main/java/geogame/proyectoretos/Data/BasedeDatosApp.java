package geogame.proyectoretos.Data;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import geogame.proyectoretos.Data.DAOS.AdminDao;
import geogame.proyectoretos.Data.DAOS.LocalizacionesDao;
import geogame.proyectoretos.Data.DAOS.PartidasDao;
import geogame.proyectoretos.Data.DAOS.RespuestasDao;
import geogame.proyectoretos.Data.DAOS.RetosDao;
import geogame.proyectoretos.Data.DAOS.UsuariosDao;
import geogame.proyectoretos.Data.entidades.Admin;
import geogame.proyectoretos.Data.entidades.Localizaciones;
import geogame.proyectoretos.Data.entidades.Partidas;
import geogame.proyectoretos.Data.entidades.Reglas;
import geogame.proyectoretos.Data.entidades.Respuestas;
import geogame.proyectoretos.Data.entidades.Retos;
import geogame.proyectoretos.Data.entidades.Usuarios;

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






