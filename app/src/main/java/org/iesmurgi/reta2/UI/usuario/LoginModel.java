package org.iesmurgi.reta2.UI.usuario;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.work.WorkManager;

/**
 * Viewmodel del login.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class LoginModel extends ViewModel {

    private MutableLiveData<FirebaseAuth> conection = new MutableLiveData<>();

    private MutableLiveData<FirebaseUser> usuario = new MutableLiveData<>();

    public WorkManager mWorkManager = WorkManager.getInstance();

    private Long miTiempo;

    /**
     * Método que devuelve el tiempo del reto
     * @return
     */
    public Long getMiTiempo() {
        return miTiempo;
    }

    /**
     * Método que asigna un valor al tiempo del reto
     * @param miTiempo tiempo del reto
     */
    public void setMiTiempo(Long miTiempo) {
        this.miTiempo = miTiempo;
    }

    /**
     * Método que devuelve la conexcion a la base de datos firebase
     * @return
     */
    public MutableLiveData<FirebaseAuth> getConection() {
        return conection;
    }

    /**
     * Método que asigna un valor una conexion a la base de datos firebase
     * @param conection conexion a la base de datos firebase
     */
    public void setConection(MutableLiveData<FirebaseAuth> conection) {
        this.conection = conection;
    }

    /**
     * Método que devuelve el usuario conectado a firebase
     * @return usuario de firebase
     */
    public MutableLiveData<FirebaseUser> getUsuario() {
        return usuario;
    }

    /**
     * Método que asigna un valor de usuario de firebase
     * @param usuario usuario de firebase
     */
    public void setUsuario(MutableLiveData<FirebaseUser> usuario) {
        this.usuario = usuario;
    }



}
