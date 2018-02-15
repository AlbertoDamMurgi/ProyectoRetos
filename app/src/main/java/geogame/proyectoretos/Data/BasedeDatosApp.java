package geogame.proyectoretos.Data;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by usuario on 15/02/18.
 */


//TODO: hay que poner las entidades aqui
@Database(entities = {},version = 1)
@TypeConverters(DateConverter.class)
public abstract class BasedeDatosApp extends RoomDatabase {

    private static final String DATABASE_NAME = "";

}
