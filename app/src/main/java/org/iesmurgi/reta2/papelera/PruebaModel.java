/*package org.iesmurgi.proyectoretos.papelera;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import org.iesmurgi.proyectoretos.Data.entidades.Admin;

/**
 * Created by Farra on 08/03/2018.
 */
/*
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import org.iesmurgi.proyectoretos.Data.entidades.Admin;

public class PruebaModel extends AndroidViewModel {

    private Repositorio mRepositorio;
    private MutableLiveData<List<Admin>> mAdmins;


    public PruebaModel(Application application) {
        super(application);
        mRepositorio = new Repositorio(application);

    }


    public void insertAdministrador(Admin admin) { mRepositorio.insertarAdmin(admin); }



    public LiveData<List<Admin>> getmAdmins() {
        if(mAdmins==null) {
            mAdmins = new MutableLiveData<>();
            mAdmins.postValue(mRepositorio.internet.listarAdmins());
        }


        return mAdmins;
    }
}
*/