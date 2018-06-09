package org.iesmurgi.reta2.UI.usuario;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.work.WorkManager;

/**
 * Created by Farra on 01/03/2018.
 */

public class LoginModel extends ViewModel {

    private MutableLiveData<FirebaseAuth> conection = new MutableLiveData<>();

    private MutableLiveData<FirebaseUser> usuario = new MutableLiveData<>();

    public WorkManager mWorkManager = WorkManager.getInstance();

    private Long miTiempo;

    public Long getMiTiempo() {
        return miTiempo;
    }

    public void setMiTiempo(Long miTiempo) {
        this.miTiempo = miTiempo;
    }

    public MutableLiveData<FirebaseAuth> getConection() {
        return conection;
    }

    public void setConection(MutableLiveData<FirebaseAuth> conection) {
        this.conection = conection;
    }

    public MutableLiveData<FirebaseUser> getUsuario() {
        return usuario;
    }

    public void setUsuario(MutableLiveData<FirebaseUser> usuario) {
        this.usuario = usuario;
    }



}
