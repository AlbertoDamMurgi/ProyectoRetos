package geogame.proyectoretos.papelera;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Farra on 01/03/2018.
 */

public class LoginModel extends ViewModel {

    private MutableLiveData<FirebaseAuth> conection = new MutableLiveData<>();

    private MutableLiveData<FirebaseUser> usuario = new MutableLiveData<>();

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
