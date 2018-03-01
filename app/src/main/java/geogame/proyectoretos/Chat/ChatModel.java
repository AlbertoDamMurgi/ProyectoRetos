package geogame.proyectoretos.Chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by Farra on 01/03/2018.
 */

public class ChatModel extends ViewModel {

   MutableLiveData<String> texto = new MutableLiveData<>();

    public MutableLiveData<String> getTexto() {
        return texto;
    }

    public void setTexto(MutableLiveData<String> texto) {
        this.texto = texto;
    }
}
