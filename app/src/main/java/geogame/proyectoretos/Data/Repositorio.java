package geogame.proyectoretos.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import geogame.proyectoretos.Data.DAOS.AdminDao;
import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by Farra on 08/03/2018.
 */

public class Repositorio {


   private BasedeDatosApp basedeDatosApp ;

    public Repositorio(Application application) {

      basedeDatosApp = BasedeDatosApp.getAppDatabase(application);


    }


    public LiveData<List<Admin>> getAdmins(){

        LiveData<List<Admin>> admins = basedeDatosApp.adminDao().getAdmins();
        return admins;

    }


    public void insertarAdmin(Admin admin){

        new insertAdminAsyncTask(basedeDatosApp.adminDao()).execute(admin);


    }

    private static class insertAdminAsyncTask extends AsyncTask<Admin, Void, Void> {

        private AdminDao mAdminDao;

        insertAdminAsyncTask(AdminDao dao) {
            mAdminDao = dao;
        }


        @Override
        protected Void doInBackground(final Admin... params) {
            mAdminDao.insertarAdmins(params[0]);

            return null;
        }


    }

}
