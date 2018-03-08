package geogame.proyectoretos.UI;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import geogame.proyectoretos.Data.Repositorio;
import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by Farra on 08/03/2018.
 */

public class PruebaModel extends AndroidViewModel {

    private Repositorio mRepositorio;
    private LiveData<List<Admin>> mAdmins;


    public PruebaModel(Application application) {
        super(application);
        mRepositorio = new Repositorio(application);
        mAdmins = mRepositorio.getAdmins();
    }

    public void insertAdministrador(Admin admin) { mRepositorio.insertarAdmin(admin); }



}
