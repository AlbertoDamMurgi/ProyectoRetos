package org.iesmurgi.reta2.UI.retos;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import org.iesmurgi.reta2.Data.entidades.Retos;

/**
 * Created by Farra on 10/03/2018.
 */

public class LocationModel extends ViewModel{

    private MutableLiveData<Location> mLocation = new MutableLiveData<>();
    private List<Retos> retos = new ArrayList<>();
    boolean cargados=false;
    private int numReto=0;
    private String usuario="";
    private String partida ="";

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Retos> getRetos() {
        return retos;
    }

    public void setRetos(List<Retos> retos) {
        this.retos = retos;
    }

    public boolean isCargados() {
        return cargados;
    }

    public void setCargados(boolean cargados) {
        this.cargados = cargados;
    }

    public int getNumReto() {
        return numReto;
    }

    public void setNumReto(int numReto) {
        this.numReto = numReto;
    }

    public MutableLiveData<Location> getmLocation() {
        return mLocation;
    }

    public void setmLocation(Location location) {
        this.mLocation.setValue(location);
    }





}
