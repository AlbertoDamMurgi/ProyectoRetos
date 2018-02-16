package geogame.proyectoretos.Data;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

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



@Database(entities = {Partidas.class,Localizaciones.class,Usuarios.class,
        Reglas.class,Admin.class, Retos.class, Respuestas.class},version = 1)
@TypeConverters(DateConverter.class)
public abstract class BasedeDatosApp extends RoomDatabase {

    private static final String DATABASE_NAME = "ProyectoRetos";
    private static final Object LOCK = new Object();
    private static volatile BasedeDatosApp sInstance;

    public static BasedeDatosApp getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), BasedeDatosApp.class, BasedeDatosApp.DATABASE_NAME).build();

                }
            }
        }

        return sInstance;


    }




    //TODO: hay que crear una interfaz(dao) por cada tabla

}