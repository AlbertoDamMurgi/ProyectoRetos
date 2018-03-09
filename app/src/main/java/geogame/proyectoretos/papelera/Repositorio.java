package geogame.proyectoretos.papelera;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import geogame.proyectoretos.Data.BasedeDatosApp;
import geogame.proyectoretos.Data.Internet;
import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by Farra on 08/03/2018.
 */

public class Repositorio {


   public BasedeDatosApp basedeDatosApp ;

   public Internet internet;

    public Repositorio(Application application) {

      basedeDatosApp = BasedeDatosApp.getAppDatabase(application);
      internet = Internet.getInternet(application);

    }


    public LiveData<List<Admin>> getAdmins(){



        return basedeDatosApp.adminDao().getAdmins();

    }




    public void insertarAdmin(Admin...admin){


        basedeDatosApp.adminDao().insertarAdmins(admin);


    }



    }


