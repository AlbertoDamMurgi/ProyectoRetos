package org.iesmurgi.reta2.UI.retos;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import org.iesmurgi.reta2.Data.entidades.Retos;

/**
 * Viewmodel para la localizacion y todos los datos relevantes para el mapa del usuario
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class LocationModel extends ViewModel{

    private MutableLiveData<Location> mLocation = new MutableLiveData<>();

    private MutableLiveData<Long> time = new MutableLiveData<>();
    private List<Retos> retos = new ArrayList<>();
    boolean cargados=false;
    private String usuario="";
    private String partida ="";
    private Integer idpartida,idusuario,numReto;
    private String nombrepartida;

    /**
     * Método que devuelve el id de la partida que se esta jugando
     * @return id de la partida actual
     */
    public Integer getIdpartida() {
        return idpartida;
    }

    /**
     * Método que asigna un valor al id de partida
     * @param idpartida valor que se asigna al id de partida
     */
    public void setIdpartida(Integer idpartida) {
        this.idpartida = idpartida;
    }

    /**
     * Método que devuelve el id del usuario que esta jugando la partida
     * @return id del usuario
     */
    public Integer getIdusuario() {
        return idusuario;
    }

    /**
     * Método que asigna el id del usuario que esta jugando la partida
     * @param idusuario id del usuario que esta jugando la partida
     */
    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    /**
     * Método que devuelve el nombre de la partida que se esta jugando
     * @return nombre de la partida
     */
    public String getNombrepartida() {
        return nombrepartida;
    }

    /**
     * Método que asigna el nombre de la partida que se esta jugando
     * @param nombrepartida nombre de la partida que se esta jugando
     */
    public void setNombrepartida(String nombrepartida) {
        this.nombrepartida = nombrepartida;
    }

    /**
     * Método que devuelve el nombre de la partida que se esta jugando
     * @return nombre de la partida
     */
    public String getPartida() {
        return partida;
    }

    /**
     * Método que asigna el nombre de la partida que se esta jugando
     * @param partida partida que se esta jugando
     */
    public void setPartida(String partida) {
        this.partida = partida;
    }

    /**
     * Método que devuelve el nombre del usuario que  esta jugando
     * @return nombre del usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Método que asigna el nombre del usuario que esta jugando
     * @param usuario nombre del usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Método que devuelve la lista de los retos de esa partida
     * @return lista de retos de la partida
     */
    public List<Retos> getRetos() {
        return retos;
    }

    /**
     * Método que asigna los retos de la partida
     * @param retos lista de retos de la partida
     */
    public void setRetos(List<Retos> retos) {
        this.retos = retos;
    }

    /**
     * Método que devuelve si los retos estan cargados o no.
     * @return
     */
    public boolean isCargados() {
        return cargados;
    }

    /**
     * Método que asigna un valor si los retos estan cargados o no.
     * @param cargados true si estan cargados, false si no.
     */
    public void setCargados(boolean cargados) {
        this.cargados = cargados;
    }

    /**
     * Método que devuelve el numero de retos que tiene la partida
     * @return numero de retos que tiene la partida
     */
    public Integer getNumReto() {
        return numReto;
    }

    /**
     * Método que asigna el numero de retos de la partida
     * @param numReto numero de retos de la partida
     */
    public void setNumReto(Integer numReto) {
        this.numReto = numReto;
    }

    /**
     * Método que devuelve la localizacion del usuario
     * @return localizacion del usuario
     */
    public MutableLiveData<Location> getmLocation() {
        return mLocation;
    }

    /**
     * Método que asigna la localizacion del usuario
     * @param location localizacion del usuario
     */
    public void setmLocation(Location location) {
        this.mLocation.setValue(location);
    }





}
