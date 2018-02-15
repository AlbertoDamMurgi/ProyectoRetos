package geogame.proyectoretos.Data;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by usuario on 15/02/18.
 */


//TODO: hay que poner las entidades aqui
@Database(entities = {Admin.class},version = 1)
@TypeConverters(DateConverter.class)
public abstract class BasedeDatosApp extends RoomDatabase {

    private static final String DATABASE_NAME = "";
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