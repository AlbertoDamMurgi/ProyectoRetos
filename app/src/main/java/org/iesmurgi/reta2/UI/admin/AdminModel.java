package org.iesmurgi.reta2.UI.admin;

import android.arch.lifecycle.ViewModel;

public class AdminModel extends ViewModel {

    private String codigoqr=null;
    private int retoactual = 0;

    public int getRetoactual() {
        return retoactual;
    }

    public void setRetoactual(int retoactual) {
        this.retoactual = retoactual;
    }

    public String getCodigoqr() {
        return codigoqr;
    }

    public void setCodigoqr(String codigoqr) {
        this.codigoqr = codigoqr;
    }
}
