package org.iesmurgi.reta2.UI.admin;

import android.arch.lifecycle.ViewModel;

public class AdminModel extends ViewModel {

    private String codigoqr=null;

    public String getCodigoqr() {
        return codigoqr;
    }

    public void setCodigoqr(String codigoqr) {
        this.codigoqr = codigoqr;
    }
}
