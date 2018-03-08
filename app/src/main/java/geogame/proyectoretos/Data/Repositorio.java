package geogame.proyectoretos.Data;

import android.arch.lifecycle.LiveData;

import geogame.proyectoretos.Data.DAOS.AdminDao;
import geogame.proyectoretos.Data.entidades.Admin;

/**
 * Created by Farra on 08/03/2018.
 */

public class Repositorio {

    private final AdminDao adminDao;


    public Repositorio(AdminDao adminDao) {
        this.adminDao = adminDao;
    }


    public LiveData<Admin> getAdmin(String nombre){

        return adminDao.getAdminActual(nombre);

    }

    public void insertarAdmin(Admin admin){

        adminDao.insertarAdmins(admin);

    }



}
