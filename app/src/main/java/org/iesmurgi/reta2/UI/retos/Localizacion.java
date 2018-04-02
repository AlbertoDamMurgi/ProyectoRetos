package org.iesmurgi.reta2.UI.retos;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Localizacion {

    private LatLng loc;

    public Localizacion(LatLng loc) {
        this.loc = loc;

    }

    public LatLng getLoc() {
        return loc;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
    }
}
