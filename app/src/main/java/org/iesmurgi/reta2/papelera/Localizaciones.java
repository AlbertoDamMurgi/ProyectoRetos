/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/

package org.iesmurgi.reta2.papelera;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by usuario on 15/02/18.
 */

@Entity(tableName = "localizaciones",
        primaryKeys ={"idUsuario","fecha"},
        foreignKeys = @ForeignKey(entity = Usuarios.class,
                parentColumns = "idUsuario",
        childColumns = "idUsuario",onDelete = CASCADE,onUpdate = CASCADE)
        )
public class Localizaciones {

    private int idUsuario;
    @NonNull
    private Date fecha;
    private float localizacionLatitud;
    private float localizacionLongitud;


    public Localizaciones(int idUsuario, Date fecha, float localizacionLatitud, float localizacionLongitud) {
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.localizacionLatitud = localizacionLatitud;
        this.localizacionLongitud = localizacionLongitud;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getLocalizacionLatitud() {
        return localizacionLatitud;
    }

    public void setLocalizacionLatitud(float localizacionLatitud) {
        this.localizacionLatitud = localizacionLatitud;
    }

    public float getLocalizacionLongitud() {
        return localizacionLongitud;
    }

    public void setLocalizacionLongitud(float localizacionLongitud) {
        this.localizacionLongitud = localizacionLongitud;
    }
}
